import './App.css'

import { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import MapCanvas from './assets/canvas/MapCanvas';
import CreationPanel from './assets/panel/CreationPanel'

import {io} from 'socket.io-client';
import socketEvents from './assets/socket/socketEvents';
import { setSocket } from './assets/socket/socketSlice';




function App() {

    const[leftPanel, setLeftPanel] = useState(0)

   

    const dispatch = useDispatch();

    useEffect(() =>{
      // SEE : https://github.com/mrniko/netty-socketio-demo
      const newSocket = io(); 

      newSocket.on(socketEvents.connect, () => {
        console.log('Socket Connected !');
        dispatch( setSocket(newSocket) );
      });

     

      setLeftPanel(<CreationPanel nextPanelListener={setLeftPanel} className="col"></CreationPanel>)

    }, [])

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
      {leftPanel}
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
