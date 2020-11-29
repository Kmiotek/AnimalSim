package agh.cs.animalsim;

import java.util.Set;

public class Animal extends AbstractMapElement{
    protected Vector2f direction;
    protected int energy;
    protected int speed;
    protected int vision;
    protected final boolean carnivore;



    public Animal(IWorldMap map, Vector2d initialPosition, boolean carnivore, int speed){
        super(map, initialPosition);
        direction = new Vector2f(0, 1);
        this.carnivore = carnivore;
        energy = 100;
        vision = 300;
        this.speed = speed;
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


    public int collisionWithHerbivore(){
        move(MoveDirection.FORWARD);
        return 0;
    }

    public int collisionWithCarnivore(){
        energy = 0;     // delete me - probably through observer
        return 500*size;
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
        if (Math.abs(direction.getY()) > Math.abs(direction.getX())){
            if (direction.getY() > 0){
                return MapDirection.NORTH;
            } else {
                return MapDirection.SOUTH;
            }
        } else {
            if (direction.getX() > 0){
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
            energy -= speed*speed*size;
    }

    private boolean moveTo(Vector2d newPosition){
        if(mapThatImOn.canThisMoveTo(newPosition, this)) {
            Vector2d oldPosition = position;
            energy += onMove(newPosition);
            position = newPosition;
            updateObservers(oldPosition);
            return true;
        }
        return false;
    }

    private int onMove(Vector2d newPosition){
        if (carnivore){
            return collisionObserver.callCollisionWithCarnivore(newPosition);
        } else {
            return collisionObserver.callCollisionWithHerbivore(newPosition);
        }
    }

    @Override
    public int getDrawingSize(){
        return size*10;
    }

    public boolean isCarnivore(){
        return carnivore;
    }

    public void goInDirection(Vector2d somethin){                                   //TODO do usuniecia i zastapienia funkcja z Vector2d
        Vector2d positio = getPosition().dualMod(mapThatImOn.upperRightCorner());
        Vector2d something = somethin.dualMod(mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()));
        int modX = mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()).getX();
        int modY = mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()).getY();
        Vector2d lowest = something;
        if (something.add(new Vector2d(0, modY)).dist(positio) < lowest.dist(positio)){
            lowest = something.add(new Vector2d(0, modY));
        }
        if (something.add(new Vector2d(0, (-1)*modY)).dist(position) < lowest.dist(position)){
            lowest = something.add(new Vector2d(0, (-1) * modY));
        }
        if (something.add(new Vector2d(modX, 8)).dist(positio) < lowest.dist(positio)){
            lowest = something.add(new Vector2d(modX, 0));
        }
        if (something.add(new Vector2d((-1) * modX, 0)).dist(positio) < lowest.dist(positio)){
            lowest = something.add(new Vector2d((-1) * modX, 0));
        }
        direction = new Vector2f(lowest.subtract(positio.dualMod(mapThatImOn.upperRightCorner()))).normalize();
    }

    public void runFrom(Vector2d somethin){                     //TODO do usuniecia i zastapienia funkcja z Vector2d
        Vector2d positio = getPosition().dualMod(mapThatImOn.upperRightCorner());
        Vector2d something = somethin.dualMod(mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()));
        int modX = mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()).getX();
        int modY = mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()).getY();
        Vector2d lowest = something;
        if (something.add(new Vector2d(0, modY)).dist(positio) < lowest.dist(positio)){
            lowest = something.add(new Vector2d(0, modY));
        }
        if (something.add(new Vector2d(0, (-1)*modY)).dist(position) < lowest.dist(position)){
            lowest = something.add(new Vector2d(0, (-1) * modY));
        }
        if (something.add(new Vector2d(modX, 8)).dist(positio) < lowest.dist(positio)){
            lowest = something.add(new Vector2d(modX, 0));
        }
        if (something.add(new Vector2d((-1) * modX, 0)).dist(positio) < lowest.dist(positio)){
            lowest = something.add(new Vector2d((-1) * modX, 0));
        }
        direction = new Vector2f(positio.subtract(lowest)).normalize();
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
            double dist = pos.distWithDualModulo(position, mapThatImOn.upperRightCorner().subtract(mapThatImOn.lowerLeftCorner()));
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
        if (closestEnemy != null){
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
            goInDirection(new Vector2d(0,0));
            speedMove();
        }

    }

    @Override
    public void go(){
        see();
    }
}
