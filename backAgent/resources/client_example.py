# Script to test the communication with the python backend 
# Deployed at https://citymanagerpython.onrender.com/

import json
import websockets
import asyncio
import random

async def hello():
    async with websockets.connect("wss://citymanagerpython.onrender.com/") as websocket:
        data = {
            'event': 'createAgent', 
            'data': {'nbAgent': 2, 'weather':0, 'timestamp': 8} 
        }
        json_data = json.dumps(data)
        await websocket.send(json_data)
        response = await websocket.recv()
        print(f"Received: {response}")

async def all_good(data):
    async with websockets.connect("wss://citymanagerpython.onrender.com/") as websocket:
        json_data = json.dumps(data)
        await websocket.send(json_data)
        response = await websocket.recv()
        print(f"Received: {response}")


if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(hello())
    for i in range(3):
        data = {
            'event': 'updateAgent', 
            'data': {'weather':random.choice([0, 1]), 'timestamp': (8+i) % 24, 'tick': i}
        }
        asyncio.get_event_loop().run_until_complete(all_good(data))
