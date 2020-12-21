package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void simpleMoveTest(){
        IWorldMap map = new TropicMap(5, 5, 0,0);;
        Animal hedgehog = new Animal(map);
        map.place(hedgehog);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.moveInDirection(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Północ", hedgehog.getStatus());
        hedgehog.moveInDirection(MoveDirection.BACKWARD);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.moveInDirection(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.moveInDirection(MoveDirection.LEFT);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
    }



    @Test
    public void collisionMoveTest(){
        TropicMap map = new TropicMap(5, 5, 0,0);
        map.place(new Animal(map, new Vector2d(2,3)));
        Animal hedgehog = new Animal(map);
        map.place(hedgehog);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.moveInDirection(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.moveInDirection(MoveDirection.RIGHT);
        hedgehog.moveInDirection(MoveDirection.FORWARD);
        hedgehog.moveInDirection(MoveDirection.LEFT);
        hedgehog.moveInDirection(MoveDirection.FORWARD);
        hedgehog.moveInDirection(MoveDirection.LEFT);
        Assertions.assertEquals("Pozycja: (3,3), kierunek: Zachód", hedgehog.getStatus());
        hedgehog.moveInDirection(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (3,3), kierunek: Zachód", hedgehog.getStatus());
    }
}
