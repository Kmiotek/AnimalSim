package agh.cs.animalsim;

public class Grass implements IMapElement{

    protected Vector2d position;
    protected IWorldMap mapThatImOn;
    protected VectorRandomizer randomer;

    public Grass(IWorldMap map, Vector2d randomMiddlePosition, int range){
        mapThatImOn = map;
        randomer = new VectorRandomizer(map);
        this.position = randomer.randomVectorInRangeSmart(randomMiddlePosition.subtract(new Vector2d(range, range)),
                randomMiddlePosition.add(new Vector2d(range, range)));
    }

    public Grass(IWorldMap map, Vector2d position){
        mapThatImOn = map;
        this.position = new Vector2d(position);
    }

    public String toString(){
        return "*";
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
        //nothing. grass doesnt care if it collides with something
    }


}
