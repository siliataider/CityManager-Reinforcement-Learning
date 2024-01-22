package com.example.BackSimulation.Model.MouvableObject;

import com.example.BackSimulation.Model.API.APIopenRouteService;
import com.example.BackSimulation.Model.MapObjects.Building;
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
    private boolean hasArrived = false;

    private final BigDecimal deplacementMax = APIopenRouteService.deltaPoints.divide( BigDecimal.valueOf(1),10, RoundingMode.HALF_UP ) ;
    private final BigDecimal sightRadius= APIopenRouteService.deltaPoints.divide( BigDecimal.valueOf(0.5), 10, RoundingMode.HALF_UP) ;

    public MouvableAgent( int id, CoordBigDecimal coords){
        super(id, coords);
        this.dCoords = new CoordBigDecimal(0,0);
    }

    // PUBLIC METHODES FROM MOUVABLE OBJECT :

    public void setGoal( Building building ){
        //System.out.println("API build"  + building );
        //System.out.println("CONVertion : " + building.coords.lng.doubleValue() + " " + building.coords.lat.doubleValue());

        this.path = APIopenRouteService.getPathAPI(this.coords.lng.floatValue(), this.coords.lat.floatValue()
                , building.coords.lng.floatValue(), building.coords.lat.floatValue());
        this.hasArrived = false;
        this.indexPath = 0;

        //System.out.println("Path : "  + this.path);
    }

    public void setMoveToGoal(){
        Map<String, BigDecimal> map = getNextPoint();

        BigDecimal dLongitude = map.get("longitude").subtract( this.coords.lng);
        BigDecimal dLatitude = map.get("latitude").subtract( this.coords.lat);

        BigDecimal distance = getDistanceNextMove(dLongitude, dLatitude);
        if (distance.compareTo(deplacementMax) < 0){
            distance = deplacementMax;
            this.indexPath++;

            if (this.indexPath >= this.path.size()) {
                this.hasArrived = true;
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
    public void setObstacle(MapObject otherAgent) {

    }


    public void setObstacle(MouvableAgent otherAgent){
        BigDecimal dLongitude = this.coords.lng.subtract(otherAgent.coords.lng);
        BigDecimal dLatitude = this.coords.lat.subtract(otherAgent.coords.lat);

        BigDecimal distance = getDistanceNextMove(dLongitude, dLatitude);
        BigDecimal push = getPush(otherAgent);


        if (distance.compareTo(this.sightRadius) <0) {
            try {
                this.dCoords.lng = dLongitude.add(dLongitude.divide(distance, 10, RoundingMode.HALF_UP).multiply(push));
                this.dCoords.lat = dLatitude.add(dLatitude.divide(distance, 10, RoundingMode.HALF_UP).multiply(push));
            }catch(ArithmeticException e ){
                System.out.println("Div 0 : " + e);

            }

            }

        /*

        BigDecimal multiply = dLongitude.divide(distance, 10, RoundingMode.HALF_UP).multiply(push);
        this.dCoords.lng = dLongitude.add(multiply);
        this.dCoords.lat = dLatitude.add(multiply);
         */


    }

    public boolean hasArrived(){
        return hasArrived || this.path == null;
    }


    // SOME INTERNAL METHODES

    /**
     * Lenth of vector in orthormal space (euclidiane distance)
     * @param dx
     * @param dy
     * @return
     */
    private BigDecimal  getDistanceNextMove(BigDecimal dx, BigDecimal dy){
        return  dx.pow(2).add(dy.pow(2)).sqrt( new MathContext(10) );
    }

    /**
     * Get next point to go in the path
     * @return
     */
    private Map<String, BigDecimal> getNextPoint(){
        Map<String, BigDecimal> map = new HashMap<>();

        map.put("longitude", this.path.get( this.indexPath ).get(0));
        map.put("latitude", this.path.get(this.indexPath ).get(1));

        return(map);
    }

    /**
     * Euclidiane distance between two agents : this and otherAgent
     * @param otherAgent
     * @return
     */
    private BigDecimal distancefromAgent(MouvableAgent otherAgent){
        return (
                otherAgent.coords.lng.subtract(this.coords.lng).pow(2).add(
                        otherAgent.coords.lat.subtract( this.coords.lat).pow(2)).sqrt( new MathContext(10) )
        );
    }

    /**
     * Caclulat the "push", vector of repultion of this agent aplied by another agent
     * @param otherAgent
     * @return
     */
    private BigDecimal getPush(MouvableAgent otherAgent){
        BigDecimal distance = distancefromAgent(otherAgent);

        BigDecimal push = BigDecimal.valueOf(0);

        if ( distance.compareTo(sightRadius) <0) {
            push = distance.multiply(BigDecimal.valueOf(-1)).add(sightRadius);
        }

        return push;
    }

    @Override
    public String toString() {
        return "lon : " + this.coords.lng + " dlon : "
                + this.dCoords.lng + " lat : " + this.coords.lat
                + " dlat : " + this.dCoords.lat
                + "Path : " + this.path;
    }


}
