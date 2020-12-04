package agh.cs.animalsim;

import java.util.Objects;

public class Vector2f {

    final public double x;
    final public double y;


    public Vector2f(double x, double y){
        this.x = x;
        this.y = y;
    }


    public Vector2f(Vector2f other){
        this(other.x, other.y);
    }

    public Vector2f(Vector2d other){
        this(other.x, other.y);
    }

    public Vector2d approx(){
        return new Vector2d((int) (this.x + 0.5), (int) (this.y + 0.5));
    }

    public Vector2f left90deg(){
        return new Vector2f(this.y * (-1), this.x);
    }

    public Vector2f right90deg(){
        return new Vector2f(this.y, this.x * (-1));
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2f))
            return false;
        Vector2f that = (Vector2f) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2f other){
        return other.x > this.x && other.y > this.y;
    }

    public boolean weakPrecedes(Vector2f other){
        return other.x >= this.x && other.y >= this.y;
    }

    public boolean follows(Vector2f other){
        return other.x < this.x && other.y < this.y;
    }

    public boolean weakFollows(Vector2f other){
        return other.x <= this.x && other.y <= this.y;
    }

    public Vector2f upperRight(Vector2f other){
        double Mx, My;
        Mx = Math.max(other.x, this.x);
        My = Math.max(other.y, this.y);
        return new Vector2f(Mx,My);
    }

    public Vector2f lowerLeft(Vector2f other){
        double Mx, My;
        Mx = Math.min(other.x, this.x);
        My = Math.min(other.y, this.y);
        return new Vector2f(Mx,My);
    }

    public Vector2f add(Vector2f other){
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    public Vector2f multiply(double a){
        return new Vector2f(this.x * a, this.y * a);
    }

    public Vector2f divide(double a){
        return new Vector2f(this.x / a, this.y / a);
    }

    public Vector2f subtract(Vector2f other){
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    public Vector2f opposite() {
        return new Vector2f(this.x * (-1), this.y * (-1));
    }

    public double length(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2f normalize(){
        return this.divide(this.length());
    }

    public Vector2f rotate(double angle){
        return new Vector2f(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
    }

}

