from .Agent import Agent
import random
import numpy as np
from resources.methods import reshape_state
from resources.wrappers import ModelWrapper
import tensorflow as tf

class AgentDQLearning(Agent) :
    def __init__(self, num_states, num_actions, env, agent_id):
        super().__init__(num_states, num_actions, env, agent_id)  # Appel du constructeur de la classe mère
        self.model = ModelWrapper()

    def choose_action(self, state_value, exploration_rate):
        state = reshape_state(state_value)
        self.model.qs = self.model.model.predict(state, verbose=0)[0]  # Exploitation
        if random.random() < exploration_rate:
            return np.random.choice(self.num_actions)  # Exploration
        else:
            return np.argmax(self.model.qs)  

    
    def train_model(self, state_value, action, reward, next_state_value, learning_rate, discount_factor):
        state = reshape_state(state_value)
        next_state = reshape_state(next_state_value)
        target = self.model.qs.copy()
        Qs_next = self.model.model.predict(next_state, verbose=0)[0]
        target[action] = reward + discount_factor * np.max(Qs_next)
        self.model.model.fit(state, np.array([target]), epochs=1, verbose=0)
    
    def save_model(self):
        self.model.model.save('dql_model_agent_' + self.agent_id +'.h5')
    
    def load_model(self, model):
        self.model.model = tf.keras.models.load_model(model)