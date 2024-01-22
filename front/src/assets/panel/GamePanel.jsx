import { useEffect, useState } from "react";
import {useDispatch, useSelector} from 'react-redux';

import socketEvents from "../socket/socketEvents";

import { clearMapObjects, setAgents } from "../canvas/drawSlice";
import CreationPanel from "./CreationPanel";
import { clearCanavas } from "../canvas/canavasTools";
import GraphReward from "../graph/GraphReward";


const GamePanel = (props) => {

    const dispatch = useDispatch();
    const socket = useSelector( (state) => state.socket.socket);
    const agents = useSelector( (state) => state.draw.agents);

    const [speed, setSpeed] = useState(1);
    const [isAgentSet, setIsAgentSet] = useState(false);
    const [weather,setWeather] = useState("Sunny")
    const [graveyard, setGraveyard] = useState([])

    function changeWeather(){
      

      socket.on(socketEvents.change_weather, (message) => {
          //console.log(message);
          const data = JSON.parse(message);
          if (data.response == "ok"){
            setWeather(data.weather);
          }
          else{
            console.log(data.message)
          }
          socket.off(socketEvents.change_weather)
      });

      const weatherMessage = {}
      //console.log(weatherMessage)

      // Ask the back if the simulation can start
      socket.emit(socketEvents.change_weather, JSON.stringify(weatherMessage))
  }

    function changeSpeed(newSpeed){
      socket.on(socketEvents.change_speed, (message) => {
          //console.log(message);
          const data = JSON.parse(message);
          if (data.response == "ok"){
            setSpeed(newSpeed);
          }
          else{
            console.log(data.message)
          }
          socket.off(socketEvents.change_speed)
      });

      const speedMessage = {speed : speed/10}

      //console.log(speedMessage)

      // Ask the back if the simulation can start
      socket.emit(socketEvents.change_speed, JSON.stringify(speedMessage))
  }

    function stop(){
      socket.on(socketEvents.stop_simulation, (message) => {
          //console.log(message);
          const data = JSON.parse(message);
          if (data.response == "ok"){
              // Switch right panel
              props.nextPanelListener(<CreationPanel nextPanelListener={props.nextPanelListener} className="col"></CreationPanel>)
          }else {
              console.log(data.message)
          }
          dispatch(clearMapObjects())
          socket.off(socketEvents.stop)
      });

      const stopMessage = {}

      //console.log(stopMessage)

      // Ask the back if the simulation can start
      socket.emit(socketEvents.stop_simulation, JSON.stringify(stopMessage))
  }
    useEffect(() =>{
        socket.on(socketEvents.refresh_agents, (message) => {
          let liste_graveyard = []

          let data = JSON.parse(message)
          let agentList = [];
          for (const agent of data.agentList){
            //console.log(agent)
            //console.log(agent.position)
            agentList.push({
                id: agent.id,
                position: agent.position,
                color : agent.color,
                size : 10,
                rewardMoyen: agent.rewardMoyen,
                state: agent.state
            })
            if (agent.color == "black") {
              liste_graveyard.push(agent.id)
            }
          }
          setGraveyard(liste_graveyard)
          dispatch( setAgents( agentList) );
          //console.log("SET AGENTS: ", agentList)
          setIsAgentSet(true)
        });
      }, [])

    function saveAlgo(){
        console.log("Saving algorithm...")
        socket.on(socketEvents.save_agent, (message) => {
          //console.log(message);
          const data = JSON.parse(message);
          console.log(data.message)
          socket.off(socketEvents.save_agent)
      });

      const saveAgent = {id: agents[0].id, filename: "test"}

      // Ask the back if the simulation can start
      socket.emit(socketEvents.save_agent, JSON.stringify(saveAgent))
    }
    return(
    <>
        <h1>Config :</h1>
        <div className="col bg-light px-2">
          <div className="mb-2">
            <label className="col-form-label">Agent : {agents.length}</label>
          </div>

          <div className="mb-2">
            <button onClick={changeWeather} className="btn btn-primary">Change Weather</button>
            <div>Weather: {weather}</div>
          </div>
          <div className="form-group row mb-2">
            <label className ="col-form-label col-4">Simulation speed:</label>
            <div className="col-4">
              <input
                className="form-control"
                value={speed}
                type="number"
                max="100"
                min="1"
                onChange={e => changeSpeed(e.target.value)}
              />
              </div>
              <label className="col-form-label col-2">%</label>
          </div>
          <div className="mb-2">
            <label className="col-form-label">Graveyard : </label>
            <ul>
              {graveyard.map((item, index) => (
                <li key={index}>agentid: {item}</li>
              ))}
            </ul>
          </div>

          <div className="mb-2">
            <button className="btn btn-danger" onClick={stop}>STOP</button>
          </div>

          <div className="mb-2">
            {isAgentSet && (
              <div>
                <GraphReward data={agents[0].rewardMoyen} />
                <button className="btn btn-primary w-50" onClick={saveAlgo}>Save Algorithm</button>
              </div>
            )}
          </div>

        </div>
    </>
    );
    
  };
  
  export default GamePanel;