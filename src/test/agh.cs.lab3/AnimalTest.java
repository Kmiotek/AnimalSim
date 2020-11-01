package agh.cs.lab3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void moveTest(){
        Animal hedgehog = new Animal();
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.toString());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Północ", hedgehog.toString());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,4), kierunek: Północ", hedgehog.toString());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,4), kierunek: Północ", hedgehog.toString());
        hedgehog.move(MoveDirection.BACKWARD);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Północ", hedgehog.toString());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Wschód", hedgehog.toString());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (3,3), kierunek: Wschód", hedgehog.toString());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Wschód", hedgehog.toString());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Wschód", hedgehog.toString());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Południe", hedgehog.toString());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Zachód", hedgehog.toString());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Północ", hedgehog.toString());
        hedgehog.move(MoveDirection.LEFT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Zachód", hedgehog.toString());
    }
}
