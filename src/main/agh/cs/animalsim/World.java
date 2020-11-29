package agh.cs.animalsim;

import javax.swing.*;
import java.util.Scanner;

public class World {

    public World(){    }



    public static void main(String[] args) {
        try  {
            MoveDirection[] directions = OptionsParser.parse(args);
            IWorldMap map = new TropicMap(500, 500, 5);
            Vector2d[] positions = { new Vector2d(200,133), new Vector2d(30,4) };
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            Animal fox = new Animal(map, new Vector2d(0,0), true, 7 );
            map.place(fox);
            map.display();
        } catch(IllegalArgumentException ex) {
            System.out.println("Weź rzesz daj dobre dane następnym razem dobry panie " + ex.getMessage());
        }
        catch(IndexOutOfBoundsException ex) {
            System.out.println("Za dużo trawy mistrzu! " + ex.getMessage());
        }
    }
}
