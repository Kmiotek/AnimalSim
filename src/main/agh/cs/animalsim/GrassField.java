package agh.cs.animalsim;

import java.util.HashSet;
import java.util.Set;

public class GrassField extends AbstractWorldMap{

    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;

    public GrassField(int grass){
        super();
        for (int i =0;i<grass;i++){
            placeAnyObject(new Grass(this, new Vector2d(0,0), (int) Math.sqrt(grass*10)));
        }
        setBounds();
    }

    public void setBounds(){
        if (map.size() < 1){
            lowerLeftCorner = new Vector2d(0,0);
            upperRightCorner = new Vector2d(0,0);
            return;
        }
        Vector2d b = map.keySet().iterator().next();
        Vector2d low = new Vector2d(b);
        Vector2d high = new Vector2d(b);
        for (Vector2d a : map.keySet()) {
            low = low.lowerLeft(a);
            high = high.upperRight(a);
        }
        lowerLeftCorner = low;
        upperRightCorner = high;
    }

    public void updateBounds(Vector2d vec){
        if (!(vec.weakPrecedes(upperRightCorner) && vec.weakFollows(lowerLeftCorner))){
            upperRightCorner = upperRightCorner.upperRight(vec);
            lowerLeftCorner = lowerLeftCorner.lowerLeft(vec);
        }
    }

    @Override
    protected Vector2d lowerLeftCorner(){
        return lowerLeftCorner;
    }

    @Override
    protected Vector2d upperRightCorner(){
        return upperRightCorner;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (!isOccupied(position)){
            return true;
        }
        return objectAt(position).getCollisionPriority() < 1;
    }

    @Override
    public boolean canThisMoveTo(Vector2d position, IMapElement object) {
        if (!isOccupied(position)){
            return true;
        }
        return objectAt(position).getCollisionPriority() < object.getCollisionPriority();
    }


    public int numberOfPositionsOccupiedInSquare(Vector2d lowerLeft, Vector2d upperRight) {
        Set<Vector2d> names = new HashSet<>();
        for (Vector2d el : map.keySet()) {
            if (el.weakFollows(lowerLeft) && el.weakPrecedes(upperRight)) {
                names.add(el);
            }
        }
        return names.size();
    }

    @Override
    public IMapElement objectAt(Vector2d position) {
        Set<IMapElement> set = map.get(position);
        if (set == null){
            return null;
        }
        IMapElement current = null;
        int currentPriority = -1;
        for (IMapElement el : set) {
            if (el.getCollisionPriority() > currentPriority){
                current = el;
                currentPriority = el.getCollisionPriority();
            }
        }
        return current;
    }
}
