package agh.cs.animalsim;

import agh.cs.animalsim.swing.TropicSimulationEngine;

public class World {

    public World(){    }



    public static void main(String[] args) {
        try  {
            TropicMap map = new TropicMap(1600, 900, 800, 450);
            TropicSimulationEngine engine = new TropicSimulationEngine(map, 10, 2, 20);
            engine.start();
        } catch(IllegalArgumentException ex) {
            System.out.println("Weź rzesz daj dobre dane następnym razem dobry panie " + ex.getMessage());
        }
        catch(IndexOutOfBoundsException ex) {
            System.out.println("Za dużo trawy mistrzu! " + ex.getMessage());
        }
    }
}
