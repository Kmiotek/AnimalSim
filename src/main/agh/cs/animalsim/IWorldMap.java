package agh.cs.animalsim;

import java.util.Set;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     *
     * This now assumes priority of the object = 1 - Jakub Kmiecik
     */
    boolean canMoveTo(Vector2d position);

    boolean canThisMoveTo(Vector2d position, IMapElement object);

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    boolean place(Animal animal);

    Set<Vector2d> getObjectsPositions();

    Vector2d lowerLeftCorner();

    Vector2d upperRightCorner();

    boolean placeAnyObject(IMapElement object);

    void display();

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    IMapElement objectAt(Vector2d position);

    Set<IMapElement> objectsAt(Vector2d position);
}