package agh.cs.animalsim;

import org.junit.jupiter.api.Test;

public class WorldTest {

    @Test
    public void mainTest(){
        World.main(new String[]{"f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"});
    }

    @Test
    public void mainTest2(){
        World.main(new String[]{"f", "f", "f", "f", "f", "f", "f", "foo", "f", "f", "f", "f", "f", "f", "f"});
    }
}
