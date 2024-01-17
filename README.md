# WORK IN PROGRESS CityManager-AI

# Trello
https://trello.com/w/citymanagergr5

## Commands
```
pip install -r requirements.txt
```

## Repository Structure
CityManager-Reinforcement-Learning<br>
├── backAgent (Python backend) <br>
├── BackSimulation (Java backend)<br>
└── front (React frontend)

## Application entry point
[https://dashboard.render.com/](https://citymanager.onrender.com)

### Deployment React
https://citymanagerreact.onrender.com

### Deployment Python
https://citymanagerpython.onrender.com

### Deployment Java
https://hub.docker.com/r/siliataider/backsimulation <br>
https://citymanagerjava.onrender.com/

### Description of java ci-cd workflow
    Build Job:
        Triggered on pushes to the main branch that include changes in the BackSimulation folder.
        Sets up JDK 17 and runs a Maven build.

    Dockerize Job:
        Depends on the successful completion of the build job.
        Logs into Docker Hub and builds & pushes a Docker image.

    Deploy Job:
        Depends on the successful completion of the dockerize job.
        Deploys the application using Render's API.

    Test Job:
        Currently, it only checks out the code and has a placeholder step for tests.
        Depends on the successful completion of the deploy job.
