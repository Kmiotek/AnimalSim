package agh.cs.lab3;

public class Animal {
    private Vector2d position;
    private MapDirection direction;

    private Vector2d leftDownBound = new Vector2d(-1,-1);
    private Vector2d rightUpBound = new Vector2d(5,5);


    public Animal(){
        position = new Vector2d(2,2);
        direction = MapDirection.NORTH;
    }

    public String toString(){
        return ("Pozycja: " + position.toString() + ", kierunek: " + direction.toString());
    }

    public Vector2d getPosition(){
        return position;
    }

    public MapDirection getDirection(){
        return direction;
    }

    public Vector2d move(MoveDirection dir){
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
                System.out.println("We're f... fine?");
        }
        if(newPosition.follows(leftDownBound) && newPosition.precedes(rightUpBound)){
            position = new Vector2d(newPosition.x, newPosition.y);
        }
        return position;
    }
}
