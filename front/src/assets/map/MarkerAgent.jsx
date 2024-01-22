import { AdvancedMarker } from "@vis.gl/react-google-maps";
import React, { useState, useRef } from "react";

function MarkerAgent(props){
  console.log("MarkerAgent props:", props);

  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [currentColor, setCurrentColor] = useState(props.color);

  const togglePopup = () => {
    if (!isPopupOpen) {
      setCurrentColor('yellow');
    }
    setIsPopupOpen(!isPopupOpen);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
    setCurrentColor(props.color); 
  };


  return (
    <>
      <AdvancedMarker
        position={props.position}
        onClick={() => {
          togglePopup();
        }}
      >
        <div
          style={{
            backgroundColor: currentColor,
            width: 20,
            height: 20,
            borderRadius: 50,
            border: `2px solid ${props.color}`,
          }}
        ></div>
      </AdvancedMarker>

      {isPopupOpen && (
        <div
        style={{
          position: "absolute",
          top: props.position.lat + 10,
          left: props.position.lng + 10,
          backgroundColor: "yellow",
          padding: "15px",
          border: "1px solid #ccc",
          zIndex: 9999,
          display: "flex",
          flexDirection: "column",
          alignItems: "flex-start",
        }}
        > 
        <button
            onClick={() => {
              closePopup();
            }}
            style={{
              position: "absolute",
              top: "5px",
              right: "5px",
              backgroundColor: "transparent",
              border: "none",
              cursor: "pointer",
            }}
          >
            X
          </button>
          <ul>
            <li>Energy: {props.state?.energy}</li>
            <li>Hunger: {props.state?.hunger}</li>
            <li>Money: {props.state?.money}</li>
          </ul>
        </div>
      )}
    </>
  );
}


export default MarkerAgent;