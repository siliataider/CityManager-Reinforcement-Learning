import MarkerAgent from "./MarkerAgent";
import MarkerBuilding from "./MarkerBuilding";

export function getMarkerBuilding(buildingList){
    let markerList = []
    let key = 0;
    for (const build of buildingList){
        markerList.push(<MarkerBuilding key={key} size={build.size} color={build.color} position={build.position}></MarkerBuilding> );
        key += 1;
    }
    return markerList;
}

export function getMarkerAgent(agentList){
    let markerList = []
    let key = 0;
    for (const agent of agentList){
        markerList.push(<MarkerAgent key={key} id={agent.id} color={agent.color} position={agent.position} state={agent.state} lifePoint={agent.lifePoint}></MarkerAgent> );
        key += 1;
    }
    return markerList;
}