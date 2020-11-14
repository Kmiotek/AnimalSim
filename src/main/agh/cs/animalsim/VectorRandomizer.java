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
        int number = map.numberOfPositionsOccupied();
        int x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1 - number);
        int y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1 - number);
        int tmp = map.numberOfPositionsOccupiedInSquare(lowerLeft, upperRight);
        x += tmp;
        y += tmp;
        while(map.isOccupied(new Vector2d(x,y))){
            x++;
            y++;
        }
        return new Vector2d(x,y);
    }
}
