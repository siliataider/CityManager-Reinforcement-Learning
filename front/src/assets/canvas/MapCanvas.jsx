// from https://stackoverflow.com/questions/70317280/remove-last-drawn-object-from-canvas


import { useDispatch } from "react-redux";
import { setCursorObject, switchIsDragging } from "../mouse/mouseSlice";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import { useRef } from "react";

import { addBuildings } from "./drawsSlice";

import { drawBuildings, drawAgents, clearCanavas } from "./canavasTools";
  
function MapCanvas () {

  const squareSize = 50;
    const canvasSize = 500;

    const canvasRef = useRef(null);

    const dispatch = useDispatch();

    const color = useSelector( (state) => state.mouse.color)
    const buildingType = useSelector( (state) => state.mouse.buildingType)
    const isDragging = useSelector( (state) => state.mouse.isDragging)

    const buildings = useSelector((state) => state.draw.buildings)
    useEffect( ()=> { 
      drawBuildings(buildings, canvasRef)
    }, [buildings])

    const agents = useSelector((state)=> state.draw.agents)
    useEffect( ()=> { 
      clearCanavas(canvasRef)
      drawBuildings(buildings, canvasRef)
      drawAgents(agents, canvasRef)
    }, [agents])

    // This is where all the magic happens:
    const handleClick = (ev) => {

      if(isDragging){
        dispatch(addBuildings( {
          buildingType: buildingType,
          x : ev.clientX,
          y : ev.clientY,
          color : color,
          size : squareSize
        } 
          ))

        dispatch(setCursorObject(null))
        dispatch(switchIsDragging())
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