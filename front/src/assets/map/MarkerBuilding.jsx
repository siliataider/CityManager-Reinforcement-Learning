import { AdvancedMarker } from "@vis.gl/react-google-maps";

function MarkerBuilding(props){

  let label = "";
  if (props.color === "blue") {
      label = "House";
  } else if (props.color === "green") {
      label = "Office";
  } else if (props.color === "red") {
      label = "Restaurant";
  }

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
      <text 
        x="50%" 
        y="50%" 
        alignmentBaseline="middle" 
        textAnchor="middle" 
        fill={props.color}
        fontSize="10"
        fontWeight="bold"
        style={{
            pointerEvents: "none",
        }}
    >
        {label}
    </text>
    </svg>
  </div>

    </AdvancedMarker>);
}

export default MarkerBuilding;