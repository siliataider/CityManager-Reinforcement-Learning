from websockets.server import serve
import asyncio
from time import time
from process.processFunctions import runProc
from classes.Agent.AgentQLearning import AgentQLearning
from classes.Agent.AgentDQLearning import AgentDQLearning
from classes.SimulationConditions import SimulationConditions
from classes.AgentEnvironment import AgentEnvironment
from resources.variables import START_EXPLORATION_RATE, NUM_STATE, NUM_ACTION
import json

simulationConditions = SimulationConditions(exploration_rate=START_EXPLORATION_RATE)

#connect to socket :
async def read_socket(websocket):
    async for message in websocket:
        res = ''
        decode_message = json.loads(message)

        if decode_message['action'] == 0:
            # init agents :
            agents = []
            for data in decode_message['agents']:
                env = AgentEnvironment(data)
                if data['agent_id'] % 2:
                    agent = AgentQLearning(num_actions=NUM_ACTION, num_states=NUM_STATE, env=env, agent_id=data['agent_id'])
                else:
                    agent = AgentDQLearning(num_actions=NUM_ACTION, num_states=NUM_STATE, env=env, agent_id=data['agent_id'])
                agents.append(agent)
            simulationConditions.set_list_agent(agents)
            res = 'agent créés'

        elif decode_message['action'] == 1:
            simulationConditions.set_simulation_conditions(decode_message['data'])

            tIN = time()
            # if too much prints : https://stackoverflow.com/questions/8391411/how-to-block-calls-to-print
            simulationConditions.set_exploration_learning_rate()
            res = runProc(simulationConditions.list_agent, simulationConditions)
            tOUT = time()

            simulationConditions.list_agent = res

            print("Execution time : " + str(tOUT - tIN))

        anwser = ''
        for value in res:
            anwser += f"{value} / "
        json_data = json.dumps(anwser)
        await websocket.send(json_data)

async def main():
    async with serve(read_socket, "localhost", 8765):
        await asyncio.Future()  # run forever

asyncio.run(main())