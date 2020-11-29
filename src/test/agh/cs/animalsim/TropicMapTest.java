package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TropicMapTest {

    private final Vector2d v_2_3 = new Vector2d(2,3);
    private final Vector2d v_9_9 = new Vector2d(9, 9);
    private final Vector2d v_3_3 = new Vector2d(3, 3);
    private final Vector2d v_3_7 = new Vector2d(3, 7);

    @Test
    public void occupiedTest(){
        TropicMap map = new TropicMap(6, 6, 5);
        Assertions.assertTrue(map.place(new Animal(map, v_2_3)));
        Assertions.assertTrue(map.isOccupied(v_2_3));
        Assertions.assertTrue(map.place(new Animal(map, v_9_9)));
        Assertions.assertTrue(map.isOccupied(v_9_9));
        Assertions.assertTrue(map.isOccupied(v_3_3));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(-1,-1))));
        System.out.println(new Vector2d(-1,-1).dualMod(v_3_3));
        Assertions.assertTrue(map.isOccupied(new Vector2d(5,5)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0,0)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0,7)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(0,-1)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(10,10)));
    }


    @Test
    public void grassAndAnimalTest(){
        GrassField map = new GrassField(0);
        Grass clover = new Grass(map, v_2_3);
        Assertions.assertTrue(map.placeAnyObject(clover));
        Assertions.assertTrue(map.isOccupied(v_2_3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> map.placeAnyObject(new Grass(map, v_2_3)));
        Animal hedgehog = new Animal(map, v_2_3);
        Assertions.assertTrue(map.placeAnyObject(hedgehog));
        Assertions.assertSame(map.objectAt(v_2_3), hedgehog);
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertSame(map.objectAt(v_2_3), clover);
        Assertions.assertSame(map.objectAt(v_2_3.add(new Vector2d(0, 1))), hedgehog);
    }
}
