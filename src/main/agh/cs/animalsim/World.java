package agh.cs.animalsim;

import agh.cs.animalsim.swing.TropicSimulationEngine;

public class World {

    public World(){    }



    public static void main(String[] args) {
        try  {
            MoveDirection[] directions = OptionsParser.parse(args);
            TropicMap map = new TropicMap(1600, 900, 800, 450);
            TropicSimulationEngine engine = new TropicSimulationEngine(map, 10, 2, 20);
            engine.start();
            //Animal fox = new Animal(map, new Vector2d(0,0), true, 9 );
            //map.place(fox);
        } catch(IllegalArgumentException ex) {
            System.out.println("Weź rzesz daj dobre dane następnym razem dobry panie " + ex.getMessage());
        }
        catch(IndexOutOfBoundsException ex) {
            System.out.println("Za dużo trawy mistrzu! " + ex.getMessage());
        }
    }
}
