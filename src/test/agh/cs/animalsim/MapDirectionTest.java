package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MapDirectionTest {
    @Test
    public void toStringTest(){
        Assertions.assertEquals("Północ", MapDirection.NORTH.toString());
        Assertions.assertEquals("Południe", MapDirection.SOUTH.toString());
        Assertions.assertEquals("Wschód", MapDirection.EAST.toString());
        Assertions.assertEquals("Zachód", MapDirection.WEST.toString());
    }

    @Test
    public void nextTest(){
        Assertions.assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        Assertions.assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        Assertions.assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
        Assertions.assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
    }

    @Test
    public void previousTest(){
        Assertions.assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
        Assertions.assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        Assertions.assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
        Assertions.assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
    }

    @Test
    public void toUnitVectorTest(){
        Assertions.assertEquals("(0,1)", MapDirection.NORTH.toUnitVector().toString());
        Assertions.assertEquals("(0,-1)", MapDirection.SOUTH.toUnitVector().toString());
        Assertions.assertEquals("(1,0)", MapDirection.EAST.toUnitVector().toString());
        Assertions.assertEquals("(-1,0)", MapDirection.WEST.toUnitVector().toString());
    }


}
