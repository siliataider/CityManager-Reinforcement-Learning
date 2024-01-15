import threading
from queue import Queue

def runProc(agents, simulationConditions, nbThreads=3):
     # res = []

    # for agent in agents:
    #     # Training the agent
    #     #agent.env.state_value = (simulationConditions.timestamp, simulationConditions.weather, *agent.env.state_value[2:])
    #     print(f"agent: {simulationConditions.timestamp}")
    #     agent.env.state_value = (simulationConditions.timestamp, simulationConditions.weather, *agent.env.state_value[2:])
    #     res.append(agent.train(simulationConditions))

    # Queues inits
    queueIN = Queue()
    queueOUT = Queue()
    lock = threading.Lock()

    # Thread list
    threads = []
    for i in range(nbThreads):
        thread = threading.Thread(target=runAgent, args=(lock, queueIN, queueOUT, simulationConditions))
        threads.append(thread)

    # Agents are added to queue
    for a in agents:
        queueIN.put(a)

    # Starting threads
    for thread in threads:
        thread.start()

    # Joining
    for thread in threads:
        thread.join()

    # Returning results
    results = []
    while not queueOUT.empty():
        results.append(queueOUT.get())

    return results

def runAgent(lock, queueIN, queueOUT, simulationConditions):
    #Making sure the queue is not empty
    lock.acquire()

    while not queueIN.empty():
        agent = queueIN.get(timeout=10)
        lock.release()

        # Training the agent
        res = agent.train(simulationConditions)
        print(f"agent: {agent}")

        # Return results of training
        queueOUT.put(res)
        lock.acquire()
    lock.release()