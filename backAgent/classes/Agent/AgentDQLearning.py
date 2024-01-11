from .Agent import Agent

class AgentDQLearning(Agent) :

    def choose_action(self, state, exploration_rate):
        pass

    def update_q_table(self, state, action, reward, next_state, learning_rate, discount_factor):
        pass