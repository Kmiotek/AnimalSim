package agh.cs.animalsim;

import agh.cs.animalsim.swing.Client;
import agh.cs.animalsim.swing.TropicSimulationEngine;

public class World {

    public World(){    }



    public static void main(String[] args) {
        try  {
            Client client = new Client();
            client.start();
        } catch(IllegalArgumentException ex) {
            System.out.println("Weź rzesz daj dobre dane następnym razem dobry panie " + ex.getMessage());
        }
        catch(IndexOutOfBoundsException ex) {
            System.out.println("Za dużo trawy mistrzu! " + ex.getMessage());
        }
    }
}
