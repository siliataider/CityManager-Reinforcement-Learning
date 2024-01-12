from .Agent import Agent
import random
import numpy as np
from resources.variables import NUM_STATE
from resources.methods import reshape_state

class AgentDQLearning(Agent) :

    def choose_action(self, state, exploration_rate):
        if random.random() < exploration_rate:
            return np.random.choice(self.num_actions)  # Exploration
        else:
            state = reshape_state(state)
            self.model.qs = self.model.model.predict(state, verbose=0)[0]  # Exploitation
            return np.argmax(self.model.qs)  

    
    def train_model(self, state, action, reward, next_state_value, learning_rate, discount_factor):
        state = reshape_state(state)
        next_state = reshape_state(next_state_value)
        target = self.model.qs.copy()
        Qs_next = self.model.model.predict(next_state, verbose=0)[0]
        target[action] = reward + discount_factor * np.max(Qs_next)
        self.model.model.fit(state, np.array([target]), epochs=1, verbose=0)
        
        self.env.state = next_state_value