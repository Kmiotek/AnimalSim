package agh.cs.animalsim;

public interface IMapElement {

    int getCollisionPriority();

    Vector2d getPosition();

    String getStatus();

    void collisionWithHerbivore();

    void collisionWithCarnivore();

    void updateObservers(Vector2d oldPosition);

    void addObserver(IPositionChangeObserver observer);

    void setMap(IWorldMap map);
}
