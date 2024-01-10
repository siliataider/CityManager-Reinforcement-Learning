# see : https://websockets.readthedocs.io/en/stable/
from websockets.server import serve
import asyncio

from threads.threadsFunctions import runThreads

from classes.Agent import Agent
from classes.SimulationConditions import SimulationConditions

# init agents :
agents = []
for i in range(100):
    agents.append( Agent() )

#connect to socket :
async def echo(websocket):
    async for message in websocket:
        simulationConditions = SimulationConditions(message)
        res = runThreads(agents, simulationConditions)
        await websocket.send(res)

async def main():
    async with serve(echo, "localhost", 8765):
        await asyncio.Future()  # run forever

asyncio.run(main())