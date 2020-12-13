package agh.cs.animalsim;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, IMapElement what);

}
