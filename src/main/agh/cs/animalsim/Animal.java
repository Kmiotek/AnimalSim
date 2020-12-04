package agh.cs.animalsim;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Animal extends AbstractMapElement{
    protected Vector2f direction;
    protected int energy;
    protected int speed;
    protected int vision;
    protected final boolean carnivore;



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

    public void see(){      //TODO this is temporary
        energy -= vision;
        Set<Vector2d> set = mapThatImOn.getObjectsPositions();
        Vector2d closestFood = null;
        double foodDist = vision + 1;
        Vector2d closestEnemy = null;
        double enemyDist = vision + 1;
        for (Vector2d pos : set) {
            if(pos.equals(this.position)){
                continue;
            }
            double dist = pos.distWithMod(position, mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()));
            if (dist < vision){
                IMapElement element = mapThatImOn.objectAt(pos);
                if (element.isCarnivore() && element.getCollisionPriority() >= size && dist < enemyDist && !carnivore){
                    closestEnemy = pos;
                    enemyDist = dist;
                } else if (element.isGrassy() && dist < foodDist && !carnivore){
                    closestFood = pos;
                    foodDist = dist;
                } else if (!element.isGrassy() && !element.isCarnivore() && dist < foodDist
                        && carnivore && element.getCollisionPriority() <= size){
                    closestFood = pos;
                    foodDist = dist;
                }
            }
        }
        if (closestEnemy != null && energy > speed*speed*size*2){
            runFrom(closestEnemy);
            speedMove();
            return;
        }
        if (closestFood != null){
            goInDirection(closestFood);
            if (foodDist < speed){
                if (moveTo(closestFood)){
                    energy -= foodDist*foodDist*size;
                }
            } else {
                speedMove();
            }
        } else {
            turnByRandomAngle(10);
            speedMove();
        }
        if (energy <= 0){
            died();
        }
    }

    @Override
    public void go(){
        if (energy > 0) {
            see();
        }
    }
}
