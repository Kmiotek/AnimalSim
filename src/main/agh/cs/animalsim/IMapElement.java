package agh.cs.animalsim;

public interface IMapElement {

    int getCollisionPriority();

    Vector2d getPosition();

    String getStatus();

    void onCollision();
}
