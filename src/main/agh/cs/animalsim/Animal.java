package agh.cs.animalsim;

public class Animal extends AbstractMapElement{
    protected MapDirection direction;
    protected float energy;
    protected float energyRequiredToLive;
    protected int speed;
    protected int vision;
    protected boolean carnivore;



    public Animal(IWorldMap map, Vector2d initialPosition){
        super(map, initialPosition);
        direction = MapDirection.NORTH;
    }

    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2));
    }


    public void collisionWithHerbivore(){
        move(MoveDirection.FORWARD);
    }

    public void collisionWithCarnivore(){
        energy = 0;     // delete me - probably through observer
    }

    public String toString(){
        switch (direction){
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
        return ("Pozycja: " + position.toString() + ", kierunek: " + direction.toString());
    }

    public MapDirection getDirection(){
        return direction;
    }

    public void move(MoveDirection dir){
        Vector2d newPosition = position;
        switch (dir){
            case FORWARD:
                newPosition = position.add(direction.toUnitVector());
                break;
            case BACKWARD:
                newPosition = position.subtract(direction.toUnitVector());
                break;
            case LEFT:
                direction = direction.previous();
                break;
            case RIGHT:
                direction = direction.next();
                break;
            default:
                System.out.println("We're fu... fine?");
        }
        if(mapThatImOn.canThisMoveTo(newPosition, this)){
            Vector2d oldPosition = position;
            position = newPosition;
            updateObservers(oldPosition);
        }
    }


    private void onMove(){
        if (carnivore){
            observer.callCollisionWithCarnivore(position);
        } else {
            observer.callCollisionWithHerbivore(position);
        }
    }
}
