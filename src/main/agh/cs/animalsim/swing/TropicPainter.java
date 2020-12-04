package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import java.awt.*;

public class TropicPainter extends Painter{
    public TropicPainter(TropicMap map, int numberOfHerbivores, int numberOfCarnivores, int amountOfGrass) {
        super(map, numberOfHerbivores, numberOfCarnivores, amountOfGrass);
    }

    @Override
    public void paint(Graphics g){
        TropicMap map1 = (TropicMap) map;
        setBackground(Color.WHITE);
        g.setColor(savannaColor);
        upperRight = map.upperRightCorner();
        lowerLeft = map.lowerLeftCorner();
        g.fillRect(50, 50, upperRight.x - lowerLeft.x, upperRight.y - lowerLeft.y);
        g.setColor(jungleColor);
        g.fillRect(map1.junglePos().x + 50, map1.junglePos().y + 50, map1.jungleSize.x, map1.jungleSize.y);
        for(Vector2d position : map.getObjectsPositions()){
            IMapElement object = map.objectAt(position);
            if (object == null){
                System.out.println(map.getObjectsPositions().contains(position));
                System.out.println(map.objectsAt(position));
                System.out.println("break point");
            }
            //System.out.println("1 " + object.toString());
            if (object instanceof Animal){
                if(object.isCarnivore()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(brown);
                }
            } else if (object instanceof Grass){
                g.setColor(Color.GREEN);
            }
            int size = object.getDrawingSize();
            Vector2d newPos = object.getPosition().modulo(map.upperRightCorner());
            g.fillOval(newPos.x - size/2 - lowerLeft.x + 50,
                    newPos.y - size/2 - lowerLeft.y + 50, size, size);
        }
    }
}
