import { AdvancedMarker } from "@vis.gl/react-google-maps";
import React, { useState, useEffect } from "react";
import {useDispatch, useSelector} from 'react-redux';
import { setCurrentAgentId } from "../canvas/drawSlice";

function MarkerAgent(props){
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [currentColor, setCurrentColor] = useState(props.color);
  const [currentTextColor, setCurrentTextColor] = useState('white');
  const current_agent_id = useSelector( (state) => state.draw.current_agent_id);
  const dispatch = useDispatch();

  useEffect(() =>{
    console.log("current_agent_id: ", current_agent_id)
    if (current_agent_id != props.id){
      setIsPopupOpen(false);
      setCurrentColor(props.color); 
      setCurrentTextColor('white');
    }
  }, [current_agent_id])

  const togglePopup = () => {
    if (!isPopupOpen) {
      setCurrentColor('yellow');
      setCurrentTextColor(props.color)
      dispatch(setCurrentAgentId(props.id));
    }
    else{
      setCurrentColor(props.color); 
      setCurrentTextColor('white')
      dispatch(setCurrentAgentId(null));
    }
    setIsPopupOpen(!isPopupOpen);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
    setCurrentColor(props.color); 
    setCurrentTextColor('white');
    dispatch(setCurrentAgentId(null));
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
            width: 35,
            height: 35,
            borderRadius: 50,
            border: `2px solid ${props.color}`,
            position: 'relative',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          <span
            style={{
              color: `${currentTextColor}`,
              fontSize: '10px',
              position: 'absolute',
            }}
          >
            {props.id}
          </span>
        </div>
      </AdvancedMarker>

      {isPopupOpen && (
        <div
        style={{
          position: "absolute",
          top: props.position.lat + 10,
          right: props.position.lng + 10,
          backgroundColor: "yellow",
          padding: "15px",
          border: "1px solid #ccc",
          zIndex: 9999,
          display: "flex",
          flexDirection: "column",
          alignItems: "flex-end",
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
            <li>Energy: {props.state?.energy*100}%</li>
            <li>Hunger: {props.state?.hunger*100}%</li>
            <li>Money: {props.state?.money*100}%</li>
          </ul>
        </div>
      )}
    </>
  );
}


export default MarkerAgent;