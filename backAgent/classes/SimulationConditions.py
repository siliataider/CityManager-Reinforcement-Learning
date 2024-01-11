from resources.variables import FINAL_EXPLORATION_RATE, START_EXPLORATION_RATE, EPLORATION_RATE_DECAY_STEPS


class SimulationConditions() :
    def __init__(self, messageSocket, exploration_rate):
        # Init of the coditions
        self.weather = messageSocket['weather']
        self.timestamp = messageSocket['timestamp']
        self.is_new_episode = not bool(messageSocket['tick']%24)
        self.exploration_rate = exploration_rate
        self.learning_rate = 1 - exploration_rate
    
    def set_exploration_learning_rate(self):
        if (self.is_new_episode):
            self.exploration_rate = max(FINAL_EXPLORATION_RATE, self.exploration_rate - (START_EXPLORATION_RATE - FINAL_EXPLORATION_RATE) / EPLORATION_RATE_DECAY_STEPS)
            self.learning_rate = 1 - self.exploration_rate
            self.is_new_episode = True