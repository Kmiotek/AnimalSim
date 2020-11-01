package agh.cs.lab3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Vector2dTest {
    @Test
    public void equalsTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(0, 10), v3 = new Vector2d(-1, 10), v4 = new Vector2d(0, -10);
        int a = 0;
        Assertions.assertEquals(v1, v1);
        Assertions.assertNotEquals(a, v1);
        Assertions.assertEquals(v2, v1);
        Assertions.assertNotEquals(v3, v1);
        Assertions.assertNotEquals(v4, v1);
    }

    @Test
    public void toStringTest(){
        Vector2d v1 = new Vector2d(0, 0), v2 = new Vector2d(-90000, 10);
        Assertions.assertEquals("(0,0)", v1.toString());
        Assertions.assertEquals("(-90000,10)", v2.toString());
    }

    @Test
    public void precedesTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(0, 10), v3 = new Vector2d(-1, 11), v4 = new Vector2d(-4, -10);
        Assertions.assertFalse(v1.precedes(v1));
        Assertions.assertFalse(v1.precedes(v2));
        Assertions.assertFalse(v3.precedes(v1));
        Assertions.assertTrue(v4.precedes(v1));
        Assertions.assertFalse(v1.precedes(v4));
    }

    @Test
    public void followsTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(0, 10), v3 = new Vector2d(-1, 11), v4 = new Vector2d(-4, -10);
        Assertions.assertFalse(v1.follows(v1));
        Assertions.assertFalse(v1.follows(v2));
        Assertions.assertFalse(v3.follows(v1));
        Assertions.assertFalse(v4.follows(v1));
        Assertions.assertTrue(v1.follows(v4));
    }

    @Test
    public void upperRightTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(10, 0), v3 = new Vector2d(-1, 11), v4 = new Vector2d(-4, -10);
        Assertions.assertEquals("(0,10)", v1.upperRight(v1).toString());
        Assertions.assertEquals("(10,10)", v1.upperRight(v2).toString());
        Assertions.assertEquals("(0,11)", v1.upperRight(v3).toString());
        Assertions.assertEquals("(0,10)", v1.upperRight(v4).toString());
    }

    @Test
    public void lowerLeftTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(10, 0), v3 = new Vector2d(-1, 11), v4 = new Vector2d(-4, -10);
        Assertions.assertEquals("(0,10)", v1.lowerLeft(v1).toString());
        Assertions.assertEquals("(0,0)", v1.lowerLeft(v2).toString());
        Assertions.assertEquals("(-1,10)", v1.lowerLeft(v3).toString());
        Assertions.assertEquals("(-4,-10)", v1.lowerLeft(v4).toString());
    }


    @Test
    public void addTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(10, 0), v3 = new Vector2d(-1, 11), v4 = new Vector2d(-4, -10);
        Assertions.assertEquals("(0,20)", v1.add(v1).toString());
        Assertions.assertEquals("(10,10)", v1.add(v2).toString());
        Assertions.assertEquals("(-1,21)", v1.add(v3).toString());
        Assertions.assertEquals("(-4,0)", v1.add(v4).toString());
    }

    @Test
    public void subtractTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(10, 0), v3 = new Vector2d(-1, 11), v4 = new Vector2d(-4, -10);
        Assertions.assertEquals("(0,0)", v1.subtract(v1).toString());
        Assertions.assertEquals("(-10,10)", v1.subtract(v2).toString());
        Assertions.assertEquals("(1,-1)", v1.subtract(v3).toString());
        Assertions.assertEquals("(4,20)", v1.subtract(v4).toString());
    }

    @Test
    public void oppositeTest(){
        Vector2d v1 = new Vector2d(0, 10), v2 = new Vector2d(10, 0), v3 = new Vector2d(-1, 11), v4 = new Vector2d(-4, -10);
        Assertions.assertEquals("(0,-10)", v1.opposite().toString());
        Assertions.assertEquals("(-10,0)", v2.opposite().toString());
        Assertions.assertEquals("(1,-11)", v3.opposite().toString());
        Assertions.assertEquals("(4,10)", v4.opposite().toString());
    }
}
