const Building = (props) => {

    console.log(props)

    const size = 50;

    return(
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
          width={size}
          height={size}
          //viewBox="0 0 100 100"
          style={{
            position: "absolute",
            left: props.clientX,
            top: props.clientY,
            transform: "translate(-50%, -50%)",
          }}
        >
          <rect
            width={""+size}
            height={""+size}
            fill="transparent"
            stroke={props.color}
            strokeWidth="10"
          />
        </svg>
      </div>
    );
    
  };
  
  export default Building;