package agh.cs.animalsim;

public class Grass extends AbstractMapElement{

    protected VectorRandomizer randomer;

    public Grass(IWorldMap map, Vector2d randomMiddlePosition, int range){
        super(map);
        randomer = new VectorRandomizer(map);
        this.position = randomer.randomVectorInRangeSmart(randomMiddlePosition.subtract(new Vector2d(range, range)),
                randomMiddlePosition.add(new Vector2d(range, range)));
    }

    public Grass(IWorldMap map, Vector2d position){
        super(map, position);
    }

    public String toString(){
        return "*";
    }

    @Override
    public int getCollisionPriority() {
        return 0;
    }

    @Override
    public String getStatus() {
        return position.toString();
    }

}
