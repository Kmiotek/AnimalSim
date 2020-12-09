package agh.cs.animalsim;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Animal extends AbstractMapElement{
    protected Vector2f direction;
    protected int energy;
    protected int speed;
    protected int vision;
    protected final boolean carnivore;

    protected IMapElement prey;
    protected IMapElement hunter;



    public Animal(IWorldMap map, Vector2d initialPosition, boolean carnivore, int speed, int size){
        super(map, initialPosition);
        direction = new Vector2f(0, 1);
        this.carnivore = carnivore;
        energy = 50000;
        vision = 200;
        this.speed = speed;
        this.size = size;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, boolean carnivore, int speed){
        this(map, initialPosition, carnivore, speed, 10);
    }

    public Animal(IWorldMap map, Vector2d initialPosition, boolean carnivore){
        this(map, initialPosition, carnivore, 10);
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
        int result = 10000 * size + energy;
        energy = 0;
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
            energy -= size;
    }

    public void speedMove(){
        Vector2d newPosition = position.add(direction.multiply(speed).approx());
        if (moveTo(newPosition))
            energy -= speed*speed*size/10;
    }

    private boolean moveTo(Vector2d newPosition){
        if(mapThatImOn.canThisMoveTo(newPosition, this)) {
            Vector2d oldPosition = position;
            energy += callCollisionsOn(newPosition);
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
        return size;
    }

    @Override
    public Color getColor() {
        if (carnivore){
            return Color.RED;
        }
        return Color.BLACK;
    }

    public boolean isCarnivore(){
        return carnivore;
    }

    public void goInDirection(Vector2d something){
        direction = new Vector2f(position.distVectorWithMod(something, mapThatImOn.upperRightCorner())).normalize();
        turnByRandomAngle(50);
    }

    public void runFrom(Vector2d something){
        direction = new Vector2f(something.distVectorWithMod(position, mapThatImOn.upperRightCorner())).normalize();
        turnByRandomAngle(50);
    }

    public void turnByRandomAngle(double a){
        direction = direction.rotate(ThreadLocalRandom.current().nextDouble((-1)*Math.PI/a, Math.PI/a));
    }

    public void see(){
        energy -= vision;
        Set<Vector2d> set = mapThatImOn.getObjectsPositions();
        for (Vector2d pos : set) {
            if(pos.equals(this.position)){
                continue;
            }
            double dist = pos.distWithMod(position, mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()));
            if (dist < vision){
                IMapElement element = mapThatImOn.objectAt(pos);
                if (element.isCarnivore() && element.getCollisionPriority() >= size && !carnivore){
                    if (hunter == null || dist < hunter.getPosition().distWithMod(position, mapThatImOn.upperRightCorner())) {
                        hunter = element;
                    }
                } else if (element.isGrassy() && !carnivore){
                    if (prey == null || dist < prey.getPosition().distWithMod(position, mapThatImOn.upperRightCorner())){
                        prey = element;
                    }
                } else if (!element.isGrassy() && !element.isCarnivore()
                        && carnivore && element.getCollisionPriority() <= size){
                    if (prey == null || dist < prey.getPosition().distWithMod(position, mapThatImOn.upperRightCorner())){
                        prey = element;
                    }
                }
            }
        }
    }

    private void updateDirection(){
        if (hunter != null){
            runFrom(hunter.getPosition());
        } else if (prey != null){
            goInDirection(prey.getPosition());
        } else {
            turnByRandomAngle(10);
        }
    }


    private void makeAMove(){
        if (prey == null){
            speedMove();
            return;
        }
        double dist = prey.getPosition().distWithMod(position, mapThatImOn.upperRightCorner());
        if (dist < speed){
            if (moveTo(prey.getPosition())){
                energy -= dist*dist*size;
            }
        } else {
            speedMove();
        }
    }

    private void updateMemory(){
        if (hunter != null && (!hunter.isAlive() || hunter.getPosition().distWithMod(position, mapThatImOn.upperRightCorner()) >= vision)) {
            hunter = null;
        }
        if (prey != null && (!prey.isAlive() || prey.getPosition().distWithMod(position, mapThatImOn.upperRightCorner()) >= vision)) {
            prey = null;
        }
    }

    @Override
    public boolean isAlive(){
        return energy > 0;
    }

    @Override
    public void go(){
        if (energy > 0) {
            updateMemory();
            if (ThreadLocalRandom.current().nextInt(0, 100) > 50){
                see();
            }
            updateDirection();
            makeAMove();
            if (energy <= 0){
                died();
            }
        }
    }


}
