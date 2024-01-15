import Cursor from "../mouse/Cursor";

import { useState } from "react";
import {useDispatch, useSelector} from 'react-redux';
import { setCursorObject, setBuildingType, switchIsDragging } from "../mouse/mouseSlice";

import BuildingType from "../buildings/BuildingType";

import socketEvents from "../socket/socketEvents";
import { setAgents } from "../canvas/drawsSlice";
import { useEffect } from "react";



const GamePanel = (props) => {

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