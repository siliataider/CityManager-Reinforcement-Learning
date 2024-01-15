import tensorflow as tf
import numpy as np
from resources.variables import TABLE_STATES, NUM_STATE, NUM_ACTION

def build_model():
  num_units = 100
  model = tf.keras.Sequential()
  model.add(tf.keras.layers.Dense(num_units, input_dim=NUM_STATE, activation="relu"))
  model.add(tf.keras.layers.Dense(num_units, activation="relu"))
  model.add(tf.keras.layers.Dense(NUM_ACTION))
  model.compile(loss="mse", optimizer=tf.keras.optimizers.Adam(learning_rate=0.001))
  return model

def reshape_state(state):

  np.array(state).reshape(1, -1)

def find_state_index(state):
  try:
    state_index = None
    for key, value in TABLE_STATES.items():
        if value == state:
            state_index = key
            break
    if state_index == None:
      raise Exception('Index non trouvé: ' + str(state))
  except Exception as e:
    print('Error: ' + str(e))

  return state_index


def convert_value_to_state(state_value):
  state = []
  timestamp, weather, hunger, energy, money = state_value
  if 9 <= timestamp < 17:
    state.append('WorkingHour')
  else:
    state.append('FreeHour')

  if weather == 0:
    state.append('NotRainy')
  else:
    state.append('Rainy')

  if hunger <= 0.2:
    state.append('LowHungry')
  elif 0.2 < hunger < 0.8:
    state.append('MediumHungry')
  else:
    state.append('FullHungry')

  if energy <= 0.2:
    state.append('LowEnergy')
  elif 0.2 < energy < 0.8:
    state.append('MediumEnergy')
  else:
    state.append('FullEnergy')

  if money <= 0.2:
    state.append('LowMoney')
  elif 0.2 < money < 0.8:
    state.append('MediumMoney')
  else:
    state.append('FullMoney')


  state_index = find_state_index(state)

  return state_index

def create_visu_q_table(q_table):
  combined_array = []

  for i, row in enumerate(q_table):
      # Récupérer la clé correspondante pour accéder aux valeurs du dictionnaire
      key = i
      values = TABLE_STATES.get(key, [])  # Obtenez les valeurs du dictionnaire pour la clé ou une liste vide si la clé n'est pas trouvée

      # Ajouter les données de la ligne actuelle et les valeurs du dictionnaire à la nouvelle liste
      combined_row = [row.tolist(), values]
      combined_array.append(combined_row)

  #print(combined_array)
  # Afficher le nouveau tableau
  for row in combined_array:
      print(row)