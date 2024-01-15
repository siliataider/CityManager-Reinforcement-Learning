import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras.optimizers import Adam
import numpy as np

def build_model():
  model = Sequential()
  model.add(tf.keras.layers.Dense(100, input_dim=3, activation="relu"))
  model.add(tf.keras.layers.Dense(100, activation="relu"))
  model.add(tf.keras.layers.Dense(3))
  model.compile(loss="mse", optimizer=Adam(learning_rate=0.001))
  return model

build_model()
print("OK")