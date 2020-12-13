package agh.cs.animalsim;

import java.util.Set;

public class GrassField extends AbstractWorldMap{

    private final MapBoundary boundaryCalculator;

    public GrassField(int grass){
        super();
        boundaryCalculator = new MapBoundary();
        for (int i =0;i<grass;i++){
            placeAnyObject(new TallGrass(this, new Vector2d(0,0), (int) Math.sqrt(grass*3000)));
        }
    }

    @Override
    public Vector2d lowerLeftCorner(){
        return boundaryCalculator.getLowerLeft();
    }

    @Override
    public Vector2d upperRightCorner(){
        return boundaryCalculator.getUpperRight();
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

    @Override
    public boolean placeAnyObject(IMapElement object){
        object.registerPositionObserver(boundaryCalculator);
        boundaryCalculator.addElement(object);
        return super.placeAnyObject(object);
    }

    @Override
    public Set<IMapElement> objectsAt(Vector2d position) {
        return map.get(position);
    }


}
