package models;

import java.io.Console;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agent {
    private BigDecimal longitude;
    private BigDecimal latitude;
    private BigDecimal dLongitude;
    private BigDecimal dLatitude;

    private List<List<BigDecimal>> path;
    private int indexPath = 0;

    private final BigDecimal deplacementMax = APIcaller.deltaPoints.divide( BigDecimal.valueOf(1),10, RoundingMode.HALF_UP ) ;
    private final BigDecimal sightRadius= APIcaller.deltaPoints.divide( BigDecimal.valueOf(4), 10, RoundingMode.HALF_UP) ;

    public Agent(float longitude, float latitude) {
        this.longitude = BigDecimal.valueOf(longitude);
        this.latitude = BigDecimal.valueOf(latitude);
    }

    public void setGoal( Building building ){
        this.path = APIcaller.getPathAPI(this.longitude.floatValue(), this.latitude.floatValue(), building.longitude, building.latitude);
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

        dLongitude = map.get("longitude").subtract( this.longitude);
        dLatitude = map.get("latitude").subtract( this.latitude);

        BigDecimal distance = getDistanceNextMove(dLongitude, dLatitude);
        if (distance.compareTo(deplacementMax) < 0){
            distance = deplacementMax;
            this.indexPath++;

            if (this.indexPath >= this.path.size()) {
                System.out.println("Yay, mission acomplie !");
            }

        }

        this.dLongitude =  dLongitude.divide(distance, 10, RoundingMode.HALF_UP).multiply(deplacementMax);
        this.dLatitude = dLatitude.divide(distance,10, RoundingMode.HALF_UP).multiply(deplacementMax);
    }

    public void move(){
        this.longitude = this.longitude.add(this.dLongitude);
        this.latitude = this.latitude.add(this.dLatitude);
        resetMove();
    }

    private void resetMove(){
        this.dLongitude = new BigDecimal(0);
        this.dLatitude = new BigDecimal(0);
    }

    private BigDecimal distancefromAgent(Agent otherAgent){
        return (
                otherAgent.longitude.subtract(this.longitude).pow(2).add(
                        otherAgent.latitude.subtract( this.latitude).pow(2)).sqrt( new MathContext(10) )
        );
    }

    private BigDecimal getPush(Agent otherAgent){
        BigDecimal distance = distancefromAgent(otherAgent);

        BigDecimal push = BigDecimal.valueOf(0);

        if ( distance.compareTo(sightRadius) <0) {
            push = distance.multiply(BigDecimal.valueOf(-1)).add(sightRadius);
        }

        return push;
    }

    public void setObstacle(Agent otherAgent){
        BigDecimal dx = this.longitude.subtract(otherAgent.longitude);
        BigDecimal dy = this.latitude.subtract(otherAgent.latitude);

        BigDecimal distance = getDistanceNextMove(dx, dy);
        BigDecimal push = getPush(otherAgent);

        BigDecimal multiply = dx.divide(distance, 10, RoundingMode.HALF_UP).multiply(push);
        this.dLongitude = this.dLongitude.add(multiply);
        this.dLatitude = this.dLatitude.add(multiply);
    }

    public boolean hasArrived(){
        return this.indexPath >= this.path.size();
    }

    @Override
    public String toString() {
        return "x : " + this.longitude + " dx : " + this.dLongitude + " y : " + this.latitude + " dy : " + this.dLatitude;
    }
}
