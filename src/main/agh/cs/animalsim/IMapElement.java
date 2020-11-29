package agh.cs.animalsim;

public interface IMapElement {

    int getCollisionPriority();

    Vector2d getPosition();

    String getStatus();

    int collisionWithHerbivore();

    int collisionWithCarnivore();

    void updateObservers(Vector2d oldPosition);

    void registerPositionObserver(IPositionChangeObserver observer);

    void registerCollisionObserver(ICollisionObserver observer);

    int getDrawingSize();

    boolean isCarnivore();

    boolean isGrassy();

    void go();
}
