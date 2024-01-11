from .Agent import Agent
import random
import numpy as np

class AgentQLearning(Agent) :
    
    def choose_action(self, state, exploration_rate):
        rand = random.random()
        if rand < exploration_rate:
            return np.random.choice(self.num_actions)  # Exploration
        else:
            return np.argmax(self.q_table[state, :])  # Exploitation
    
    def update_q_table(self, state, action, reward, next_state, learning_rate, discount_factor):
        best_next_action = np.argmax(self.q_table[next_state, :])
        self.q_table[state, action] += learning_rate * (reward + discount_factor * self.q_table[next_state, best_next_action] - self.q_table[state, action])