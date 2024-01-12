from .Agent import Agent
import random
import numpy as np
from resources.methods import convert_value_to_state

class AgentQLearning(Agent) :
    
    def choose_action(self, state, exploration_rate):
        rand = random.random()
        if rand < exploration_rate:
            return np.random.choice(self.num_actions)  # Exploration
        else:
            return np.argmax(self.q_table[state, :])  # Exploitation
    
    def train_model(self, state, action, reward, next_state_value, learning_rate, discount_factor):
        next_state = convert_value_to_state(next_state_value)

        best_next_action = np.argmax(self.q_table[next_state, :])
        self.q_table[state, action] += learning_rate * (reward + discount_factor * self.q_table[next_state, best_next_action] - self.q_table[state, action])

        self.env.state = next_state
