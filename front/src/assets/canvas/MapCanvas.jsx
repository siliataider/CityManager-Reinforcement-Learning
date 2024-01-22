// from https://stackoverflow.com/questions/70317280/remove-last-drawn-object-from-canvas
import { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { setCursorObject, switchIsDragging } from "../mouse/mouseSlice";
import { addBuildings } from "./drawSlice";
import { drawBuildings, drawAgents, clearCanavas } from "./canavasTools";
import socketEvents from "../socket/socketEvents";
  
function MapCanvas () {

    const squareSize = 50;
    const canvasSize = 500;
    // REF of the canvas is used whene the context can't be fetched
    const canvasRef = useRef(null);
    const dispatch = useDispatch();
    const color = useSelector( (state) => state.mouse.color);
    const buildingType = useSelector( (state) => state.mouse.buildingType);
    const isDragging = useSelector( (state) => state.mouse.isDragging);
    const socket = useSelector((state) => state.socket.socket);
    const buildings = useSelector((state) => state.draw.buildings);
    const agents = useSelector((state)=> state.draw.agents);
    
    // REFRESH THE CANAVS WHENE NEW BUILDING OR AGENTS
    useEffect( ()=> { 
      clearCanavas(canvasRef)
      drawBuildings(buildings, canvasRef)
      drawAgents(agents, canvasRef)
    }, [buildings, agents])

    /**
     * if possible will add a building on the canvas
     */
    const handleClick = (ev) => {
      if(isDragging){

        //Building to draw :
        const building = {
          type: buildingType,
          x : ev.clientX,
          y : ev.clientY,
          color : color,
          size : squareSize,
          openTime : 8,
          closeTime :20,
        }

        /**
         * Will wait for the back to validate the postion o a new building
         * if ok : the building is addded to the canavs
         * if not an error message is displayed
         */
        socket.on(socketEvents.new_building, (message) => {
          let data = JSON.parse(message);
          if (data.response == 'ok'){
            // draw building :
            dispatch(addBuildings( building ))
            // Switch back cursor :
            dispatch(setCursorObject(null))
            dispatch(switchIsDragging())
          } else{
            //TODO:
            alert(data.message);
          }
          // Delete listener :
          socket.off(socketEvents.new_building)
        });
        // Send the building to back
        socket.emit(socketEvents.new_building, JSON.stringify(building))
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