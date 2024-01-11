from websockets.server import serve
import asyncio

from time import time

from process.processFunctions import runProc

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

        tIN = time()
        # if too much prints : https://stackoverflow.com/questions/8391411/how-to-block-calls-to-print
        res = runProc(agents, simulationConditions)
        tOUT = time()

        print("Execution time : " + str(tOUT - tIN))

        await websocket.send(res)

async def main():
    async with serve(echo, "localhost", 8765):
        await asyncio.Future()  # run forever

asyncio.run(main())