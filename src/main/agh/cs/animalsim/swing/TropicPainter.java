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
        for(Drawable object : map.getDrawableObjects()){
            g.setColor(object.color);
            int size = (int)(Math.sqrt(object.size * 10));
            Vector2d newPos = object.position.modulo(map.upperRightCorner());
            g.fillOval(newPos.x - size/2 - lowerLeft.x + 50,
                    newPos.y - size/2 - lowerLeft.y + 50, size, size);
        }
    }
}
