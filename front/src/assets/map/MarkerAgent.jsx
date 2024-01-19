import { AdvancedMarker } from "@vis.gl/react-google-maps";

function MarkerAgent(props){
return  (<AdvancedMarker position={props.position} onClick={() => setOpen(true)}>
    <div style={{
      backgroundColor:props.color,
      width: 20,
      height: 20,
      borderRadius:50}}>
    </div>
    </AdvancedMarker>);
}

export default MarkerAgent;