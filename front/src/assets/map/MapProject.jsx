import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import { APIProvider, Map } from "@vis.gl/react-google-maps";
import { getMarkerBuilding } from "./mapTools";

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

  const position = { lat: 45.77722633389068, lng:  4.92226934948768 };
  const [open, setOpen] = useState(false);

  const [buildingList, setBuildingList] = useState([]);

  const [marketList, setMarkerList] = useState([])

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
          setBuildingList( buildingList.concat(building))

          // Switch cursor back to normal
          dispatch(setCursorObject(null))
          dispatch(switchIsDragging())

        } else{
          //TODO:
          console.log(data.message);
        }
        // Delete listener :
        socket.off(socketEvents.new_building)
      });
      console.log(building)

      // Send the building to back
      socket.emit(socketEvents.new_building, JSON.stringify(building))
    }

      // drawing marker list of building
    useEffect(() =>{
      setMarkerList(getMarkerBuilding(buildingList))
    }, [buildingList])
  }

  return (
      <APIProvider apiKey={"AIzaSyAAvMg9x2VcnKS2R5PuaiaydluXxykRFno"}>
      <div style={{ height: "100vh", width: "100%" }}>
        <Map zoom={15} center={position} mapId={"c1f6e617d042fe39"} onClick={onClick}>

          {marketList}

          {/* {open && (
            <InfoWindow position={position} onCloseClick={() => setOpen(false)}>
              <p>I AM A TEXT BOX ! PLEASE PUT SOMTHIN IN ME I FEEL EMPTY !</p>
            </InfoWindow>
          )} */}

        </Map>
      </div>
    </APIProvider>
  );
}

export default MapProject;