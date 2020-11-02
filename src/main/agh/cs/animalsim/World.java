package agh.cs.animalsim;

import java.util.Scanner;

public class World {

    public World(){    }



    public static void main(String[] args) {
        Animal turtle = new Animal();
        System.out.println(turtle.toString());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Witaj, jestem żółwiowym tłumaczem.");
        while(true) {
            System.out.println("Jakie kierunki mam podać zółwiowi?");
            String inputString = scanner.nextLine();
            String[] tab = inputString.split(String.valueOf(' '));
            MoveDirection[] directions = OptionsParser.parse(tab);

            System.out.println("Żółw zakończył poruszać się zgodnie z twoimi instrukcjami. Jego stan teraz to " + turtle.toString() + ".");
        }
    }
}
