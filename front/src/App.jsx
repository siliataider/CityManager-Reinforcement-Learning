import useMousePosition from './assets/mouse/useMousePosition'
import './App.css'
import SockJsClient from 'react-stomp';

const SOCKET_URL = 'http://localhost:8080/ws-message';

import MapCanvas from './assets/canvas/MapCanvas';
import GamePanel from './assets/panel/GamePanel';
import CreationPanel from './assets/panel/CreationPanel'

import BuildingCreatorWidget from './assets/widgets/BuildingCreatorWidget';

import Popup from 'reactjs-popup';

import { useEffect } from 'react';

import {io} from 'socket.io-client';

import { useSelector } from 'react-redux';






function App() {
    console.log("LO");

      const newSocket = io();

      console.log("gf")
  
      newSocket.on('connect', () => {
        console.log('Connected with socket ID:');
      });

      newSocket.on('eventFromBack', ()=>{
        console.log("OUI")
      })




  const cursorObject = useSelector( (state) => state.mouse.cursorObject)

  return (
    <>


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
