package agh.cs.animalsim;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Animal extends AbstractMapElement{
    protected Vector2f direction;

    protected int energy;
    protected int initialEnergy;
    protected float moveEfficiency; // higher -> less energy spent on moving
    protected int meatQuality; // higher -> carnivores get more energy from eating this
    protected int speed;
    protected int vision;
    protected final boolean carnivore;
    protected int chanceOfLooking; // unit -> [%]

    private boolean resting = false;

    protected IMapElement prey;
    protected IMapElement hunter;
    protected Animal mate;

    protected ArrayList<Animal> children;

    private VectorRandomizer randomizer;

    private int age = 0;

    protected boolean parentsType;


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
        children = new ArrayList<>();
        randomizer = new VectorRandomizer(map);
    }

    public Animal(IWorldMap map, Vector2d initialPosition, boolean carnivore){
        this(map, initialPosition, carnivore, 1, 10, 50000, 3000, 10, 50);
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this(map, initialPosition, false);
    }

    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2));
    }

    @Override
    public boolean isCarnivore(){
        return carnivore;
    }

    @Override
    public boolean isDead(){
        return energy <= 0;
    }

    public boolean isReadyToMate(){
        return energy > initialEnergy * 2;
    }

    public int getSpeed(){
        return speed;
    }

    public ArrayList<Animal> getChildren(){
        return children;
    }

    protected void addChild(Animal child){
        children.add(child);
    }

    public int getAge(){
        return age;
    }

    public int getEnergy(){
        return energy;
    }

    public boolean getParentsType(){
        return parentsType;
    }

    @Override
    public int getCollisionPriority() {
        return size;
    }

    @Override
    public int collisionWithHerbivore(){
        moveInDirection(MoveDirection.FORWARD);
        return 0;
    }

    @Override
    public int collisionWithCarnivore(){
        died();
        int result = meatQuality * size;
        energy = -initialEnergy;
        return result;
    }

    // VISUALIZATION

    @Override
    public String toString(){
        switch (getMapDirection()){
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
        return ("Pozycja: " + position.toString() + ", kierunek: " + getMapDirection().toString());
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
            return new Color(255, (int)Math.max(0, Math.min(200, 200*(1 - energy/(float)(initialEnergy*2)))),
                    (int)Math.max(0, Math.min(200, 200*(1 - energy/(float)(initialEnergy*2)))));
        }
        return new Color((int)Math.max(0, Math.min(200, 200*(1 - energy/(float)(initialEnergy*2)))),
                (int)Math.max(0, Math.min(200, 200*(1 - energy/(float)(initialEnergy*2)))),
                255);
        //return new Color((int)Math.max(0, 200*(1 - energy/(float)(initialEnergy*2))),
        //       (int)(200 + Math.max(0, 30*(1 - energy/(float)(initialEnergy*2)))),
        //         (int)(170 + Math.max(0, 60*(1 - energy/(float)(initialEnergy*2)))));
        // this color is nicer but when i use it differences between animals with high and those with low energy become impossible to spot
    }

    // DIRECTION MODIFICATIONS

    public MapDirection getMapDirection(){
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

    protected void updateDirection(){
        if (hunter != null){
            setDirectionAwayFrom(hunter.getPosition());
        } else if (mate != null){
            setDirectionTo(mate.getPosition());
        } else if (prey != null){
            setDirectionTo(prey.getPosition());
        } else {
            turnByRandomAngle(10);
        }
    }

    protected void setDirectionTo(Vector2d something){
        direction = new Vector2f(position.subtract(something)).normalize();
        turnByRandomAngle(50);
    }

    protected void setDirectionAwayFrom(Vector2d something){
        direction = new Vector2f(something.subtract(position)).normalize();
        turnByRandomAngle(50);
    }

    protected void turnByRandomAngle(double a){
        direction = direction.rotate(ThreadLocalRandom.current().nextDouble((-1)*Math.PI/a, Math.PI/a));
    }

    // MOVEMENT

    public void moveInDirection(MoveDirection dir){
        Vector2d newPosition = position;
        switch (dir){
            case FORWARD:
                newPosition = position.add(getMapDirection().toUnitVector().scale(speed));
                break;
            case BACKWARD:
                newPosition = position.subtract(getMapDirection().toUnitVector().scale(speed));
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
            subtractEnergyForMovement(speed);
    }

    protected void moveBasedOnKnownInformation(){
        if (prey == null && hunter == null && mate == null){
            if (!resting || energy < initialEnergy/5) {
                move();
            }
            if (ThreadLocalRandom.current().nextInt(0,100) > 95){
                resting = !resting;
            }
            return;
        }
        if (mate != null){
            double dist = distanceFrom(mate.getPosition());
            if (dist < speed){
                mateWith(mate);
            } else {
                move();
            }
            return;
        }
        if (prey == null){
            move();
            return;
        }
        double dist = distanceFrom(prey.getPosition());
        if (dist < speed){
            if (moveTo(prey.getPosition())){
                subtractEnergyForMovement(dist);
            }
        } else {
            move();
        }
    }

    public void move(){
        Vector2d newPosition = position.add(direction.multiply(speed).approx());
        if (moveTo(newPosition))
            subtractEnergyForMovement(speed);
    }

    protected boolean moveTo(Vector2d newPosition){
        if(mapThatImOn.canThisMoveTo(newPosition, this)) {
            Vector2d oldPosition = position;
            energy += interactWithObjectsOn(newPosition);
            if (energy > 7*initialEnergy){
                energy = 7*initialEnergy;
            }
            position = newPosition;
            moved(oldPosition);
            return true;
        }
        return false;
    }

    protected void subtractEnergyForMovement(double length){
        energy -= length*length*size*size/moveEfficiency;
    }

    private int interactWithObjectsOn(Vector2d newPosition){
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

    // INFORMATION GATHERING

    protected void see(){
        energy -= vision;
        Set<Vector2d> set = mapThatImOn.getObjectsPositions();
        for (Vector2d pos : set) {
            double dist = distanceFrom(pos);
            if (dist < vision){
                IMapElement mapElement = mapThatImOn.objectAt(pos);
                if (mapElement == this){
                    continue;
                }
                if (mapElement.isCarnivore() && mapElement.getCollisionPriority() >= size && !carnivore){
                    if (hunter == null || dist < distanceFrom(hunter.getPosition())) {
                        hunter = mapElement;
                    }
                } else if ( (mapElement.isGrassy() && !carnivore) || (!mapElement.isGrassy() && !mapElement.isCarnivore()
                        && carnivore && mapElement.getCollisionPriority() <= size)){
                    if (prey == null || dist < distanceFrom(prey.getPosition())){
                        prey = mapElement;
                    }
                } else if (mapElement instanceof TropicAnimal && mapElement.isCarnivore() == carnivore && isReadyToMate()
                        && ((Animal)mapElement).isReadyToMate()){
                    if (mate == null || dist < distanceFrom(mate.getPosition())){
                        mate = (Animal)mapElement;
                    }
                }
            }
        }
    }

    protected void updateMemory(){
        if (hunter != null && (hunter.isDead() || vision < distanceFrom(hunter.getPosition()))) {
            hunter = null;
        }
        if (prey != null && (prey.isDead() || vision < distanceFrom(prey.getPosition()))) {
            prey = null;
        }
        if (mate != null && (!mate.isReadyToMate() || !isReadyToMate() || vision < distanceFrom(mate.getPosition()))) {
            mate = null;
        }
    }

    // REPRODUCTION

    public void mateWith(Animal other){
        boolean willChildBeCarnivore = carnivore;
        int childSpeed = speed;
        int childSize = size;
        if (ThreadLocalRandom.current().nextInt(0 , 100) > 80){     // 20%
            willChildBeCarnivore = !willChildBeCarnivore;
        }
        if (ThreadLocalRandom.current().nextInt(0 , 100) > 50){     // 50%
            childSize = other.size;
        }
        if (ThreadLocalRandom.current().nextInt(0 , 100) > 50){     // 50%
            childSpeed = other.speed;
        }
        int randomInt = ThreadLocalRandom.current().nextInt(0 , 100);
        if (randomInt < 25 && childSpeed > 0){      // 25%
            childSpeed --;
        }
        if (randomInt > 75){        // 25%
            childSpeed ++;
        }
        randomInt = ThreadLocalRandom.current().nextInt(0 , 100);
        if (randomInt < 25 && childSize > 0){       // 25%
            childSize --;
        }
        if (randomInt > 75){        // 25%
            childSize ++;
        }
        Vector2d childPosition;
        try {
            childPosition = randomizer.randomVectorInRangeSmart(position.subtract(new Vector2d(1,1)), position.add(new Vector2d(1,1)));
        } catch (IndexOutOfBoundsException e){
            childPosition = randomizer.randomVectorInRangeStupid(mapThatImOn.lowerLeftCorner(), mapThatImOn.upperRightCorner());
        }
        TropicAnimal frog = new TropicAnimal(mapThatImOn, childPosition, willChildBeCarnivore, childSpeed, childSize,
                initialEnergy, meatQuality, moveEfficiency, chanceOfLooking, vision);
        frog.parentsType = carnivore;
        for (ILifeObserver observer : lifeObservers){
            observer.wasBorn(frog);
        }
        addChild(frog);
        other.addChild(frog);
        energy-=initialEnergy;
    }

    // OTHER

    protected double distanceFrom(Vector2d position){
        return position.dist(this.position);
    }

    @Override
    public void update(){
        if (energy > 0) {
            age++;
            updateMemory();
            int actualChanceOfLooking = chanceOfLooking;
            if (prey != null || mate != null){
                actualChanceOfLooking/=2;
            }
            if (ThreadLocalRandom.current().nextInt(0, 100) < actualChanceOfLooking){
                see();
            }
            updateDirection();
            moveBasedOnKnownInformation();
            if (energy <= 0){
                died();
            }
        }
    }


}
