import useMousePosition from './assets/mouse/useMousePosition'
import './App.css'

import MapCanvas from './assets/canvas/MapCanvas';
import GamePanel from './assets/panel/GamePanel';
import CreationPanel from './assets/panel/CreationPanel'

import BuildingCreatorWidget from './assets/widgets/BuildingCreatorWidget';

import Popup from 'reactjs-popup';


import { useSelector } from 'react-redux';

import App_Stomp from './assets/socket/App_Stomp';







function App() {


  const cursorObject = useSelector( (state) => state.mouse.cursorObject)

  return (
    <>

<App_Stomp></App_Stomp>

    {cursorObject}


    {/* <Popup trigger=
                {<button> Click to open modal </button>} 
                modal nested>
                {
                    close => (
                        <div className='modal'>
                            <div className='content'>
                                Welcome to GFG!!!
                            </div>
                            <div>
                                <button onClick=
                                    {() => close()}>
                                        Close modal
                                </button>
                            </div>
                        </div>
                    )
                }
            </Popup>
    */}
    
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
