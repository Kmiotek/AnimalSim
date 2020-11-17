package agh.cs.animalsim;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, IMapElement what);

    void callCollisionWithHerbivore(Vector2d position);

    void callCollisionWithCarnivore(Vector2d position);
}
