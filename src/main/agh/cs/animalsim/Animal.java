package agh.cs.animalsim;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Animal extends AbstractMapElement{
    protected Vector2f direction;
    protected int energy;
    protected int initialEnergy;
    protected float moveEfficiency; // higher -> less energy spent on moving
    protected int meatQuality; // higher -> carnivores get more energy from eating this
    protected int speed;
    protected int vision;
    protected int alertness; // higher -> checks surroundings more often, max 100
    protected double turnPreference;
    protected final boolean carnivore;
    protected int chanceOfLooking; // unit -> [%]

    protected IMapElement prey;
    protected IMapElement hunter;
    protected IMapElement mate;



    public Animal(IWorldMap map, Vector2d initialPosition, boolean carnivore, int speed, int size, int initialEnergy,
                  int meatQuality, float moveEfficiency, int chanceOfLooking){
        super(map, initialPosition);
        direction = new Vector2f(0, 1);
        this.carnivore = carnivore;
        energy = initialEnergy;
        vision = 50;
        this.speed = speed;
        this.size = size;
        this.initialEnergy = initialEnergy;
        this.meatQuality = meatQuality;
        this.moveEfficiency = moveEfficiency;
        this.chanceOfLooking = chanceOfLooking;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, boolean carnivore){
        this(map, initialPosition, carnivore, 10, 10, 50000, 3000, 10, 50);
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this(map, initialPosition, false);
    }

    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2));
    }

    @Override
    public int getCollisionPriority() {
        if (carnivore){
            return size+1;
        }
        return size;
    }

    @Override
    public int collisionWithHerbivore(){
        move(MoveDirection.FORWARD);
        return 0;
    }

    @Override
    public int collisionWithCarnivore(){
        died();
        int result = meatQuality * size;
        energy = -initialEnergy;
        return result;
    }

    public String toString(){
        switch (getDirection()){
            case NORTH:
                return "^";
            case SOUTH:
                return "v";
            case WEST:
                return "<";
            case EAST:
                return ">";
            default:
                return "o";
        }
    }

    public String getStatus(){
        return ("Pozycja: " + position.toString() + ", kierunek: " + getDirection().toString());
    }

    public MapDirection getDirection(){
        if (Math.abs(direction.y) > Math.abs(direction.x)){
            if (direction.y > 0){
                return MapDirection.NORTH;
            } else {
                return MapDirection.SOUTH;
            }
        } else {
            if (direction.x > 0){
                return MapDirection.EAST;
            } else {
                return MapDirection.WEST;
            }
        }
    }

    public void move(MoveDirection dir){
        Vector2d newPosition = position;
        switch (dir){
            case FORWARD:
                newPosition = position.add(getDirection().toUnitVector());
                break;
            case BACKWARD:
                newPosition = position.subtract(getDirection().toUnitVector());
                break;
            case LEFT:
                direction = direction.left90deg();
                break;
            case RIGHT:
                direction = direction.right90deg();
                break;
            default:
                System.out.println("We're fu... fine?");
        }
        if (moveTo(newPosition))
            energy -= size/moveEfficiency;
    }

    public void speedMove(){
        Vector2d newPosition = position.add(direction.multiply(speed).approx());
        if (moveTo(newPosition))
            energy -= speed*speed*size/moveEfficiency;
    }

    protected boolean moveTo(Vector2d newPosition){
        if(mapThatImOn.canThisMoveTo(newPosition, this)) {
            Vector2d oldPosition = position;
            energy += callCollisionsOn(newPosition);
            if (energy > 7*initialEnergy){
                energy = 7*initialEnergy;
            }
            position = newPosition;
            moved(oldPosition);
            return true;
        }
        return false;
    }

    private int callCollisionsOn(Vector2d newPosition){
        int energyGathered = 0;
        if (carnivore){
            for (ICollisionObserver observer : collisionObservers){
                energyGathered += observer.callCollisionWithCarnivore(newPosition);
            }
        } else {
            for (ICollisionObserver observer : collisionObservers){
                energyGathered += observer.callCollisionWithHerbivore(newPosition);
            }
        }
        return energyGathered;
    }

    @Override
    public int getDrawingSize(){
        if (highlighted){
            return size*4;
        }
        return size;
    }

    @Override
    public Color getColor() {
        if (carnivore){
            return Color.RED;
        }
        return Color.CYAN;
    }

    public boolean isCarnivore(){
        return carnivore;
    }

    protected void goInDirection(Vector2d something){
        direction = new Vector2f(position.subtract(something)).normalize();
        turnByRandomAngle(50);
    }

    protected void runFrom(Vector2d something){
        direction = new Vector2f(something.subtract(position)).normalize();
        turnByRandomAngle(50);
    }

    protected void turnByRandomAngle(double a){
        direction = direction.rotate(ThreadLocalRandom.current().nextDouble((-1)*Math.PI/a, Math.PI/a));
    }

    protected void see(){

    }

    protected void updateDirection(){
        if (hunter != null){
            runFrom(hunter.getPosition());
        } else if (mate != null){
            goInDirection(mate.getPosition());
        } else if (prey != null){
            goInDirection(prey.getPosition());
        } else {
            turnByRandomAngle(10);
        }
    }


    protected void makeAMove(){
        speedMove();
    }

    protected void updateMemory(){

    }

    @Override
    public boolean isDead(){
        return energy <= 0;
    }

    @Override
    public boolean isReadyToMate(){
        return energy > initialEnergy * 2;
    }

    @Override
    public void go(){
        if (energy > 0) {
            updateMemory();
            int actualChanceOfLooking = chanceOfLooking;
            if (prey != null || mate != null){
                actualChanceOfLooking/=2;
            }
            if (ThreadLocalRandom.current().nextInt(0, 100) < actualChanceOfLooking){
                see();
            }
            updateDirection();
            makeAMove();
            if (energy <= 0){
                died();
            }
        }
    }


    public void mateWith(Animal other){
        Animal frog = new Animal(mapThatImOn);
        for (ILifeObserver observer : lifeObservers){
            observer.wasBorn(frog);
        }
        energy-=initialEnergy;
    }


}
