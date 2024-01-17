# Script to test the communication with the python backend 
# Deployed at https://citymanagerpython.onrender.com/

import json
import websockets
import asyncio
import random

async def createAgentTest(websocket):
    # Envoyer un message au client toutes les secondes
    data = {
        'event': 'createAgent', 
        'data': {
            'nbAgent': 4, 
            'weather':0, 
            'timestamp': 8
        } 
    }
    await send_data(data=data, websocket=websocket)


async def updateAgentTest(websocket, tick):
    # Envoyer un message au client toutes les secondes

    data = {
        'event': 'updateAgent',
        'data': {
            'weather':random.choice([0, 1]),
            'timestamp': (8+tick) % 24, 
            'tick': tick
        }
    }
    await send_data(data=data, websocket=websocket)

async def saveAgentTest(websocket):
    data = {
        'event': 'saveAgent',
        'data': {
            'id': 0,
            'filename': 'agentok1'
        }
    }
    await send_data(data=data, websocket=websocket)


async def loadAgentTest(websocket):
    # data = {
    #     'event': 'loadAgent',
    #     'data': {
    #         'id': 1,
    #         'filename': 'agentok1.npy'
    #     }
    # }

    data = {
        'event': 'loadAgent',
        'data': {
            'id': 2,
            'filename': 'agentok1.keras'
        }
    }
    await send_data(data=data, websocket=websocket)


async def send_data(data, websocket):
    json_data = json.dumps(data)
    await websocket.send(json_data)
    response = await websocket.recv()
    print("="*200)
    print(response)


max_tick = 25
async def connect_to_websocket():
    uri = "ws://localhost:8765"
    async with websockets.connect(uri) as websocket:
        await createAgentTest(websocket)


        tick = 1
        done = False
        while not done:
            await updateAgentTest(websocket=websocket, tick=tick)

            tick += 1
            await asyncio.sleep(1)
            if tick == max_tick:
                done = True

        #await saveAgentTest(websocket)
        #await loadAgentTest(websocket)


if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(connect_to_websocket())
