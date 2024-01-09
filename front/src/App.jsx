import useMousePosition from './assets/mousePosition/useMousePosition'
import './App.css'
import Building from './assets/buildings/Building';

import ControlPanel from './assets/panel/ControlPanel';

import Canvas from './assets/canvas/Canavas';


function App() {

  let nbAgent = 0

  const { clientX, clientY } = useMousePosition();

  return (
    <>
    
    <div className='container'>
    <div className="row">

      <div className='col-4'>
      <ControlPanel className="col" ></ControlPanel>
      </div>

      <div className='col'>
      <Canvas></Canvas>
      </div>

    </div>
    </div>

    </>
   

  )
}

export default App
