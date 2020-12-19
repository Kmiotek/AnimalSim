package agh.cs.animalsim.swing;

import agh.cs.animalsim.*;

import javax.swing.*;
import java.awt.*;


public class TropicPainter extends JPanel {
    protected IWorldMap map;

    protected final Color savannaColor = new Color(128, 123, 5);
    protected final Color jungleColor  = new Color(60, 100, 5);

    public TropicPainter(IWorldMap map){
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics g){
        Vector2d panelSize = new Vector2d(getSize().width, getSize().height);
        TropicMap map1 = (TropicMap) map;
        setBackground(Color.WHITE);
        Vector2d upperRight = new Vector2d(panelSize.x-10, panelSize.y-10);
        Vector2d lowerLeft = new Vector2d(10, 10);
        Vector2f scale = new Vector2f((upperRight.x-lowerLeft.x)/(double)(map.upperRightCorner().x - map.lowerLeftCorner().x),
                (upperRight.y- lowerLeft.y)/(double)(map.upperRightCorner().y - map.lowerLeftCorner().y));

        g.setColor(Color.RED);
        g.fillRect(0, 0, panelSize.x, panelSize.y);
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

    public Vector2d getMapPosition(Vector2d panelPosition){
        Vector2d panelSize = new Vector2d(getSize().width, getSize().height);
        Vector2d upperRight = new Vector2d(panelSize.x-10, panelSize.y-10);
        Vector2d lowerLeft = new Vector2d(10, 10);
        Vector2f scale = new Vector2f((double)(map.upperRightCorner().x - map.lowerLeftCorner().x)/(upperRight.x-lowerLeft.x),
                (double)(map.upperRightCorner().y - map.lowerLeftCorner().y)/(upperRight.y- lowerLeft.y));
        Vector2d relativePos = panelPosition.subtract(lowerLeft);
        return relativePos.scale(scale);
    }




}
