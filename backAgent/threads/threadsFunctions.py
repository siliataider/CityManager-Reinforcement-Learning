from threading import Thread
from queue import Queue

def runThreads(agents, simulationConditions, nbProc = 3):

    # Queues inits
    queueIN = Queue()
    queueOUT = Queue()

    #Process list
    process = []
    for i in range(nbProc):
        process.append( 
            Thread(target=runAgent, args=(queueIN, queueOUT, simulationConditions)) 
        )


    # Agents are added to queue
    for a in agents :
        queueIN.put(a)

    # Starting process
    for proc in process :
        proc.start()
    
    # Joining
    for proc in process :
        proc.join()

    # retuning results
    results = []
    while not queueOUT.empty() :
        results.append( queueOUT.get(timeout=10) )

    return(results)


def runAgent(queueIN, queueOUT, simulationConditions):

    #Making sure the queue is not empty
    while not queueIN.empty() :
        agent = queueIN.get(timeout=10) 

        # Training the agent
        res = agent.train(simulationConditions)
        print("Learn method from Agent : " + res )

        # Return results of training
        queueOUT.put(res)