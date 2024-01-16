# Script to test the communication with the python backend 
# Deployed at https://citymanagerpython.onrender.com/

import json
import websockets
import asyncio
import random

max_tick = 3

async def test_server(websocket):
    await createAgentTest(websocket)
    tick = 0
    done = False
    while not done:
        await updateAgentTest(websocket=websocket, tick=tick)
        tick += 1
        await asyncio.sleep(1)
        if tick == max_tick:
            done = True

    await saveAgentTest(websocket)
    await loadAgentTest(websocket)

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
    json_data = json.dumps(data)
    await websocket.send(json_data)
    response = await websocket.recv()
    print(response)

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

    json_data = json.dumps(data)
    await websocket.send(json_data)
    response = await websocket.recv()
    print(response)

async def saveAgentTest(websocket):
    data = {
        'event': 'saveAgent',
        'data': {
            'id': 3,
            'filename': 'agentok1'
        }
    }

    json_data = json.dumps(data)
    await websocket.send(json_data)
    response = await websocket.recv()
    print(response)

async def loadAgentTest(websocket):
    data = {
        'event': 'loadAgent',
        'data': {
            'id': 1,
            'filename': 'agentok1.npy'
        }
    }

    data = {
        'event': 'loadAgent',
        'data': {
            'id': 2,
            'filename': 'agentok1.h5'
        }
    }

    json_data = json.dumps(data)
    await websocket.send(json_data)
    response = await websocket.recv()
    print(response)

def rien(websocket):
    while True:
        pass

if __name__ == "__main__":
    start_server = websockets.serve(rien, "localhost", 8080)

    asyncio.get_event_loop().run_until_complete(start_server)
    asyncio.get_event_loop().run_forever()
