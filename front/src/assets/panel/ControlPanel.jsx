import Cursor from "../mouse/Cursor";

import { useState } from "react";
import {useDispatch} from 'react-redux';
import { setCursorObject, setBuildingType } from "../mouse/mouseSlice";

import BuildingType from "../buildings/BuildingType";



const ControlPanel = (props) => {

    const dispatch = useDispatch();

    const [showBuildingCursor, setShowBuildingCursor] = useState(true)

    function switchCursor (buildingType){

        setShowBuildingCursor( !showBuildingCursor )

        let val = <></>
        if (showBuildingCursor){
            val = <Cursor></Cursor>

            dispatch(
                setBuildingType(
                    buildingType
                )
            )
        }

        dispatch(
            setCursorObject(
                val
            )
        )
    }


    return(
    <>

        <h1>Config :</h1>

        <button>Agent : 0</button>
        <button>+</button>
        <br></br>

        <button onClick={ () => switchCursor(BuildingType.HOME) }>New House</button>
        <br></br>

        <button onClick={ () => switchCursor(BuildingType.FOOD)}>New cantine</button>
        <br></br>

        
        <button onClick={ () => switchCursor(BuildingType.JOB) }>New office</button>
        <br></br>
    </>

    );
    
  };
  
  export default ControlPanel;