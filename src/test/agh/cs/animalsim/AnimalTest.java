package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test
    public void simpleMoveTest(){
        Animal hedgehog = new Animal(new RectangularWorldMap());
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.BACKWARD);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.LEFT);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
    }

    @Test
    public void complexMoveTest(){
        Animal hedgehog = new Animal(new RectangularWorldMap());
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,4), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,4), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.BACKWARD);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (2,3), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (3,3), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Wschód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Południe", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Zachód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.LEFT);
        Assertions.assertEquals("Pozycja: (4,3), kierunek: Zachód", hedgehog.getStatus());
        for (int i =0;i<10;i++){
            hedgehog.move(MoveDirection.FORWARD);
        }
        Assertions.assertEquals("Pozycja: (0,3), kierunek: Zachód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.LEFT);
        for (int i =0;i<10;i++){
            hedgehog.move(MoveDirection.FORWARD);
        }
        Assertions.assertEquals("Pozycja: (0,0), kierunek: Południe", hedgehog.getStatus());
    }

    @Test
    public void boundariesMoveTest(){
        Animal hedgehog = new Animal(new RectangularWorldMap(7,7));
        for (int i =0;i<10;i++){
            hedgehog.move(MoveDirection.FORWARD);
        }
        Assertions.assertEquals("Pozycja: (2,6), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.LEFT);
        for (int i =0;i<10;i++){
            hedgehog.move(MoveDirection.FORWARD);
        }
        Assertions.assertEquals("Pozycja: (0,6), kierunek: Zachód", hedgehog.getStatus());
        for (int i =0;i<10;i++){
            hedgehog.move(MoveDirection.BACKWARD);
        }
        Assertions.assertEquals("Pozycja: (6,6), kierunek: Zachód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.LEFT);
        for (int i =0;i<10;i++){
            hedgehog.move(MoveDirection.FORWARD);
        }
        Assertions.assertEquals("Pozycja: (6,0), kierunek: Południe", hedgehog.getStatus());
    }

    @Test
    public void collisionMoveTest(){
        RectangularWorldMap map = new RectangularWorldMap();
        map.place(new Animal(map, new Vector2d(2,3)));
        Animal hedgehog = new Animal(map);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (2,2), kierunek: Północ", hedgehog.getStatus());
        hedgehog.move(MoveDirection.RIGHT);
        hedgehog.move(MoveDirection.FORWARD);
        hedgehog.move(MoveDirection.LEFT);
        hedgehog.move(MoveDirection.FORWARD);
        hedgehog.move(MoveDirection.LEFT);
        Assertions.assertEquals("Pozycja: (3,3), kierunek: Zachód", hedgehog.getStatus());
        hedgehog.move(MoveDirection.FORWARD);
        Assertions.assertEquals("Pozycja: (3,3), kierunek: Zachód", hedgehog.getStatus());
    }
}
