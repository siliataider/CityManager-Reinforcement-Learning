import Building from "../buildings/Building";
import useMousePosition from "./useMousePosition";

const Cursor = (props) => {

    const { clientX, clientY } = useMousePosition();

    return <Building clientX={clientX} clientY={clientY}
    ></Building>;
    
  };
  
  export default Cursor;