package agh.cs.animalsim;

import java.awt.*;

public class Drawable {
    public Color color;
    public Vector2d position;
    public int size;

    public Drawable(Vector2d el, Color red, int drawingSize) {
        position = el;
        color = red;
        size = drawingSize;
    }
}
