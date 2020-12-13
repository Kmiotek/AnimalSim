package agh.cs.animalsim;

public interface ICollisionObserver {

    int callCollisionWithHerbivore(Vector2d position);

    int callCollisionWithCarnivore(Vector2d position);
}
