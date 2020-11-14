package agh.cs.animalsim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimulationEngineTest {


    @Test
    public void run_SimpleTest_RectangularMap(){
        Vector2d[] positions = { new Vector2d(2,2)};
        RectangularWorldMap map =  new RectangularWorldMap(10,5);
        SimulationEngine simulation = new SimulationEngine(null, map, positions);
        simulation.run();
        String state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | | | | | | | | | |" + System.lineSeparator() +
                "  2: | | |^| | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
    }

    @Test
    public void run_MoveDistributionTest_RectangularMap(){
        MoveDirection[] moves = OptionsParser.parse(new String[]{"f", "b", "r", "f"});
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(3,2)};
        RectangularWorldMap map =  new RectangularWorldMap(10,5);
        SimulationEngine simulation = new SimulationEngine(moves, map, positions);
        simulation.run();
        String state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | |^| | | | | | | |" + System.lineSeparator() +
                "  3: | | | |^| | | | | | |" + System.lineSeparator() +
                "  2: | | | |>| | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
    }

    @Test
    public void run_ComplexTest_RectangularMap(){
        MoveDirection[] moves = OptionsParser.parse(new String[]{"f", "b", "r", "f", "b", "f", "f"});
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(3,2)};
        RectangularWorldMap map =  new RectangularWorldMap(10,5);
        SimulationEngine simulation = new SimulationEngine(moves, map, positions);
        simulation.run();
        String state = " y\\x  0 1 2 3 4 5 6 7 8 9" + System.lineSeparator() +
                "  5: ---------------------" + System.lineSeparator() +
                "  4: | | |^| | | | | | | |" + System.lineSeparator() +
                "  3: | | | |^| | | | | | |" + System.lineSeparator() +
                "  2: | | | | |>| | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------------" + System.lineSeparator();
        Assertions.assertEquals(map.toString(), state);
    }
}
