package agh.cs.animalsim;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.ThreadLocalRandom;

public class VectorRandomizer {

    private IWorldMap map;

    public VectorRandomizer(IWorldMap map){
        this.map = map;
    }

    public Vector2d randomVectorInRangeStupid(Vector2d lowerLeft, Vector2d upperRight){    //this is just temporary
        int x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
        int y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);
        while(map.isOccupied(new Vector2d(x,y))) {
            x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
            y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);
        }
        return new Vector2d(x,y);
    }

    public Vector2d randomVectorInRingStupid(Vector2d outerLowerLeft, Vector2d outerUpperRight,
                                                Vector2d innerLowerLeft, Vector2d innerUpperRight){
        int x = ThreadLocalRandom.current().nextInt(outerLowerLeft.x, outerUpperRight.x + 1);
        int y = ThreadLocalRandom.current().nextInt(outerLowerLeft.y, outerUpperRight.y + 1);
        Vector2d vec = new Vector2d(x,y);
        while((vec.weakFollows(innerLowerLeft) && vec.weakPrecedes(innerUpperRight)) || map.isOccupied(vec)) {
            x = ThreadLocalRandom.current().nextInt(outerLowerLeft.x, outerUpperRight.x + 1);
            y = ThreadLocalRandom.current().nextInt(outerLowerLeft.y, outerUpperRight.y + 1);
            vec = new Vector2d(x,y);
        }
        return vec;
    }

    public Vector2d randomVectorOnMapSmart(){
        return randomVectorInRangeSmart(map.lowerLeftCorner(), map.upperRightCorner());
    }

    public Vector2d randomVectorInRingHalfSmart(Vector2d outerLowerLeft, Vector2d outerUpperRight,
                                             Vector2d innerLowerLeft, Vector2d innerUpperRight){
        double chanceForInnerPoint = Math.pow(innerLowerLeft.dist(innerUpperRight)/outerLowerLeft.dist(outerUpperRight),2);
        throw new NotImplementedException();
        // TODO maybe do this?
    }

    public Vector2d randomVectorInRangeSmart(Vector2d lowerLeft, Vector2d upperRight){ // this is so slooooow
        AbstractWorldMap map = (AbstractWorldMap) (this.map);
        int taken = map.numberOfPositionsOccupiedInSquare(lowerLeft, upperRight);
        int total = (Math.abs(lowerLeft.x - upperRight.x) + 1) * (Math.abs(lowerLeft.y - upperRight.y) + 1);
        int number = total - taken;
        if (number < 1){
            throw new IndexOutOfBoundsException("The square " + lowerLeft + " " + upperRight +
                    " is full. Cannot find a free position inside to return for placement");
        }
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


    public Vector2d randomVectorInRingSmart(Vector2d outerLowerLeft, Vector2d outerUpperRight,
                                            Vector2d innerLowerLeft, Vector2d innerUpperRight){
        AbstractWorldMap map = (AbstractWorldMap) (this.map);
        int taken = map.numberOfPositionsOccupiedInSquare(outerLowerLeft, outerUpperRight)
                - map.numberOfPositionsOccupiedInSquare(innerLowerLeft, innerUpperRight);
        int total = (Math.abs(outerLowerLeft.x - outerUpperRight.x) + 1) * (Math.abs(outerLowerLeft.y - outerUpperRight.y) + 1)
                - (Math.abs(innerLowerLeft.x - innerUpperRight.x) + 1) * (Math.abs(innerLowerLeft.y - innerUpperRight.y) + 1);
        int number = total - taken;
        if (number < 1){
            throw new IndexOutOfBoundsException("The ring " + outerLowerLeft + " " + outerUpperRight +
                    " without " + innerLowerLeft + " " + innerUpperRight +
                    " is full. Cannot find a free position inside to return for placement");
        }
        int ignore = ThreadLocalRandom.current().nextInt(0, number);
        int x = outerLowerLeft.x;
        int y = outerLowerLeft.y;
        while(true){
            if (x >= innerLowerLeft.x && x <= innerUpperRight.x && y >= innerLowerLeft.y && y <= innerUpperRight.y){
                x = innerUpperRight.x + 1;
                continue;
            }
            if (!map.isOccupied(new Vector2d(x,y))){
                if (ignore > 0){
                    ignore--;
                } else {
                    break;
                }
            }
            if (x < outerUpperRight.x){
                x++;
            } else {
                y++;
                x = outerLowerLeft.x;
            }
        }
        return new Vector2d(x,y);
    }


}
