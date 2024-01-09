import { useState } from "react";
import Building from "../buildings/Building";

const Canvas = () => {
    
    const size = 600;

    const [drawList, setDrawList] = useState([])

    function onClick(e) {

        console.log(getXYCoords(e))
        console.log( e.clientX )
        setDrawList(
            [].concat(
                drawList,

                                
            <Building
            key={drawList.length}
            clientX={e.clientX}
            clientY={e.clientY}
        ></Building>

            )
                
        )
        console.log('ON CLICK, clientX:', e.clientX)
    }

    function getXYCoords (ev, {h = 0, w = 0, centered = true} = {}) {
        console.log(ev.target.getContext())
        const ctx = ev.target.getContext('2d');

        //assertIsContext(ctx);
        const rect = ctx.canvas.getBoundingClientRect();
        const scaleX = ctx.canvas.width / rect.width;
        const scaleY = ctx.canvas.height / rect.height;
        const x = (ev.clientX - rect.left - (centered ? (w / 2) : 0)) * scaleX;
        const y = (ev.clientY - rect.top - (centered ? (h / 2) : 0)) * scaleY;

        return [x, y];
      }

    return(
        
    <div style={{ 
        width : size,
        height : size,
        backgroundColor : "grey"
    }} onClick={onClick}>

        <h1>Canavas</h1>

        {drawList}

    </div>
       
    );
    
  };
  
  export default Canvas;