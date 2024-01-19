import websockets
from websockets import serve
import asyncio
from time import time
from process.processFunctions import runProc
from classes.Agent.AgentQLearning import AgentQLearning
from classes.Agent.AgentDQLearning import AgentDQLearning
from classes.SimulationConditions import SimulationConditions
from classes.AgentEnvironment import AgentEnvironment
from resources.variables import START_EXPLORATION_RATE, NUM_STATE, NUM_ACTION, NUM_STATE_DISCRETE
from resources.methods import load_and_save_model
import json

simulationConditions = SimulationConditions(exploration_rate=START_EXPLORATION_RATE)

#connect to socket :
async def read_socket(websocket):
    async for message in websocket:
        print('='*100)
        anwser = ''
        decode_message = json.loads(message)

        if decode_message['event'] == 'createAgent':
            print("Creating agents")
            # init agents :
            agents = []
            data = decode_message['data']
            simulationConditions.set_simulation_config(max_time_steps=data['maxTimeStep'], exploration_rate_decay=data['explorationRateDecay'])
            print(f"exploration_rate_decay {simulationConditions.exploration_rate_decay}, maxtimestep {simulationConditions.max_time_steps}")
            for index in range(data['nbAgent']):
                env = AgentEnvironment(timestamp=data['timestamp'], weather=data['weather'])
                if index % 2:
                    agent = AgentQLearning(num_actions=NUM_ACTION, num_states=NUM_STATE_DISCRETE, env=env, agent_id=index)
                else:
                    agent = AgentDQLearning(num_actions=NUM_ACTION, num_states=NUM_STATE, env=env, agent_id=index)
                agents.append(agent)
            simulationConditions.set_list_agent(agents)
            anwser = {
                'event': 'createAgent',
                'data': {
                    'agentList': simulationConditions.get_agents_info()
                }
            }

        elif decode_message['event'] == 'updateAgent':
            simulationConditions.set_simulation_conditions(decode_message['data'])
            simulationConditions.set_exploration_learning_rate()
            print(f"{simulationConditions} tick: {decode_message['data']['tick']}")

            tIN = time()
            # if too much prints : https://stackoverflow.com/questions/8391411/how-to-block-calls-to-print
            res = runProc(simulationConditions.list_agent, simulationConditions)
            tOUT = time()
            
            new_list_agent = []
            anwser = {
                'event': 'updateAgent',
                'data': {
                    'agentList': []
                }
            }
            for one_agent in res:
                agent_object, action = one_agent
                new_list_agent.append(agent_object)
                anwser['data']['agentList'].append(simulationConditions.get_one_agent_info(agent=agent_object, action=action))
            simulationConditions.list_agent = new_list_agent

            # Set new episode to False
            simulationConditions.is_new_episode = False
            print("Execution time : " + str(tOUT - tIN))

        elif decode_message['event'] in ['loadAgent', 'saveAgent']:
            anwser = load_and_save_model(event=decode_message['event'], simulationConditions=simulationConditions, message_data=decode_message['data'])

        anwser = json.dumps(anwser)
        await websocket.send(anwser)


# Run python backend as client
# async def connect_to_websocket():
#     uri = "wss://citymanagerjava.onrender.com/websocket-endpoint" 
#     async with websockets.connect(uri) as websocket:
#         while True:
#             # Envoyez et recevez des messages ici
#             await read_socket(websocket)

# Run python backend as server
async def listen_to_websocket():
    async with serve(read_socket, "", 8765):
        await asyncio.Future()  # run forever

if __name__ == "__main__":
    #asyncio.get_event_loop().run_until_complete(connect_to_websocket())
    asyncio.run(listen_to_websocket())
