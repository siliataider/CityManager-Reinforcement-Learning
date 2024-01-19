package com.example.BackSimulation.Model.MouvableObject;

import com.example.BackSimulation.Model.API.APIopenRouteService;
import com.example.BackSimulation.Model.MapObjects.Agent;
import com.example.BackSimulation.Model.MapObjects.MapObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MouvableAgent extends MapObject implements Mouvable{
    private CoordBigDecimal dCoords;

    private List<List<BigDecimal>> path;
    private int indexPath = 0;

    private final BigDecimal deplacementMax = APIopenRouteService.deltaPoints.divide( BigDecimal.valueOf(1),10, RoundingMode.HALF_UP ) ;
    private final BigDecimal sightRadius= APIopenRouteService.deltaPoints.divide( BigDecimal.valueOf(4), 10, RoundingMode.HALF_UP) ;

    public MouvableAgent( int id, CoordBigDecimal coords){
        super(id, coords);
    }

    public void setGoal( Building building ){
        this.path = APIopenRouteService.getPathAPI(this.coords.lng.floatValue(), this.coords.lat.floatValue()
                , building.coords.lng.floatValue(), building.coords.lat.floatValue());
        System.out.println(this.path.get(0));
    }

    public BigDecimal  getDistanceNextMove(BigDecimal dx, BigDecimal dy){
        return  dx.pow(2).add(dy.pow(2)).sqrt( new MathContext(10) );
    }

    private Map<String, BigDecimal> getPoint(){
        Map<String, BigDecimal> map = new HashMap<>();

        map.put("longitude", this.path.get( this.indexPath ).get(0));
        map.put("latitude", this.path.get(this.indexPath ).get(1));

        return(map);
    }

    public void setMoveToGoal(){
        Map<String, BigDecimal> map = getPoint();

        BigDecimal dLongitude = map.get("longitude").subtract( this.coords.lng);
        BigDecimal dLatitude = map.get("latitude").subtract( this.coords.lat);

        BigDecimal distance = getDistanceNextMove(dLongitude, dLatitude);
        if (distance.compareTo(deplacementMax) < 0){
            distance = deplacementMax;
            this.indexPath++;

            if (this.indexPath >= this.path.size()) {
                System.out.println("Yay, mission acomplie !");
            }

        }

        this.dCoords.lng =  dLongitude.divide(distance, 10, RoundingMode.HALF_UP).multiply(deplacementMax);
        this.dCoords.lat = dLatitude.divide(distance,10, RoundingMode.HALF_UP).multiply(deplacementMax);
    }

    public void move(){
        this.coords.lng = this.coords.lng.add(this.dCoords.lng);
        this.coords.lat = this.coords.lat.add(this.dCoords.lat);
        this.dCoords.setZero();
    }

    @Override
    public void setObstacle(Agent otherAgent) {

    }

    private BigDecimal distancefromAgent(MouvableAgent otherAgent){
        return (
                otherAgent.coords.lng.subtract(this.coords.lng).pow(2).add(
                        otherAgent.coords.lat.subtract( this.coords.lat).pow(2)).sqrt( new MathContext(10) )
        );
    }

    private BigDecimal getPush(MouvableAgent otherAgent){
        BigDecimal distance = distancefromAgent(otherAgent);

        BigDecimal push = BigDecimal.valueOf(0);

        if ( distance.compareTo(sightRadius) <0) {
            push = distance.multiply(BigDecimal.valueOf(-1)).add(sightRadius);
        }

        return push;
    }

    public void setObstacle(MouvableAgent otherAgent){
        BigDecimal dLongitude = this.coords.lng.subtract(otherAgent.coords.lng);
        BigDecimal dLatitude = this.coords.lat.subtract(otherAgent.coords.lat);

        BigDecimal distance = getDistanceNextMove(dLongitude, dLatitude);
        BigDecimal push = getPush(otherAgent);

        BigDecimal multiply = dLongitude.divide(distance, 10, RoundingMode.HALF_UP).multiply(push);
        this.dCoords.lng = dLongitude.add(multiply);
        this.dCoords.lat = dLatitude.add(multiply);
    }

    public boolean hasArrived(){
        return this.indexPath >= this.path.size();
    }

    @Override
    public String toString() {
        return "lon : " + this.coords.lng + " dlon : " + this.dCoords.lng + " lat : " + this.coords.lat + " dlat : " + this.dCoords.lat;
    }
}
