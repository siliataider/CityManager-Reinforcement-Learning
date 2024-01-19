import random

class AgentEnvironment:
    def __init__(self, timestamp, weather):
        self.state = 0 
        self.state_value = self.init_state(timestamp=timestamp, weather=weather)
        self.reward_moy = []
        self.total_reward = 0

    def reset(self):
        self.state = 0
        self.state_value = (0, 0, 0, 0, 0)
        self.reward_moy = []
        self.total_reward = 0
    
    def init_state(self, timestamp, weather):
        hunger, energy, money = [round(random.uniform(0, 1), 1) for _ in range(3)]
        state = (timestamp, weather, hunger, energy, money) #TODO, hunger, energy et money random pour le moment
        return state

    def get_reward_and_next_state(self, action):
        timestamp, weather, hunger, energy, money = self.state_value
        working_hours = 9 <= timestamp < 17
        reward = 0

        if action == 0:  # Go Home
            if energy > 0.8:
                reward -= 0.20
            if hunger < 0.2:
                reward -= 0.15
            if working_hours:
                if money < 0.2:
                    reward -= 0.15
                reward -= 0.50
                energy += 0.025
            else:
                if energy < 0.2:
                    reward += 1
                if weather:
                    reward += 1
                reward += 1
                energy += 0.05
            hunger -= 0.025

        elif action == 1:  # Go to Work
            if working_hours:
                if energy < 0.2 or hunger < 0.2:
                    if money > 0.8: 
                        reward -= 0.40  
                    else:
                        reward -= 0.20
                if weather:
                    reward += 1
                if money < 0.2:
                    reward += 1
                reward += 1
                energy -= 0.025
                money += 0.1
            else:
                if money > 0.8:
                    reward -= 0.20
                if energy < 0.2:
                    reward -= 0.15
                if hunger < 0.2:
                    reward -= 0.15
                reward -= 0.50
                energy -= 0.05
            hunger -= 0.05

        elif action == 2:  # Go Eat
            if hunger > 0.8:
                if money < 0.2:
                    reward -= 0.30
                reward -= 0.50
            elif hunger < 0.2:
                if money < 0.2:
                    reward -= 0.30
                elif money > 0.8:
                    reward += 1
                reward += 1
            else:
                if money < 0.2:
                    reward -= 0.30
                elif money > 0.8:
                    reward += 0.30
                reward += 0.35

            if weather:
                reward -= 0.20
            else:
                reward += 1

            money -= 0.1
            hunger += 0.1

        hunger = max(min(hunger, 1), 0)
        energy = max(min(energy, 1), 0)
        money = max(min(money, 1), 0)

        '''
        # TODO remove timestamp and weather modif when using SimuCondition
        timestamp += 1
        if timestamp > 23:
            timestamp = 0
        weather = random.choice([0, 1])
        '''
        
        return reward, (timestamp, weather, hunger, energy, money)

    def calul_reward_moyen(self, reward, simulationCondition):
        if simulationCondition.is_new_episode:
            self.reward_moy.append(self.total_reward/ simulationCondition.max_time_steps)
            self.total_reward = 0
        self.total_reward += reward
        #print(f"total reward: {self.total_reward}")
