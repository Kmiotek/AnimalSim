package agh.cs.animalsim;

import java.awt.*;

public interface IMapElement {

    int getCollisionPriority();

    Vector2d getPosition();

    String getStatus();

    int collisionWithHerbivore();

    int collisionWithCarnivore();

    void moved(Vector2d oldPosition);

    void registerPositionObserver(IPositionChangeObserver observer);

    void registerCollisionObserver(ICollisionObserver observer);

    void registerDeathObserver(ILifeObserver observer);

    void died();

    int getDrawingSize();

    Color getColor();

    boolean isAlive();

    boolean isCarnivore();

    boolean isGrassy();

    void go();
}
