import numpy as np
from resources.variables import TABLE_ACTIONS, TABLE_STATES, DISCOUNT_FACTOR 
from resources.methods import build_model
from resources.wrappers import ModelWrapper

class Agent() :
    def __init__(self, num_states, num_actions, env, agent_id):
        # Attributs of the agents here
        self.num_states = num_states
        self.num_actions = num_actions
        self.q_table = np.zeros((num_states, num_actions))
        self.model = ModelWrapper()
        self.env = env
        self.agent_id = agent_id
    
    def __str__(self) -> str:
        return f"agent_id: {self.agent_id}, state: {self.env.state}, state_value: {self.env.state_value}"
    
    def train(self, simulationConditions):
        # Choix de l'action
        action = self.choose_action(self.env.state_value, simulationConditions.exploration_rate)
        # Exécution de l'action et obtention du nouvel état et de la récompense
        reward, next_state_value = self.env.get_reward_and_next_state(action)

        next_state_value = (simulationConditions.timestamp, simulationConditions.weather, *next_state_value[2:])
        # Mise à jour de la table Q
        #print(f"state value : {self.env.state_value}, next_state_value: {next_state_value}")
        self.train_model(self.env.state_value, action, reward, next_state_value, simulationConditions.learning_rate, DISCOUNT_FACTOR)

        #print(f"REWARD: {reward}, action: {action}")
        #print(f"current state value {self.env.state_value} current state: {TABLE_STATES[self.env.state]}")
        #print(f"next state value: {next_state_value} next state: {TABLE_STATES[next_state]}")
        #print(f"action: {TABLE_ACTIONS[action]}, reward: {reward}, exploration_rate: {simulationConditions.exploration_rate}")
        #print("------------------------------------------------------------------------------")
        
        # Mettre à jour l'état actuel
        #self.env.set_state(next_state_value)
        self.env.state_value = next_state_value

        # total_reward += reward
        '''
        if total_reward <= 0:
        print("DEEEEEEEEEEEEAAAAAAAAAAAAAADDDDDDDDDDDD")
        print(f"time_step: {env.time_step}, total_reward: {total_reward}")
        break
        '''
        #average_rewards.append(total_reward / max_time_steps)
        print(f"exploration_rate: {simulationConditions.exploration_rate}, action: {action}")
        
        return(self)
    
    def choose_action(self, state_value, exploration_rate):
        raise NotImplementedError("La méthode 'choose_action' doit être implémentée par les sous-classes.")
            

    def train_model(self, state_value, action, reward, next_state_value, learning_rate, discount_factor):
        raise NotImplementedError("La méthode 'update_q_table' doit être implémentée par les sous-classes.")