import tensorflow as tf
from resources.variables import NUM_STATE, NUM_ACTION

class ModelWrapper:
    def __init__(self):
        self.model = self.build_model()  # TensorFlow model
        self.qs = None  # Placeholder for Q-values
    

    def build_model(self):
        num_units = 100
        model = tf.keras.Sequential()
        model.add(tf.keras.layers.Dense(num_units, input_dim=NUM_STATE, activation="relu"))
        model.add(tf.keras.layers.Dense(num_units, activation="relu"))
        model.add(tf.keras.layers.Dense(NUM_ACTION))
        model.compile(loss="mse", optimizer=tf.keras.optimizers.Adam(learning_rate=0.001))
        return model