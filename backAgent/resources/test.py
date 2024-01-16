# Script to test the communication with the python backend 
# Deployed at https://citymanagerpython.onrender.com/

import json
import websockets
import asyncio
import random

async def test_server(websocket):
    # Envoyer un message au client toutes les secondes
    data = {
            'event': 'createAgent', 
            'data': {'nbAgent': 2, 'weather':0, 'timestamp': 8} 
        }
    json_data = json.dumps(data)
    await websocket.send(json_data)
    response = await websocket.recv()
    print(response)
    i = 0
    done = False
    while not done:
        # Envoyer un message au client toutes les secondes

        data = {'event': 'updateAgent', 'data': {'weather':random.choice([0, 1]), 'timestamp': (8+i) % 24, 'tick': i}}

        json_data = json.dumps(data)
        await websocket.send(json_data)
        response = await websocket.recv()
        print(response)

        i += 1
        await asyncio.sleep(1)
        if i == 2:
            done = True

if __name__ == "__main__":
    start_server = websockets.serve(test_server, "localhost", 8080)

    asyncio.get_event_loop().run_until_complete(start_server)
    asyncio.get_event_loop().run_forever()
