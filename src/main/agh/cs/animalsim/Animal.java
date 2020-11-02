package agh.cs.animalsim;

public class Animal {
    private Vector2d position;
    private MapDirection direction;
    private IWorldMap mapThatImOn;

    private Vector2d leftDownBound = new Vector2d(-1,-1);
    private Vector2d rightUpBound = new Vector2d(5,5);


    public Animal(IWorldMap map, Vector2d initialPosition){
        mapThatImOn = map;
        if(map.canMoveTo(initialPosition)) {
            position = new Vector2d(initialPosition);
        } else {
            position = new Vector2d(-1,-1);
        }

        direction = MapDirection.NORTH;
    }

    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2));
    }

    public Animal(){
        this(null);
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

    public Vector2d getPosition(){
        return position;
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
        if(mapThatImOn.canMoveTo(newPosition)){
            position = newPosition;
        }
    }
}