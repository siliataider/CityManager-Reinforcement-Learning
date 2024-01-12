from resources.variables import FINAL_EXPLORATION_RATE, START_EXPLORATION_RATE, EPLORATION_RATE_DECAY_STEPS, MAX_TIME_STEP


class SimulationConditions() :
    def __init__(self, exploration_rate):
        # Init of the coditions
        self.weather = 0
        self.timestamp = 0
        self.is_new_episode = True
        self.exploration_rate = exploration_rate
        self.learning_rate = 1 - exploration_rate
        self.list_agent = []
    
    def set_exploration_learning_rate(self):
        if (self.is_new_episode):
            self.exploration_rate = max(FINAL_EXPLORATION_RATE, self.exploration_rate - (START_EXPLORATION_RATE - FINAL_EXPLORATION_RATE) / EPLORATION_RATE_DECAY_STEPS)
            self.learning_rate = 1 - self.exploration_rate
            self.is_new_episode = False
    
    def set_simulation_conditions(self, message):
        self.weather = message['weather']
        self.timestamp = message['timestamp']
        self.is_new_episode = not bool(message['tick']%MAX_TIME_STEP)
    
    def set_list_agent(self, list_agent):
        self.list_agent = list_agent