import { useEffect } from "react";
import {useDispatch, useSelector} from 'react-redux';

import socketEvents from "../socket/socketEvents";

import { setAgents } from "../canvas/drawSlice";
import CreationPanel from "./CreationPanel";



const GamePanel = (props) => {

    const dispatch = useDispatch();
    const socket = useSelector( (state) => state.socket.socket);

    function stop(){
      socket.on(socketEvents.stop_simulation, (message) => {
          console.log(message);
          const data = JSON.parse(message);
          if (data.response == "ok"){
              // Switch right panel
              props.nextPanelListener(<CreationPanel nextPanelListener={props.nextPanelListener} className="col"></CreationPanel>)
          }else {
              console.log(data.message)
          }
          socket.off(socketEvents.stop)
      });

      const stopMessage = {}

      console.log(stopMessage)

      // Ask the back if the simulation can start
      socket.emit(socketEvents.stop_simulation, JSON.stringify(stopMessage))
  }

    useEffect(() =>{
        socket.on(socketEvents.refresh_agents, (message) => {
          console.log(message)
          let data
          data = JSON.parse(message)
          let agentList = [];
          for (const agent of data.agentList){
            agentList.push({
                id: agent.id,
                x : agent.state.x,
                y : agent.state.y,
                color : "red",
                size : 10,
            })
          }
          dispatch( setAgents( agentList) );
        });
      }, [])

    return(
    <>
        <h1>Config :</h1>

        <button>Agent : 0</button>
        <button>+</button>
        <br></br>

        <button>Change Weather</button>
        <br></br>

        <button>x1</button>
        <button>x10</button>
        <br></br>
        <button>GraveYard</button>
        <br></br>
        <br></br>
        <button onClick={stop}>STOP</button>
        <br></br>
    </>
    );
    
  };
  
  export default GamePanel;