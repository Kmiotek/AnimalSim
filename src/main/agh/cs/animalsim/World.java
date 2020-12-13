package agh.cs.animalsim;

import agh.cs.animalsim.swing.TropicSimulationEngine;

public class World {

    public World(){    }



    public static void main(String[] args) {
        try  {
            TropicMap map = new TropicMap(1600, 900, 800, 450);
            TropicSimulationEngine engine = new TropicSimulationEngine(map, 10, 0, 50);
            engine.start();
            TropicMap map2 = new TropicMap(1600, 900, 800, 450);
            TropicSimulationEngine engine2 = new TropicSimulationEngine(map2, 100, 2, 200);
            //engine2.start();
        } catch(IllegalArgumentException ex) {
            System.out.println("Weź rzesz daj dobre dane następnym razem dobry panie " + ex.getMessage());
        }
        catch(IndexOutOfBoundsException ex) {
            System.out.println("Za dużo trawy mistrzu! " + ex.getMessage());
        }
    }
}
