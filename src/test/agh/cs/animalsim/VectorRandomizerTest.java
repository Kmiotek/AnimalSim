package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorRandomizerTest {


    @Test
    public void randomVectorInRangeSmartTest(){
        GrassField map = new GrassField(0);
        VectorRandomizer rand = new VectorRandomizer(map);
        Grass g_2_2 = new Grass(map, new Vector2d(2,2));
        Grass g_3_2 = new Grass(map, new Vector2d(3,2));
        Grass g_4_2 = new Grass(map, new Vector2d(4,2));
        Grass g_2_4 = new Grass(map, new Vector2d(2,4));
        Grass g_3_4 = new Grass(map, new Vector2d(3,4));
        Grass g_4_4 = new Grass(map, new Vector2d(4,4));
        Grass g_4_3 = new Grass(map, new Vector2d(4,3));
        Grass g_2_3 = new Grass(map, new Vector2d(2,3));
        map.placeAnyObject(g_2_2);
        map.placeAnyObject(g_3_2);
        map.placeAnyObject(g_4_2);
        map.placeAnyObject(g_2_4);
        map.placeAnyObject(g_3_4);
        map.placeAnyObject(g_4_4);
        map.placeAnyObject(g_2_3);
        map.placeAnyObject(g_4_3);
        for(int i =0;i<100;i++) {
            Assertions.assertEquals(new Vector2d(3, 3), rand.randomVectorInRangeSmart(new Vector2d(2, 2), new Vector2d(4, 4)));
        }
    }
}
