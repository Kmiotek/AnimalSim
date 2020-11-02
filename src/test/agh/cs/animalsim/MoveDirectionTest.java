package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveDirectionTest {
    @Test
    public void toStringTest(){
        Assertions.assertEquals("do przodu", MoveDirection.FORWARD.toString());
    }
}
