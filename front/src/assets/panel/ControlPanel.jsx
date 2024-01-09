import Building from "../buildings/Building";

import Cursor from "../mousePosition/Cursor";
import { useState } from "react";

const ControlPanel = (props) => {

    const [showBuildingCursor, setShowBuildingCursor] = useState(true)

    const [cursorObject, setCursorObject] = useState(<></>)

    function switchCursor (){

        setShowBuildingCursor( !showBuildingCursor )

        let val = <></>
        console.log(showBuildingCursor)
        if (showBuildingCursor){
            val =  <Cursor building={true} ></Cursor>
        }

        setCursorObject( val )
    }


    return(
    <>
        {cursorObject}

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