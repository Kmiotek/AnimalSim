package agh.cs.animalsim;
import java.util.concurrent.ThreadLocalRandom;

public class VectorRandomizer {

    private IWorldMap map;

    public VectorRandomizer(IWorldMap map){
        this.map = map;
    }

    public Vector2d randomVectorInRangeStupid(Vector2d lowerLeft, Vector2d upperRight){    //this is just temporary
        int x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
        int y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);
        while(!map.isOccupied(new Vector2d(x,y))) {
            x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
            y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);
        }
        return new Vector2d(x,y);
    }

    public Vector2d randomVectorInRangeSmart(Vector2d lowerLeft, Vector2d upperRight){
        GrassField map = (GrassField) (this.map);
        map.setBounds();
        int taken = map.numberOfPositionsOccupiedInSquare(lowerLeft, upperRight);
        int total = (Math.abs(lowerLeft.x - upperRight.x) + 1) * (Math.abs(lowerLeft.y - upperRight.y) + 1);
        int number = total - taken;
        int ignore = ThreadLocalRandom.current().nextInt(0, number);
        int x = lowerLeft.x;
        int y = lowerLeft.y;
        while(true){
            if (!map.isOccupied(new Vector2d(x,y))){
                if (ignore > 0){
                    ignore--;
                } else {
                    break;
                }
            }
            if (x < upperRight.x){
                x++;
            } else {
                y++;
                x = lowerLeft.x;
            }
        }
        return new Vector2d(x,y);
    }
}
