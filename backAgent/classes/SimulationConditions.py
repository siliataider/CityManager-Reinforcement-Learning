from resources.variables import FINAL_EXPLORATION_RATE, START_EXPLORATION_RATE, EPLORATION_RATE_DECAY_STEPS, MAX_TIME_STEP, TABLE_ACTIONS


class SimulationConditions() :
    def __init__(self, exploration_rate):
        # Init of the coditions
        self.weather = 0
        self.timestamp = 0
        self.is_new_episode = True
        self.exploration_rate = exploration_rate
        self.learning_rate = 1 - exploration_rate
        self.list_agent = []

    def __str__(self) -> str:
        return f"simulationCondition: exploration_rate => {self.exploration_rate}, learning_rate => {self.learning_rate}, is_new_episode: {self.is_new_episode}"
    
    def set_exploration_learning_rate(self):
        if (self.is_new_episode):
            self.exploration_rate = max(FINAL_EXPLORATION_RATE, self.exploration_rate - (START_EXPLORATION_RATE - FINAL_EXPLORATION_RATE) / EPLORATION_RATE_DECAY_STEPS)
            self.learning_rate = 1 - self.exploration_rate
    
    def set_simulation_conditions(self, message):
        self.weather = message['weather']
        self.timestamp = message['timestamp']
        self.is_new_episode = not bool(message['tick']%MAX_TIME_STEP)
    
    def set_list_agent(self, list_agent):
        self.list_agent = list_agent

    def get_agents_info(self):
        agents_list = []
        for agent in self.list_agent:
            agent_info = self.get_one_agent_info(agent=agent, action=None)
            agents_list.append(agent_info)

        return agents_list

    def get_one_agent_info(self, agent, action):

        (timestamp, weather, hunger, energy, money) = agent.env.state_value
        agent_info = {
                'id': agent.agent_id,
                'state': {
                    'hunger': hunger,
                    'energy': energy,
                    'money': money
                },
                'algo': agent.algo,
                'reward_moyen': agent.env.reward_moy
            }
        
        if action != None:
            agent_info['action'] = TABLE_ACTIONS[action]

        return agent_info
