// from https://stackoverflow.com/questions/70317280/remove-last-drawn-object-from-canvas


import { useDispatch } from "react-redux";
import { setCursorObject, switchIsDragging } from "../mouse/mouseSlice";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import { useRef } from "react";

import { addBuildings } from "./drawsSlice";

import { drawBuildings, drawAgents, clearCanavas } from "./canavasTools";

import socketEvents from "../socket/socketEvents";
  
function MapCanvas () {

  const squareSize = 50;
    const canvasSize = 500;

    const canvasRef = useRef(null);

    const dispatch = useDispatch();

    const color = useSelector( (state) => state.mouse.color);
    const buildingType = useSelector( (state) => state.mouse.buildingType);
    const isDragging = useSelector( (state) => state.mouse.isDragging);

    const buildings = useSelector((state) => state.draw.buildings);
    useEffect( ()=> { 
      drawBuildings(buildings, canvasRef)
    }, [buildings])

    const agents = useSelector((state)=> state.draw.agents);
    useEffect( ()=> { 
      clearCanavas(canvasRef)
      drawBuildings(buildings, canvasRef)
      drawAgents(agents, canvasRef)
    }, [agents])

    const socket = useSelector((state) => state.socket.socket);
    // This is where all the magic happens:
    const handleClick = (ev) => {

      if(isDragging){

        //Building to draw :
        const building = {
          buildingType: buildingType,
          x : ev.clientX,
          y : ev.clientY,
          color : color,
          size : squareSize
        } 

          socket.on(socketEvents.new_building, (message) => {
            if (message == 'OK'){
              // draw building :
              dispatch(addBuildings( building ))
              // Switch back cursor :
              dispatch(setCursorObject(null))
              dispatch(switchIsDragging())
            } else{
              //TODO:
              console.log("Placement invalide !");
            }
            // Delete listener :
            socket.off(socketEvents.new_building)
          });
          socket.emit(socketEvents.new_building, building)

      }
    };
  
    return (
      <div style={{border: '1px solid black', display: 'inline-block'}}>
        <canvas
          ref={canvasRef}
          onClick={handleClick}
          width= {""+canvasSize}
          height={""+canvasSize}
        ></canvas>
      </div>
    );
  }

export default MapCanvas;