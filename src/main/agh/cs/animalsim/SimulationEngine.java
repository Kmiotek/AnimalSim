package agh.cs.animalsim;

import java.util.ArrayList;

public class SimulationEngine implements IEngine{

    private MoveDirection[] moves;
    private ArrayList<Animal> animals;
    private int numberOfAnimals;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] StartingPositions){
        this.moves = moves;
        animals = new ArrayList<>();
        for (Vector2d pos : StartingPositions) {
            if(map.canMoveTo(pos)){
                Animal tmp = new Animal(map, pos);
                animals.add(tmp);
                map.place(tmp);
            } else {
                System.out.println("Nie udało się stworzyć zwierzęcia o koordynatach: " + pos.toString());
            }
        }
        numberOfAnimals = animals.size();
    }

    @Override
    public void run() {
        int currentAnimal = 0;
        if(moves == null){
            return;
        }
        for (MoveDirection move : moves) {
            animals.get(currentAnimal).move(move);
            currentAnimal = (currentAnimal + 1) % numberOfAnimals;  //this makes sure that every animal gets the correct moves
        }
    }


}
