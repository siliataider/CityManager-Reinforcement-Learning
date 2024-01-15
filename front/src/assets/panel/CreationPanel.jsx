import Cursor from "../mouse/Cursor";


import {useDispatch, useSelector} from 'react-redux';
import { setCursorObject, setBuildingType, switchIsDragging } from "../mouse/mouseSlice";

import BuildingType from "../buildings/BuildingType";
import socketEvents from "../socket/socketEvents";

import GamePanel from "./GamePanel";



const CreationPanel = (props) => {

    const dispatch = useDispatch();

    const isDragging = useSelector( (state) => state.mouse.isDragging);
    const socket = useSelector( (state) => state.socket.socket);

    function switchCursor (buildingType){

        let val = null
        if (!isDragging){
            val = <Cursor></Cursor>

            dispatch(
                setBuildingType(
                    buildingType
                )
            )
        }

        dispatch( switchIsDragging() );

        dispatch(
            setCursorObject(
                val
            )
        )
    }

    /**
     * See more here : https://www.geeksforgeeks.org/how-to-disable-a-button-in-reactjs/
     */
    function getButtonStyle(color){
        return {backgroundColor : color,
            color: "white",
        };
    }

    function saveAndStart(){
        props.nextPanelListener(<GamePanel className="col"></GamePanel>)
        socket.on(socketEvents.saveAndStart, (message) => {
            if (message == "OK"){
                console.log('GO simu!');
                props.nextPanelListener(<GamePanel className="col"></GamePanel>)
                // CHANGE PANEL
            }
            socket.off(socketEvents.saveAndStart)
        });

        socket.emit(socketEvents.saveAndStart, "START ?")
    }
    


    return(
    <>
        <h1>Config :</h1>

        <button>Agent : 0</button>
        <button>+</button>
        <br></br>


        <button onClick={ () => switchCursor(BuildingType.HOME) }
        style={getButtonStyle(BuildingType.HOME[1])}
        >New House</button>
        <br></br>

        <button onClick={ () => switchCursor(BuildingType.FOOD)}
        style={getButtonStyle(BuildingType.FOOD[1])}
        >New cantine</button>
        <br></br>

    
        <button onClick={ () => switchCursor(BuildingType.JOB) }
        style={getButtonStyle(BuildingType.JOB[1])}
        >New office</button>
        <br></br>

        <button onClick={saveAndStart}>Save & Start</button>
        <br></br>
    </>
    );
    
  };
  
  export default CreationPanel;