import random

class AgentEnvironment:
    def __init__(self, state):
        self.state = 0 
        self.time_step = 0
        self.state_value = (state['timestamp'], state['weather'], state['hunger'], state['energy'], state['money'])

    def reset(self):
        self.state = 0
        self.time_step = 0
        self.state_value = (0, 0, 0, 0, 0)
    
    def set_state(self, state_value):
        self.state_value = state_value

    def get_reward_and_next_state(self, action):
        timestamp, weather, hunger, energy, money = self.state_value
        working_hours = 9 <= timestamp < 17
        reward = 0

        if action == 0:  # Go home
            if energy > 0.8:
                reward -= 20
            if hunger < 0.2:
                reward -= 15
            if working_hours:
                if money < 0.2:
                    reward -= 15
                reward -= 50
                energy += 0.025
            else:
                if energy < 0.2:
                    reward += 30
                if weather:
                    reward += 20
                reward += 50
                energy += 0.05
            hunger -= 0.05

        elif action == 1:  # Go Work
            if working_hours:
                if energy < 0.2 or hunger < 0.2:
                    if money > 0.8:
                        reward -= 40
                    else:
                        reward -= 20
                if weather:
                    reward += 20
                if money < 0.2:
                    reward += 30
                reward += 50
                energy -= 0.075
                money += 0.05
            else:
                if money > 0.8:
                    reward -= 20
                if energy < 0.2:
                    reward -= 15
                if hunger < 0.2:
                    reward -= 15
                reward -= 50
                energy -= 0.2
            hunger -= 0.075

        elif action == 2:  # Go eat
            if hunger > 0.8:
                if money < 0.2:
                    reward -= 30
                reward -= 50
            elif hunger < 0.2:
                if money > 0.8:
                    reward += 30
                elif money < 0.2 :
                    reward -= 30
                reward += 50
            else:
                if money > 0.8:
                    reward += 30
                elif money < 0.2:
                    reward -= 30
                reward += 35

            if weather:
                reward -= 20
            else:
                reward += 20

            money -= 0.1
            hunger += 0.1

        # Ensure hunger, energy, and money stay within 0-1 range
        hunger = max(min(hunger, 1), 0)
        energy = max(min(energy, 1), 0)
        money = max(min(money, 1), 0)

        self.time_step += 1

        '''
        # TODO remove timestamp and weather modif when using SimuCondition
        timestamp += 1
        if timestamp > 23:
            timestamp = 0
        weather = random.choice([0, 1])
        '''
        return reward, (timestamp, weather, hunger, energy, money)

