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
            //console.log(message);
            const data = JSON.parse(message);
            if (data.response == "ok"){
                // Switch left panel
                props.nextPanelListener(<GamePanel nextPanelListener={props.nextPanelListener} className="col"></GamePanel>)
            }else {
                console.log(data.message)
            }
            socket.off(socketEvents.run_simulation)
        });

        const startMessage = {nAgents : counter, explorationRateDecay: explorationRateDecay, maxTimeStep: lengthOfEpisode}

        //console.log(startMessage)

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

    function loadAlgo(){
        console.log("Loading algorithm...")
    }

    return(
    <>
        <h1>Config :</h1>
        <div className='col bg-secondary-subtle p-2'>
            <div className='mb-2'>
                <button type="button" className='btn btn-light' onClick = {decrease}>-</button>
                <label className='col-form-label bg-light'>Agents : {counter}</label>
                <button type="button" className='btn btn-light' onClick = {increase}>+</button>
            </div>
            
            <div className='mb-2'>
                <button type="button" className='btn btn-primary w-50' onClick={ () => switchCursor(BuildingType.HOME) }
                >New House</button>
            </div>

            <div className='mb-2'>
                <button className='btn btn-danger w-50' onClick={ () => switchCursor(BuildingType.FOOD)}
                >New cantine</button>
            </div>

            <div className='mb-5'>
                <button className='btn btn-success w-50' onClick={ () => switchCursor(BuildingType.JOB) }
                >New office</button>
            </div>

            <div className='form-group row mb-2'>
                <label className='col-form-label col-6'>Length of episode:</label>
                <div className="col-4">
                    <input 
                        className='form-control' 
                        type="number" 
                        id="lengthOfEp" 
                        value={lengthOfEpisode} 
                        onChange={handleChangeLengthOfEpisode}
                    />
                </div>
            </div>

            <div className='form-group row mb-5'>
                <label className='col-form-label col-6'>Exploration Rate Decay:</label>
                <div className="col-4">
                    <input 
                        className='form-control' 
                        type="number" 
                        id="lengthOfEp" 
                        value={explorationRateDecay} 
                        onChange={handleChangeExplorationRateDecay}
                    />
                </div>
            </div>

            <div className="mb-2">
                <button className="btn btn-dark w-50" onClick={loadAlgo}>Load Algorithm</button>
            </div>

            <div className='mb-2'>
                <button type="button" className="btn btn-light w-50" onClick={saveAndStart}>Save & Start</button>
            </div>

        </div>
    </>
    );
    
  };
  
  export default CreationPanel;