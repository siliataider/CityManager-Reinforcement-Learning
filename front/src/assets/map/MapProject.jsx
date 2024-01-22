import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { getMarkerAgent, getMarkerBuilding } from "./mapTools";

import socketEvents from "../socket/socketEvents";

import MarkerAgent from "./MarkerAgent";
import { addBuildings } from "../canvas/drawSlice";

import { setCursorObject, switchIsDragging } from "../mouse/mouseSlice";


// SEE : https://www.youtube.com/watch?v=PfZ4oLftItk
// SEE : https://console.cloud.google.com/google/maps-apis/home?project=front-map-411609

function MapProject () {

  // VAR FROM SLICES
  const dispatch = useDispatch();

  const socket = useSelector((state) => state.socket.socket);

  const color = useSelector( (state) => state.mouse.color);
  const buildingType = useSelector( (state) => state.mouse.buildingType);
  const isDragging = useSelector( (state) => state.mouse.isDragging);

  const agentsList = useSelector((state)=> state.draw.agents);
  const buildingsList = useSelector((state)=> state.draw.buildings);

  const position = { lat: 45.77722633389068, lng:  4.92226934948768 };

  const [markerListBuilding, setMarkerBuildingList] = useState([])
  const [markerListAgent, setMarkerAgentList] = useState([])

  const onClick = (e) =>{
    if(isDragging){
      //Building to draw :
      const building = {
        type: buildingType,
        position : e.detail.latLng,
        color : color,
        size : 40,
        openTime : 8,
        closeTime :20,
      }

      /**
       * Will wait for the back to validate the postion o a new building
       * if ok : the building is addded to the map list (a useEffect will draw it)
       * if not an error message is displayed
       */
      socket.on(socketEvents.new_building, (message) => {
        let data = JSON.parse(message);
        if (data.response == 'ok'){
          // new building added
          // Switch cursor back to normal
          dispatch(setCursorObject(null))
          dispatch(switchIsDragging())
          dispatch(addBuildings(building))

        } else{
          //TODO:
          console.log(data.message);
        }
        // Delete listener :
        socket.off(socketEvents.new_building)
      });

      // Send the building to back
      socket.emit(socketEvents.new_building, JSON.stringify(building))
    }
  }

      // drawing marker list of building
    useEffect(() =>{
      setMarkerBuildingList(getMarkerBuilding(buildingsList))
    }, [buildingsList])

    useEffect(() => {
      console.log("Agent List: ", agentsList)
      setMarkerAgentList(getMarkerAgent(agentsList))
    }, [agentsList])
  

  return (
      <APIProvider apiKey={"AIzaSyAAvMg9x2VcnKS2R5PuaiaydluXxykRFno"}>
      <div style={{ height: "100vh", width: "100%" }}>
        <Map zoom={15} center={position} mapId={"c1f6e617d042fe39"} onClick={onClick}>

          {markerListBuilding}
          {markerListAgent}

        </Map>
      </div>
    </APIProvider>
  );
}

export default MapProject;