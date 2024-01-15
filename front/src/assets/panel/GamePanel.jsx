import { useEffect } from "react";
import {useDispatch, useSelector} from 'react-redux';

import socketEvents from "../socket/socketEvents";

import { setAgents } from "../canvas/drawsSlice";



const GamePanel = () => {

    const dispatch = useDispatch();
    const socket = useSelector( (state) => state.socket.socket);

    useEffect(() =>{
        socket.on(socketEvents.refresh_agents, (message) => {
            dispatch( setAgents( JSON.parse(message)) );
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