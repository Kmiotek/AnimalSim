package agh.cs.animalsim;

public class TropicAnimal extends Animal {


    public TropicAnimal(IWorldMap map, Vector2d initialPosition, boolean carnivore, int speed, int size, int initialEnergy,
                        int meatQuality, float moveEfficiency, int chanceOfLooking, int vision){
        super(map, initialPosition, carnivore, speed, size, initialEnergy, meatQuality, moveEfficiency, chanceOfLooking);
        this.vision = vision;
    }

    @Override
    protected double distanceFrom(Vector2d position){
        return position.distWithMod(this.position, mapThatImOn.getDimensions());
    }


    @Override
    protected void setDirectionTo(Vector2d something){
        direction = new Vector2f(position.distVectorWithMod(something, mapThatImOn.getDimensions())).normalize();
        turnByRandomAngle(50);
    }

    @Override
    protected void setDirectionAwayFrom(Vector2d something){
        direction = new Vector2f(something.distVectorWithMod(position, mapThatImOn.getDimensions())).normalize();
        turnByRandomAngle(50);
    }

}
