import pytest
import numpy as np
from classes.Agent.AgentDQLearning import AgentDQLearning
from classes.SimulationConditions import SimulationConditions
from classes.Agent.AgentQLearning import AgentQLearning
from classes.AgentEnvironment import AgentEnvironment
from resources.variables import DISCOUNT_FACTOR, START_EXPLORATION_RATE

@pytest.fixture
def agent_q_learning():
    env = AgentEnvironment(timestamp=8, weather=0)

    # Créez une instance de AgentQLearning avec des valeurs fictives pour les paramètres nécessaires.
    return AgentQLearning(num_states=108, num_actions=3, env=env, agent_id=1)

@pytest.fixture
def agent_dq_learning():
    env = AgentEnvironment(timestamp=8, weather=0)

    # Créez une instance de AgentQLearning avec des valeurs fictives pour les paramètres nécessaires.
    return AgentDQLearning(num_states=108, num_actions=3, env=env, agent_id=1)


@pytest.fixture
def simulationConditions():
    return SimulationConditions(exploration_rate=1)


def test_choose_action_explotation_Q_learning(agent_q_learning):
    # Testez le choix d'action en exploration
    state_value = (8, 0, 0.5, 0.5, 0.5)
    exploration_rate = 0
    action = agent_q_learning.choose_action(state_value, exploration_rate)

    #Table vide donc choisit la premiere action
    assert action == 0


def test_train_model_Q_learning(agent_q_learning):
    # Testez le choix d'action en exploration
    state_value = (8, 0, 0.5, 0.5, 0.5)
    next_state_value = (9, 0, 0.6, 0.5, 0.4)
    action = 0
    reward = 15
    learning_rate = 0.1
    expected_array = [[0., 0., 0.], 
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [1.5, 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.], [0., 0., 0.],
    [0., 0., 0.], [0., 0., 0.], [0., 0., 0.]]

    agent_q_learning.train_model(state_value, action, reward, next_state_value, learning_rate, DISCOUNT_FACTOR)
    assert np.array_equal(agent_q_learning.q_table, np.array(expected_array))

def test_train_agent(simulationConditions, agent_q_learning, monkeypatch):
    agent_q_learning.env.state_value = (8, 0, 0.5, 0.5, 0.5)
    simulationConditions.timestamp = 9
    simulationConditions.weather = 0

    def mock_action(arr):
        return 0
    monkeypatch.setattr(np.random, 'choice', mock_action)

    agent_update, action = agent_q_learning.train(simulationConditions)

    assert agent_q_learning.env.state_value == (9, 0, 0.475, 0.55, 0.5)


