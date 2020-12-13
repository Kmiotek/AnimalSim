package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrassFieldTest {

    private final Vector2d poz = new Vector2d(2,3);
    private final Vector2d poz2 = new Vector2d(9, 9);
    private final Vector2d poz3 = new Vector2d(3, 7);

    @Test
    public void animalPlaceAndOccupiedTest(){
        GrassField map = new GrassField(0);
        Assertions.assertTrue(map.place(new Animal(map, poz)));
        Assertions.assertTrue(map.isOccupied(poz));
        Assertions.assertTrue(map.place(new Animal(map, poz2)));
        Assertions.assertTrue(map.isOccupied(poz2));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0,0)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0,7)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0,-1)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(10,10)));
    }

    @Test
    public void grassAndAnimalTest(){
        GrassField map = new GrassField(0);
        Grass clover = new Grass(map, poz);
        Assertions.assertTrue(map.placeAnyObject(clover));
        Assertions.assertTrue(map.isOccupied(poz));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.placeAnyObject(new Grass(map, poz)));
        Animal hedgehog = new Animal(map, poz);
        Assertions.assertTrue(map.placeAnyObject(hedgehog));
        Assertions.assertSame(map.objectAt(poz), hedgehog);
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertSame(map.objectAt(poz), clover);
        Assertions.assertSame(map.objectAt(poz.add(new Vector2d(0, 1))), hedgehog);
    }


    @Test
    public void animalPlaceTest(){
        GrassField map = new GrassField(10);
        Assertions.assertTrue(map.place((new Animal(map))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.place((new Animal(map))));
        Assertions.assertTrue(map.place((new Animal(map, poz))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(-1, 0))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(0, 10))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(10, 10))));
        Assertions.assertTrue(map.place(new Animal(map, poz2)));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(0, 0))));
    }

    @Test
    public void grassPlaceTest(){
        GrassField map = new GrassField(0);
        Assertions.assertTrue(map.placeAnyObject((new Grass(map, new Vector2d(2, 2)))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.placeAnyObject((new Grass(map, new Vector2d(2, 2)))));
        Assertions.assertTrue(map.placeAnyObject((new Grass(map, poz))));
        Assertions.assertTrue(map.placeAnyObject((new Grass(map, new Vector2d(-1, 0)))));
        Assertions.assertTrue(map.placeAnyObject((new Grass(map, new Vector2d(0, 10)))));
        Assertions.assertTrue(map.placeAnyObject((new Grass(map, new Vector2d(10, 10)))));
        Assertions.assertTrue(map.placeAnyObject((new Grass(map, poz2))));
        Assertions.assertTrue(map.placeAnyObject((new Grass(map, new Vector2d(0, 0)))));
    }

    @Test
    public void canMoveToTest(){
        GrassField map = new GrassField(0);
        map.place(new Animal(map, poz));
        Assertions.assertFalse(map.canMoveTo(poz));
        Assertions.assertTrue(map.canMoveTo(poz3));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(-1, 0)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(10,10)));
    }

    @Test
    public void canThisMoveToTest(){
        GrassField map = new GrassField(0);
        Animal elephant = new Animal(map, poz);
        Animal rhino = new Animal(map, poz);
        map.place(rhino);
        Assertions.assertFalse(map.canThisMoveTo(poz, elephant));
        Assertions.assertTrue(map.canThisMoveTo(poz3, elephant));
        Assertions.assertTrue(map.canThisMoveTo(new Vector2d(-1, 0), elephant));
        Assertions.assertTrue(map.canThisMoveTo(new Vector2d(10,10), elephant));
        map.placeAnyObject(new Grass(map, poz2));
        Assertions.assertTrue(map.canThisMoveTo(poz2, elephant));
        Grass flower = new Grass(map, poz);
        Assertions.assertTrue(map.canThisMoveTo(poz3, flower));
        Assertions.assertFalse(map.canThisMoveTo(poz2, flower));
    }


    @Test
    public void simpleToStringTest(){
        String state = " y\\x  0" + System.lineSeparator() +
                "  1: ---" + System.lineSeparator() +
                "  0: | |" + System.lineSeparator() +
                " -1: ---" + System.lineSeparator();
        GrassField map = new GrassField(0);
        Assertions.assertEquals(state, map.toString());
    }

    @Test
    public void complexToStringTest(){
        String state = " y\\x 10" + System.lineSeparator() +
                "-10: ---" + System.lineSeparator() +
                "-11: |*|" + System.lineSeparator() +
                "-12: ---" + System.lineSeparator();
        GrassField map = new GrassField(0);
        map.placeAnyObject(new Grass(map, new Vector2d(10,-11)));
        Assertions.assertEquals(state, map.toString());
        state = " y\\x 10" + System.lineSeparator() +
                "-10: ---" + System.lineSeparator() +
                "-11: |^|" + System.lineSeparator() +
                "-12: ---" + System.lineSeparator();
        map.placeAnyObject(new Animal(map, new Vector2d(10,-11)));
        Assertions.assertEquals(state, map.toString());
        state = " y\\x  910" + System.lineSeparator() +
                " -5: -----" + System.lineSeparator() +
                " -6: |*| |" + System.lineSeparator() +
                " -7: | | |" + System.lineSeparator() +
                " -8: | | |" + System.lineSeparator() +
                " -9: | | |" + System.lineSeparator() +
                "-10: | | |" + System.lineSeparator() +
                "-11: | |^|" + System.lineSeparator() +
                "-12: -----" + System.lineSeparator();
        map.placeAnyObject(new Grass(map, new Vector2d(9,-6)));
        Assertions.assertEquals(state, map.toString());
    }

    @Test
    public void movementTest(){
        String state = " y\\x 10" + System.lineSeparator() +
                "-10: ---" + System.lineSeparator() +
                "-11: |*|" + System.lineSeparator() +
                "-12: ---" + System.lineSeparator();
        GrassField map = new GrassField(0);
        map.placeAnyObject(new Grass(map, new Vector2d(10,-11)));
        Assertions.assertEquals(state, map.toString());
        state = " y\\x 10" + System.lineSeparator() +
                "-10: ---" + System.lineSeparator() +
                "-11: |^|" + System.lineSeparator() +
                "-12: ---" + System.lineSeparator();
        Animal giraffe = new Animal(map, new Vector2d(10,-11));
        map.placeAnyObject(giraffe);
        Assertions.assertEquals(state, map.toString());
        giraffe.move(MoveDirection.FORWARD);
        state = " y\\x 10" + System.lineSeparator() +
                " -9: ---" + System.lineSeparator() +
                "-10: |^|" + System.lineSeparator() +
                "-11: |*|" + System.lineSeparator() +
                "-12: ---" + System.lineSeparator();
        Assertions.assertEquals(state, map.toString());
        giraffe.move(MoveDirection.RIGHT);
        state = " y\\x 10" + System.lineSeparator() +
                " -9: ---" + System.lineSeparator() +
                "-10: |>|" + System.lineSeparator() +
                "-11: |*|" + System.lineSeparator() +
                "-12: ---" + System.lineSeparator();
        Assertions.assertEquals(state, map.toString());
    }

    @Test
    public void boundaryUpdateTest(){
        GrassField map = new GrassField(0);
        Animal giraffe = new Animal(map, new Vector2d(10,-11));
        map.placeAnyObject(giraffe);
        Assertions.assertEquals(giraffe.getPosition(), map.upperRightCorner());
        Assertions.assertEquals(giraffe.getPosition(), map.lowerLeftCorner());
        giraffe.move(MoveDirection.FORWARD);
        Assertions.assertEquals(giraffe.getPosition(), map.upperRightCorner());
        Assertions.assertEquals(giraffe.getPosition(), map.lowerLeftCorner());
    }

}
