import useMousePosition from './assets/mouse/useMousePosition'
import './App.css'

import MapCanvas from './assets/canvas/MapCanvas';
import GamePanel from './assets/panel/GamePanel';
import CreationPanel from './assets/panel/CreationPanel'

import { useSelector } from 'react-redux';


function App() {


  const cursorObject = useSelector( (state) => state.mouse.cursorObject)

  return (
    <>

    {cursorObject}
    
    <div className='container'>
    <div className="row">

      <div className='col-4'>
      
      <CreationPanel className="col"></CreationPanel>
      {/* <GamePanel className="col"></GamePanel> */}
      </div>

      <div className='col'>
        <MapCanvas></MapCanvas>
      </div>

    </div>
    </div>

    </>
   

  )
}

export default App
