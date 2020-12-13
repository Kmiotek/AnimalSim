package agh.cs.animalsim;

import java.util.Objects;

public class Vector2d {
    final public int x;
    final public int y;


    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d other){
        this(other.x, other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other){
        return other.x > this.x && other.y > this.y;
    }

    public boolean weakPrecedes(Vector2d other){
        return other.x >= this.x && other.y >= this.y;
    }

    public boolean follows(Vector2d other){
        return other.x < this.x && other.y < this.y;
    }

    public boolean weakFollows(Vector2d other){
        return other.x <= this.x && other.y <= this.y;
    }

    public Vector2d upperRight(Vector2d other){
        int Mx, My;
        Mx = Math.max(other.x, this.x);
        My = Math.max(other.y, this.y);
        return new Vector2d(Mx,My);
    }

    public Vector2d lowerLeft(Vector2d other){
        int Mx, My;
        Mx = Math.min(other.x, this.x);
        My = Math.min(other.y, this.y);
        return new Vector2d(Mx,My);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(this.x * (-1), this.y * (-1));
    }

    public double length(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double dist(Vector2d other){
        return Math.sqrt(Math.pow(this.x - other.x , 2) + Math.pow(this.y - other.y, 2));
    }

    public Vector2d scale(double a){
        return new Vector2d((int)(x*a + 0.5), (int)(y*a +0.5));
    }

    public Vector2d distVectorWithMod(Vector2d other, Vector2d mod){
        Vector2d v1 = other.modulo(mod);
        Vector2d v2 = this.modulo(mod);
        int minX = v1.x;
        int minY = v1.y;
        if (Math.abs(v1.x - mod.x - v2.x) < Math.abs(v1.x - v2.x)){
            minX = v1.x - mod.x;
        }
        if (Math.abs(v1.x + mod.x - v2.x) < Math.abs(minX - v2.x)){
            minX = v1.x + mod.x;
        }
        if (Math.abs(v1.y - mod.y - v2.y) < Math.abs(v1.y - v2.y)){
            minY = v1.y - mod.y;
        }
        if (Math.abs(v1.y + mod.y - v2.y) < Math.abs(minY - v2.y)){
            minY = v1.y + mod.y;
        }
        return new Vector2d(minX - v2.x, minY - v2.y);
    }

    public double distWithMod(Vector2d other, Vector2d mod){
        return distVectorWithMod(other, mod).length();
    }

    public Vector2d modulo(Vector2d other){
        return new Vector2d(Math.floorMod(x, other.x), Math.floorMod(y, other.y));
    }

}
