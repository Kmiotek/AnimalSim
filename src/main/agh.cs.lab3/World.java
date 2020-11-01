package agh.cs.lab3;

import java.util.Scanner;

public class World {

    private final Animal[][] map;

    public World(){
        map = new Animal[5][5];
    }



    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
        World world = new World();
        world.drawWorld();
        Animal turtle = new Animal();
        world.addAnimal(turtle);
        world.drawWorld();
        world.moveAnimal(new Vector2d(2,2), MoveDirection.FORWARD);
        world.drawWorld();
        System.out.println(turtle.toString());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Witaj, jestem żółwiowym tłumaczem.");
        while(true) {
            System.out.println("Jakie kierunki mam podać zółwiowi?");
            String inputString = scanner.nextLine();
            String[] tab = inputString.split(String.valueOf(' '));
            MoveDirection[] directions = OptionsParser.parse(tab);
            for (MoveDirection dir : directions) {
                world.moveAnimal(turtle.getPosition(), dir);
            }
            System.out.println("Żółw zakończył poruszać się zgodnie z twoimi instrukcjami. Jego stan teraz to " + turtle.toString() + ".");
        }
    }

    public void addAnimal(Animal anime){
        Vector2d pos = anime.getPosition();
        if (map[pos.x][pos.y] == null){
            map[pos.x][pos.y] = anime;
        }
    }

    public void moveAnimal(Vector2d position, MoveDirection direction){
        if (map[position.x][position.y] == null){
            return;
        }
        Animal anime = map[position.x][position.y];
        Vector2d newPosition = anime.move(direction);
        if (newPosition.equals(position)){
            return;
        }
        if (map[newPosition.x][newPosition.y] != null){
            anime.move(direction.opposite());
        } else {
            map[newPosition.x][newPosition.y] = anime;
            map[position.x][position.y] = null;
        }
    }

    private void printHorizontalLine(int length){
        for (int i = 0; i < length; i++){
            System.out.print('-');
        }
        System.out.println();
    }

    public void drawWorld(){
        int horizontalLineLength = map.length*2 + 1;
        StringBuilder[] lines = new StringBuilder[map[0].length];
        for(int i = 0; i < map[0].length; i++){
            lines[i] = new StringBuilder("|");
        }

        for (Animal[] line : map) {
            for (int i = 0;i<map[0].length; i++) {
                if (line[i] != null) {
                    lines[map[0].length-i-1].append("x");    //fajnie byłoby mieć tutaj literki oznaczające rodzaje zwierząt, ale na razie nie ma rodzajów...
                } else {
                    lines[map[0].length-i-1].append(" ");
                }
                lines[map[0].length-i-1].append("|");
            }
        }

        printHorizontalLine(horizontalLineLength);
        for (StringBuilder line : lines) {
            System.out.println(line);
            printHorizontalLine(horizontalLineLength);
        }
    }
}
