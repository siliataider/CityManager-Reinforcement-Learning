import Building from "../buildings/Building";
import useMousePosition from "./useMousePosition";
import { useSelector } from "react-redux";

/**
 * Generate an image that will follow the mouse
 */
const Cursor = () => {

    const { clientX, clientY } = useMousePosition();

    const color = useSelector( (state) => state.mouse.color)

    return <Building clientX={clientX} clientY={clientY} color={color}
    ></Building>;
    
  };
  
export default Cursor;