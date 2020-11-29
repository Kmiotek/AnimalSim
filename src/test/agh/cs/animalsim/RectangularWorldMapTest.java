package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangularWorldMapTest {

    private final Vector2d poz = new Vector2d(2,3);
    private final Vector2d poz2 = new Vector2d(9, 9);

    @Test
    public void placeAndOccupiedTest(){
        RectangularWorldMap map = new RectangularWorldMap(10, 10);
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
    public void placeTest(){
        RectangularWorldMap map = new RectangularWorldMap(10, 10);
        Assertions.assertTrue(map.place((new Animal(map))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.place((new Animal(map))));
        Assertions.assertTrue(map.place((new Animal(map, poz))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(-1, 0))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(0, 10))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(10, 10))));
        Assertions.assertTrue(map.place(new Animal(map, poz2)));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(0, 0))));
    }

    @Test
    public void canMoveToTest(){
        RectangularWorldMap map = new RectangularWorldMap(10, 10);
        map.place(new Animal(map, poz));
        Assertions.assertFalse(map.canMoveTo(poz));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(3,7)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(-1, 0)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(10,10)));
    }

    @Test
    public void objectAtTest(){
        RectangularWorldMap map = new RectangularWorldMap(10, 10);
        Animal hedgehog = new Animal(map);
        map.place(new Animal(map, poz));
        map.place(hedgehog);
        Assertions.assertNotSame(map.objectAt(poz), hedgehog); //i compare references on purpose
        Assertions.assertSame(map.objectAt(new Vector2d(2, 2)), hedgehog);
        Assertions.assertNull(map.objectAt(new Vector2d(-1, -1)));
        Assertions.assertNull(map.objectAt(new Vector2d(1, 1)));
        Assertions.assertNull(map.objectAt(new Vector2d(10, 10)));
    }

    @Test
    public void simpleToStringTest(){
        String state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | | | | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        RectangularWorldMap map = new RectangularWorldMap(10,5);
        Assertions.assertEquals(map.toString(), state);
    }

    @Test
    public void complexToStringTest(){
        String state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | | | | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        RectangularWorldMap map = new RectangularWorldMap(10,5);
        Assertions.assertEquals(map.toString(), state);
        Animal donkey = new Animal(map);
        map.place(donkey);
        state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | | | | | | | | | |" + System.lineSeparator() +
                "  2: | | |^| | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
        donkey.move(MoveDirection.FORWARD);
        state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | |^| | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
        donkey.move(MoveDirection.RIGHT);
        state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | |>| | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
        donkey.move(MoveDirection.RIGHT);
        state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | |v| | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
        donkey.move(MoveDirection.RIGHT);
        state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | |<| | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
    }


}
