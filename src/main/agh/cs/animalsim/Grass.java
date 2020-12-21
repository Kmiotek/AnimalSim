package agh.cs.animalsim;

import java.awt.*;

public class Grass extends AbstractMapElement{

    protected VectorRandomizer randomizer;

    protected int nutrients;
    private boolean alive = true;

    public Grass(IWorldMap map, Vector2d randomMiddlePosition, int range, int nutrients){
        super(map);
        randomizer = new VectorRandomizer(map);
        this.position = randomizer.randomVectorInRangeStupid(randomMiddlePosition.subtract(new Vector2d(range, range)),
                randomMiddlePosition.add(new Vector2d(range, range)));
        this.nutrients = nutrients;
    }

    public Grass(IWorldMap map, Vector2d position, int nutrients){
        super(map, position);
        randomizer = new VectorRandomizer(map);
        this.position = position;
        this.nutrients = nutrients;
    }

    public Grass(IWorldMap map, Vector2d position){
        this(map, position, 7000);
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

    @Override
    public int getDrawingSize(){
        return 10;
    }

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public boolean isGrassy() {
        return true;
    }

    @Override
    public boolean isDead(){
        return !alive;
    }

    @Override
    public int collisionWithHerbivore() {
        died();
        alive = false;
        return nutrients;
    }

}
