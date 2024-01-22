import './App.css'

import { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import MapProject from './assets/map/MapProject';
import CreationPanel from './assets/panel/CreationPanel'
import {io} from 'socket.io-client';
import socketEvents from './assets/socket/socketEvents';
import { setSocket } from './assets/socket/socketSlice';

function App() {

    const[leftPanel, setLeftPanel] = useState(0)
    const cursorObject = useSelector( (state) => state.mouse.cursorObject)
    const dispatch = useDispatch();

    useEffect(() =>{
      // SOCKET INIT :
      // SEE : https://github.com/mrniko/netty-socketio-demo
      const newSocket = io(); 
      //const newSocket = io('wss://citymanagerreact.onrender.com');
      console.log("init connection depuis react: ", newSocket)

      newSocket.on(socketEvents.connect, () => {
        console.log('Socket Connected !');
        dispatch( setSocket(newSocket) );
      });

      // INIT OF THE LEFT PANEL :
      setLeftPanel(<CreationPanel nextPanelListener={setLeftPanel} className="col"></CreationPanel>)
    }, [])

    return (
      <>
        <div className="full-screen-map">
          <MapProject></MapProject>
          <div className="floating-panel">
            {leftPanel}
          </div>
        </div>
        {cursorObject}
      </>
    );
}

export default App
