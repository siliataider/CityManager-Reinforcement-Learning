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
              weight: 'bold',
            }}
          >
            {props.id}
          </span>
        </div>
      </AdvancedMarker>

      {isPopupOpen && (
        <div className="bg-dark-opacity text-white rounded p-4 col-2 position-absolute"
        style={{ 
          top: '100PX', 
          right: '100PX',
          zIndex: 9999 
      }}
      > 
        <button
            onClick={() => {
              closePopup();
            }}
            className="btn-close btn-close-white position-absolute top-0 end-0"
          >
          </button>
          <ul>
            <li>Energy: {props.state?.energy ? Math.round(props.state.energy * 100) : 0}%</li>
            <li>Hunger: {props.state?.hunger ? Math.round(props.state.hunger * 100) : 0}%</li>
            <li>Money: {props.state?.money ? Math.round(props.state.money * 100) : 0}%</li>
          </ul>
        </div>
      )}
    </>
  );
}


export default MarkerAgent;