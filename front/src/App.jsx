import useMousePosition from './assets/mouse/useMousePosition'
import './App.css'

import MapCanvas from './assets/canvas/MapCanvas';
import ControlPanel from './assets/panel/ControlPanel';

import { setCursorObject } from './assets/mouse/mouseSlice';
import {useDispatch} from 'react-redux';
import { useSelector } from 'react-redux';




function App() {


  const cursorObject = useSelector( (state) => state.mouse.cursorObject)

  const { clientX, clientY } = useMousePosition();

  return (
    <>

    {cursorObject}
    
    <div className='container'>
    <div className="row">

      <div className='col-4'>
      <ControlPanel className="col" ></ControlPanel>
      </div>

      <div className='col'>
        <MapCanvas></MapCanvas>
      {/* <Canvas></Canvas> */}
      </div>

    </div>
    </div>

    </>
   

  )
}

export default App
