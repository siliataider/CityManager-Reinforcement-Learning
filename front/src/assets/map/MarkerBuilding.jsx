import { AdvancedMarker } from "@vis.gl/react-google-maps";

function MarkerBuilding(props){

    return  (<AdvancedMarker position={props.position} onClick={() => setOpen(true)}>
    <div 
    style={{ 
      //position: "fixed",
      top: 0,
      bottom: 0,
      left: 0,
      right: 0,
      zIndex: 9999,
      pointerEvents: "none",
    }}
  >
    <svg
      width={props.size}
      height={props.size}
      style={{
        position: "absolute",
        transform: "translate(-50%, -50%)",
      }}
    >
      <rect
        width={""+props.size}
        height={""+props.size}
        fill="transparent"
        stroke={props.color}
        strokeWidth="10"
      />
    </svg>
  </div>

    </AdvancedMarker>);
}

export default MarkerBuilding;