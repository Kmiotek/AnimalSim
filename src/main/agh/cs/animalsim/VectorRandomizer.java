package agh.cs.animalsim;
import java.util.concurrent.ThreadLocalRandom;

public class VectorRandomizer {

    private IWorldMap map;

    public VectorRandomizer(IWorldMap map){
        this.map = map;
    }

    public Vector2d randomVectorInRangeStupid(Vector2d lowerLeft, Vector2d upperRight){    //this is just temporary
        int x = ThreadLocalRandom.current().nextInt(lowerLeft.getX(), upperRight.getX() + 1);
        int y = ThreadLocalRandom.current().nextInt(lowerLeft.getY(), upperRight.getY() + 1);
        while(!map.isOccupied(new Vector2d(x,y))) {
            x = ThreadLocalRandom.current().nextInt(lowerLeft.getX(), upperRight.getX() + 1);
            y = ThreadLocalRandom.current().nextInt(lowerLeft.getY(), upperRight.getY() + 1);
        }
        return new Vector2d(x,y);
    }

    public Vector2d randomVectorInRangeSmart(Vector2d lowerLeft, Vector2d upperRight){
        GrassField map = (GrassField) (this.map);
        int taken = map.numberOfPositionsOccupiedInSquare(lowerLeft, upperRight);
        int total = (Math.abs(lowerLeft.getX() - upperRight.getX()) + 1) * (Math.abs(lowerLeft.getY() - upperRight.getY()) + 1);
        int number = total - taken;
        if (number < 1){
            throw new IndexOutOfBoundsException("The square " + lowerLeft + " " + upperRight + " is full. Cannot find a free position inside to return for placement");
        }
        int ignore = ThreadLocalRandom.current().nextInt(0, number);
        int x = lowerLeft.getX();
        int y = lowerLeft.getY();
        while(true){
            if (!map.isOccupied(new Vector2d(x,y))){
                if (ignore > 0){
                    ignore--;
                } else {
                    break;
                }
            }
            if (x < upperRight.getX()){
                x++;
            } else {
                y++;
                x = lowerLeft.getX();
            }
        }
        return new Vector2d(x,y);
    }
}
