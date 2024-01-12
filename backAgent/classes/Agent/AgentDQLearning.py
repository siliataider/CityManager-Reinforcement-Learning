from .Agent import Agent

class AgentDQLearning(Agent) :

    def choose_action(self, state, exploration_rate):
        pass

    def train_model(self, state, action, reward, next_state_value, learning_rate, discount_factor):
        pass