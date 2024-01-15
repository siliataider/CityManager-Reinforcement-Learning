# see : https://websockets.readthedocs.io/en/stable/

import json
from websockets.sync.client import connect
import random

def hello():
    with connect("ws://localhost:8080/py-message/topic") as websocket:
        #data = {'action': 1, 'data': {'weather':0, 'timestamp': 8, 'tick': 0}}
        data = {
            'action': 0, 
            'agents': [
                {'agent_id': 0, 'weather':0, 'timestamp': 8, 'hunger': 0.5, 'energy': 0.5, 'money': 0.5 }
            ]
        }
        json_data = json.dumps(data)
        websocket.send(json_data)
        websocket.send("Hola")
        message = websocket.recv()
        print(f"Received: {message}")

def all_good(data):
    with connect("ws://localhost:8765") as websocket:
        json_data = json.dumps(data)
        websocket.send(json_data)

        message = websocket.recv()
        print(f"Received: {message}")

hello()

for i in range(100):
    data = {'action': 1, 'data': {'weather':random.choice([0, 1]), 'timestamp': (8+i) % 24, 'tick': 0+i}}
    all_good(data)

