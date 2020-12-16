package agh.cs.animalsim;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TropicAnimal extends Animal {

    private VectorRandomizer randomizer;
    private boolean resting = false;


    public TropicAnimal(IWorldMap map, Vector2d initialPosition, boolean carnivore, int speed, int size, int initialEnergy,
                        int meatQuality, float moveEfficiency, int chanceOfLooking){
        super(map, initialPosition, carnivore, speed, size, initialEnergy, meatQuality, moveEfficiency, chanceOfLooking);
        randomizer = new VectorRandomizer(map);
    }

    @Override
    protected void see(){
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

    @Override
    protected void makeAMove(){
        if (prey == null && hunter == null){
            if (!resting || energy < initialEnergy/5) {
                speedMove();
            }
            if (ThreadLocalRandom.current().nextInt(0,100) > 95){
                resting = !resting;
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
                energy -= dist*dist*size;
            }
        } else {
            speedMove();
        }
    }

    @Override
    protected void updateMemory(){
        if (hunter != null && (!hunter.isAlive() || hunter.getPosition().distWithMod(position, mapThatImOn.upperRightCorner()) >= vision)) {
            hunter = null;
        }
        if (prey != null && (!prey.isAlive() || prey.getPosition().distWithMod(position, mapThatImOn.upperRightCorner()) >= vision)) {
            prey = null;
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
    public void makeAClone(){
        boolean carn = carnivore;
        int sped = speed;
        int siz = size;
        if (ThreadLocalRandom.current().nextInt(0 , 100) > 80){
            carn = !carn;
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
        if (carn){
            //sped ++;
            //siz ++;
        }
        TropicAnimal frog = new TropicAnimal(mapThatImOn,
                randomizer.randomVectorInRangeStupid(position.subtract(new Vector2d(1,1)), position.add(new Vector2d(1,1))),
                carn, sped, siz, initialEnergy, meatQuality, moveEfficiency, chanceOfLooking);
        for(ILifeObserver observer : lifeObservers){
            observer.wasBorn(frog);
        }
        energy-=initialEnergy;
    }
}
