import websockets
import asyncio
from time import time
from process.processFunctions import runProc
from classes.Agent.AgentQLearning import AgentQLearning
from classes.Agent.AgentDQLearning import AgentDQLearning
from classes.SimulationConditions import SimulationConditions
from classes.AgentEnvironment import AgentEnvironment
from resources.variables import START_EXPLORATION_RATE, NUM_STATE, NUM_ACTION, NUM_STATE_DISCRETE
import json

simulationConditions = SimulationConditions(exploration_rate=START_EXPLORATION_RATE)

#connect to socket :
async def read_socket(websocket):
    async for message in websocket:
        anwser = ''
        decode_message = json.loads(message)

        if decode_message['event'] == 'createAgent':
            # init agents :
            agents = []
            data = decode_message['data']
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
            anwser = json.dumps(anwser)

        elif decode_message['event'] == 'updateAgent':
            simulationConditions.set_simulation_conditions(decode_message['data'])

            tIN = time()
            # if too much prints : https://stackoverflow.com/questions/8391411/how-to-block-calls-to-print
            simulationConditions.set_exploration_learning_rate()
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

            print("Execution time : " + str(tOUT - tIN))

            anwser = json.dumps(anwser)

        elif decode_message['event'] == 2:
            agent_object = None
            for agent in simulationConditions.list_agent:
                if agent.id == decode_message['agent_id']:
                    agent_object = agent
            if agent_object != None:
                res = agent_object.save_model()
            else: 
                anwser = 'Agent not found'

        await websocket.send(anwser)


async def connect_to_websocket():
    uri = "wss://citymanagerjava.onrender.com/websocket-endpoint" 
    async with websockets.connect(uri) as websocket:
        while True:
            # Envoyez et recevez des messages ici
            await read_socket(websocket)

if __name__ == "__main__":
    asyncio.get_event_loop().run_until_complete(connect_to_websocket())
