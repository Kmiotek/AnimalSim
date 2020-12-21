package agh.cs.animalsim;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TropicAnimal extends Animal {

    private VectorRandomizer randomizer;
    private boolean resting = false;


    public TropicAnimal(IWorldMap map, Vector2d initialPosition, boolean carnivore, int speed, int size, int initialEnergy,
                        int meatQuality, float moveEfficiency, int chanceOfLooking, int vision){
        super(map, initialPosition, carnivore, speed, size, initialEnergy, meatQuality, moveEfficiency, chanceOfLooking);
        this.vision = vision;
        randomizer = new VectorRandomizer(map);
    }

    @Override
    protected void see(){
        energy -= vision;
        Set<Vector2d> set = mapThatImOn.getObjectsPositions();
        for (Vector2d pos : set) {
            if(pos.equals(this.position.modulo(mapThatImOn.upperRightCorner()))){
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
                } else if (!element.isGrassy() && element.isCarnivore() == carnivore && isReadyToMate() && element.isReadyToMate()){
                    if (mate == null || dist < mate.getPosition().distWithMod(position, mapThatImOn.upperRightCorner())){
                        mate = element;
                    }
                }
            }
        }
    }

    @Override
    protected void makeAMove(){
        if (prey == null && hunter == null && mate == null){
            if (!resting || energy < initialEnergy/5) {
                speedMove();
            }
            if (ThreadLocalRandom.current().nextInt(0,100) > 95){
                resting = !resting;
            }
            return;
        }
        if (mate != null){
            double dist = mate.getPosition().distWithMod(position, mapThatImOn.upperRightCorner());
            if (dist < speed){
                mateWith((Animal) mate);
            } else {
                speedMove();
            }
            return;
        }
        if (prey == null){
            speedMove();
            return;
        }
        double dist = prey.getPosition().distWithMod(position, mapThatImOn.upperRightCorner());
        if (dist < speed){
            if (moveTo(prey.getPosition())){
                energy -= dist*dist*size/moveEfficiency;
            }
        } else {
            speedMove();
        }
    }

    @Override
    protected void updateMemory(){
        if (hunter != null && (hunter.isDead() || hunter.getPosition().distWithMod(position, mapThatImOn.upperRightCorner()) >= vision)) {
            hunter = null;
        }
        if (prey != null && (prey.isDead() || prey.getPosition().distWithMod(position, mapThatImOn.upperRightCorner()) >= vision)) {
            prey = null;
        }
        if (mate != null && (!mate.isReadyToMate() || !isReadyToMate() || mate.getPosition().distWithMod(position, mapThatImOn.upperRightCorner()) >= vision)) {
            mate = null;
        }
    }

    @Override
    protected void goInDirection(Vector2d something){
        direction = new Vector2f(position.distVectorWithMod(something, mapThatImOn.upperRightCorner())).normalize();
        turnByRandomAngle(50);
    }

    @Override
    protected void runFrom(Vector2d something){
        direction = new Vector2f(something.distVectorWithMod(position, mapThatImOn.upperRightCorner())).normalize();
        turnByRandomAngle(50);
    }

    @Override
    public void mateWith(Animal other){
        boolean carn = carnivore;
        int sped = speed;
        int siz = size;
        if (ThreadLocalRandom.current().nextInt(0 , 100) > 80){
            carn = !carn;
        }
        if (ThreadLocalRandom.current().nextInt(0 , 100) > 50){
            siz = other.size;
        }
        if (ThreadLocalRandom.current().nextInt(0 , 100) > 50){
            sped = other.speed;
        }
        int r =ThreadLocalRandom.current().nextInt(0 , 100);
        if (r < 25 && sped > 0){
            sped --;
        }
        if (r > 75){
            sped ++;
        }
        int r2 =ThreadLocalRandom.current().nextInt(0 , 100);
        if (r2 < 25 && siz > 0){
            siz --;
        }
        if (r2 > 75){
            siz ++;
        }
        TropicAnimal frog = new TropicAnimal(mapThatImOn,
                randomizer.randomVectorInRangeSmart(position.subtract(new Vector2d(1,1)), position.add(new Vector2d(1,1))),
                carn, sped, siz, initialEnergy, meatQuality, moveEfficiency, chanceOfLooking, vision);
        for (ILifeObserver observer : lifeObservers){
            observer.wasBorn(frog);
        }
        addChild(frog);
        other.addChild(frog);
        energy-=initialEnergy;
    }

}
