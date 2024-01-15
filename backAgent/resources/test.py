# Script to test the communication with the python backend 
# Deployed at https://citymanagerpython.onrender.com/

import json
import websockets
import asyncio
import random

async def hello():
    async with websockets.connect("wss://citymanagerpython.onrender.com/") as websocket:
        data = {
            'action': 0, 
            'agents': [
                {'agent_id': 0, 'weather':0, 'timestamp': 8, 'hunger': 0.5, 'energy': 0.5, 'money': 0.5 },
            ]
        }
        json_data = json.dumps(data)
        await websocket.send(json_data)
        message = await websocket.recv()
        print(f"Received: {message}")

async def all_good(data):
    async with websockets.connect("wss://citymanagerpython.onrender.com/") as websocket:
        json_data = json.dumps(data)
        await websocket.send(json_data)
        message = await websocket.recv()
        print(f"Received: {message}")

# Call your WebSocket client functions
asyncio.get_event_loop().run_until_complete(hello())


for i in range(3):
    data = {'action': 1, 'data': {'weather':random.choice([0, 1]), 'timestamp': (8+i) % 24, 'tick': 0+i}}
    asyncio.get_event_loop().run_until_complete(all_good(data))

'''
Output >>>
silia.taider@tpopt10:/mnt/home/users/silia.taider/desktop/CityManager-Reinforcement-Learning/backAgent/resources$ python test.py 
Received: "a / g / e / n / t /   / c / r / \u00e9 / \u00e9 / s / "
Received: "agent_id: 0, state: 0, state_value: (8, 0, 0.6, 0.5, 0.4) / "
Received: "agent_id: 0, state: 0, state_value: (9, 1, 0.575, 0.55, 0.4) / "
Received: "agent_id: 0, state: 0, state_value: (10, 0, 0.5249999999999999, 0.525, 0.5) / 
'''
