from websockets.server import serve
import asyncio

from time import time

from process.processFunctions import runProc

from classes.Agent.AgentQLearning import AgentQLearning
from classes.Agent.AgentDQLearning import AgentDQLearning
from classes.SimulationConditions import SimulationConditions
from classes.AgentEnvironment import AgentEnvironment
from resources.variables import START_EXPLORATION_RATE
import json


# init agents :
num_state = 108
num_action = 3
agents = []
for i in range(5):
    env = AgentEnvironment()
    agents.append( AgentQLearning(num_actions=num_action, num_states=num_state, env=env) )

#connect to socket :
async def echo(websocket):
    async for message in websocket:

        decode_message = json.loads(message)
        simulationConditions = SimulationConditions(decode_message, exploration_rate=START_EXPLORATION_RATE)

        tIN = time()
        # if too much prints : https://stackoverflow.com/questions/8391411/how-to-block-calls-to-print
        simulationConditions.set_exploration_learning_rate()
        res = runProc(agents, simulationConditions)
        tOUT = time()

        print("Execution time : " + str(tOUT - tIN))

        json_data = json.dumps(res)
        await websocket.send(json_data)

async def main():
    async with serve(echo, "localhost", 8765):
        await asyncio.Future()  # run forever

asyncio.run(main())