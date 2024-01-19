import './App.css'

import {  useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import MapCanvas from './assets/canvas/MapCanvas';
import CreationPanel from './assets/panel/CreationPanel'

import {io} from 'socket.io-client';
import socketEvents from './assets/socket/socketEvents';
import { setSocket } from './assets/socket/socketSlice';

import { GoogleMap, Marker } from "react-google-maps"
import {
  APIProvider,
  Map,
  AdvancedMarker,
  Pin,
  InfoWindow,
} from "@vis.gl/react-google-maps";

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

    // SEE : https://www.youtube.com/watch?v=PfZ4oLftItk
    // SEE : https://console.cloud.google.com/google/maps-apis/home?project=front-map-411609
    const position = { lat: 45.77722633389068, lng:  4.92226934948768 };
    const [open, setOpen] = useState(false);


    
    const onClick = (e) =>{
      console.log(e.detail.latLng);
    }


  return (

    <>
    {cursorObject} 

  
    


    
    <div className='container'>
    <div className="row">

      <div className='col-4'>
      {leftPanel}
      </div>

      <div className='col'>
      <APIProvider apiKey={"AIzaSyAAvMg9x2VcnKS2R5PuaiaydluXxykRFno"}>
      <div style={{ height: "100vh", width: "100%" }}>
        <Map zoom={15} center={position} mapId={"c1f6e617d042fe39"} onClick={onClick}>

          
          <AdvancedMarker position={position} onClick={() => setOpen(true)}>
          <div style={{
            backgroundColor:"blue",
            width: 20,
            height: 20,
            borderRadius:50}}>
          </div>
          </AdvancedMarker>

          {open && (
            <InfoWindow position={position} onCloseClick={() => setOpen(false)}>
              <p>I'm in Hamburg</p>
            </InfoWindow>
          )}
        </Map>
      </div>
    </APIProvider>
       
      </div>

    </div>
    </div>


     </>); 
   

  
}

export default App
