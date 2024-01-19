import {useDispatch, useSelector} from 'react-redux';
import {React, useState} from 'react';

import { setCursorObject, setBuildingType, switchIsDragging } from "../mouse/mouseSlice";

import BuildingType from "../buildings/BuildingType";
import socketEvents from "../socket/socketEvents";

import GamePanel from "./GamePanel";

import Cursor from "../mouse/Cursor";



const CreationPanel = (props) => {

    const dispatch = useDispatch();

    const isDragging = useSelector( (state) => state.mouse.isDragging);
    const socket = useSelector( (state) => state.socket.socket);

    const [counter, setCounter] = useState(1);
    const [explorationRateDecay, setExplorationRateDecay] = useState(25);
    const [lengthOfEpisode, setLengthOfEpisode] = useState(50);


    const increase = () => {
        setCounter(count => count+1)
    }

    const decrease = () => {
        if(counter > 1){
            setCounter(count => count-1)
        }
        
    }

    /**
     * Switch the cursore from dragging a building to not or the oposite
     */
    function switchCursor (buildingType){
    let val = null

    if (!isDragging){
        val = <Cursor></Cursor>
        dispatch(setBuildingType(buildingType))
    }

    dispatch( switchIsDragging() );
    dispatch(setCursorObject(val))
    }

    /**
     * Give some style to buttons
     * See more here : https://www.geeksforgeeks.org/how-to-disable-a-button-in-reactjs/
     */
    function getButtonStyle(color){
        return {backgroundColor : color,
            color: "white",
        };
    }

    /**
     * Aske the back to launch the simulation.
     * If OK : switch the left panel
     * Else : show an error message
     */
    function saveAndStart(){
        socket.on(socketEvents.run_simulation, (message) => {
            console.log(message);
            const data = JSON.parse(message);
            if (data.response == "ok"){
                // Switch left panel
                props.nextPanelListener(<GamePanel nextPanelListener={props.nextPanelListener} className="col"></GamePanel>)
            }else {
                console.log(data.message)
            }
            socket.off(socketEvents.saveAndStart)
        });

        const startMessage = {nAgents : counter, explorationRateDecay: explorationRateDecay, maxTimeStep: lengthOfEpisode}

        console.log(startMessage)

        // Ask the back if the simulation can start
        socket.emit(socketEvents.run_simulation, JSON.stringify(startMessage))
    }
    
    const handleChangeLengthOfEpisode = (event) => {
        if (event.target.value && event.target.value != 0) {
            setLengthOfEpisode(event.target.value);
        }
    };

    const handleChangeExplorationRateDecay = (event) => {
        if (event.target.value && event.target.value != 0) {
            setExplorationRateDecay(event.target.value);
        }
    
    };

    return(
    <>
        <h1>Config :</h1>
        <button onClick = {decrease}>-</button>
        <button>Agents : {counter}</button>
        <button onClick = {increase}>+</button>
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

        <label className='col-form-label'>Length of episode:</label>
        <input 
            className='form-control' 
            type="number" 
            id="lengthOfEp" 
            value={lengthOfEpisode} 
            onChange={handleChangeLengthOfEpisode}
        />
        <br></br>

        <label className='col-form-label'>Exploration Rate Decay:</label>
        <input 
            className='form-control' 
            type="number" 
            id="lengthOfEp" 
            value={explorationRateDecay} 
            onChange={handleChangeExplorationRateDecay}
        />
        <br></br>

        <button onClick={saveAndStart}>Save & Start</button>
        <br></br>
    </>
    );
    
  };
  
  export default CreationPanel;