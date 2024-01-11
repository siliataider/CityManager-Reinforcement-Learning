# see : https://websockets.readthedocs.io/en/stable/

import json
from websockets.sync.client import connect

def hello():
    with connect("ws://localhost:8765") as websocket:
        data = {'weather':0, 'timestamp': 8, 'tick': 0}
        json_data = json.dumps(data)
        websocket.send(json_data)
        message = websocket.recv()
        print(f"Received: {message}")

hello()