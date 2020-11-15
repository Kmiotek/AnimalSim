package agh.cs.animalsim;

public class TallGrass extends Grass{

    private int range;
    private Vector2d randomMiddlePosition;


    public TallGrass(IWorldMap map, Vector2d randomMiddlePosition, int range){
        super(map,randomMiddlePosition, range);
        this.range = range;
        this.randomMiddlePosition = randomMiddlePosition;
    }

    public TallGrass(IWorldMap map, Vector2d position){
        super(map, position);
        this.range = 1;
        this.randomMiddlePosition = position;
    }

    public String toString(){
        return "W";
    }

    @Override
    public int getCollisionPriority() {
        return 0;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String getStatus() {
        return position.toString();
    }

    @Override
    public void onCollision() {
        this.position = randomer.randomVectorInRangeSmart(randomMiddlePosition.subtract(new Vector2d(range, range)),
                randomMiddlePosition.add(new Vector2d(range, range)));
    }
}
