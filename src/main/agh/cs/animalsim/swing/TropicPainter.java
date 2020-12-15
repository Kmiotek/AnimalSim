package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import javax.swing.*;
import java.awt.*;


public class TropicPainter extends JPanel {
    protected IWorldMap map;

    protected final Color savannaColor = new Color(128, 123, 5);
    protected final Color jungleColor  = new Color(60, 100, 5);

    private Vector2d windowSize;

    public TropicPainter(IWorldMap map){
        this.map = map;
        windowSize = new Vector2d(1600, 900);
    }

    public void setResolution(Vector2d res){
        this.windowSize = res;
    }

    @Override
    public void paintComponent(Graphics g){
        TropicMap map1 = (TropicMap) map;
        setBackground(Color.WHITE);
        Vector2d upperRight = new Vector2d(windowSize.x-28, windowSize.y-82);
        Vector2d lowerLeft = new Vector2d(windowSize.x/2, 10);
        Vector2f scale = new Vector2f((upperRight.x-lowerLeft.x)/(double)(map.upperRightCorner().x - map.lowerLeftCorner().x),
                (upperRight.y- lowerLeft.y)/(double)(map.upperRightCorner().y - map.lowerLeftCorner().y));

        g.setColor(Color.RED);
        g.fillRect(0, 0, windowSize.x, windowSize.y);
        g.setColor(savannaColor);
        g.fillRect(lowerLeft.x, lowerLeft.y, upperRight.x-lowerLeft.x, upperRight.y-lowerLeft.y);
        g.setColor(jungleColor);
        g.fillRect((int) (map1.junglePos().x * scale.x + lowerLeft.x), (int)(map1.junglePos().y * scale.y + lowerLeft.y),
                (int)(map1.jungleSize.x * scale.x), (int) (map1.jungleSize.y * scale.y));
        for(Drawable object : map.getDrawableObjects()){
            g.setColor(object.color);
            int size = (int)(Math.sqrt(object.size *20));
            Vector2d newPos = object.position.modulo(map.upperRightCorner()).scale(scale);
            g.fillOval(newPos.x - size/2 + lowerLeft.x,
                    newPos.y - size/2 + lowerLeft.y, size, size);
        }
    }






}
