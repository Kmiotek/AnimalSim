package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void moveTest(){
        Animal hedgehog = new Animal();
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,4), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,5), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.BACKWARD);
        Assertions.assertEquals("Pozycja: (2,4), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (2,4), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (3,4), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (4,4), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (5,4), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (5,4), kierunek: Południe", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (5,5), kierunek: Zachód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (5,4), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.LEFT);
        Assertions.assertEquals("Pozycja: (5,5), kierunek: Zachód", hedgehog.getStatus());
    }
}
