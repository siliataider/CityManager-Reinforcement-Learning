import { useEffect } from "react";
import {useDispatch, useSelector} from 'react-redux';

import socketEvents from "../socket/socketEvents";

import { setAgents } from "../canvas/drawSlice";



const GamePanel = () => {

    const dispatch = useDispatch();
    const socket = useSelector( (state) => state.socket.socket);

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
    </>
    );
    
  };
  
  export default GamePanel;