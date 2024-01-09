import Building from "../buildings/Building";

import Cursor from "../mouse/Cursor";


import { useState } from "react";
import {useDispatch} from 'react-redux';
import { setCursorObject } from "../mouse/mouseSlice";


const ControlPanel = (props) => {

    const dispatch = useDispatch();

    const [showBuildingCursor, setShowBuildingCursor] = useState(true)

    function switchCursor (){

        setShowBuildingCursor( !showBuildingCursor )

        let val = <></>
        if (showBuildingCursor){
            val = <Cursor building={true} ></Cursor>
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

        <button onClick={ switchCursor }>New House</button>
        <br></br>

        <button onClick={ switchCursor }>New cantine</button>
        <br></br>

        
        <button onClick={ switchCursor }>New office</button>
        <br></br>
    </>

    );
    
  };
  
  export default ControlPanel;