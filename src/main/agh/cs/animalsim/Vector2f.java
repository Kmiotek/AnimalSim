package agh.cs.animalsim;

import java.util.Objects;

public class Vector2f {

    final private double x;
    final private double y;


    public Vector2f(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2f(Vector2f other){
        this(other.x, other.y);
    }

    public Vector2f(Vector2d other){
        this(other.getX(), other.getY());
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
        return this.getX() == that.getX() && this.getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public String toString(){
        return "(" + this.getX() + "," + this.getY() + ")";
    }

    public boolean precedes(Vector2f other){
        return other.getX() > this.getX() && other.getY() > this.getY();
    }

    public boolean weakPrecedes(Vector2f other){
        return other.getX() >= this.getX() && other.getY() >= this.getY();
    }

    public boolean follows(Vector2f other){
        return other.getX() < this.getX() && other.getY() < this.getY();
    }

    public boolean weakFollows(Vector2f other){
        return other.getX() <= this.getX() && other.getY() <= this.getY();
    }

    public Vector2f upperRight(Vector2f other){
        double Mx, My;
        Mx = Math.max(other.getX(), this.getX());
        My = Math.max(other.getY(), this.getY());
        return new Vector2f(Mx,My);
    }

    public Vector2f lowerLeft(Vector2f other){
        double Mx, My;
        Mx = Math.min(other.getX(), this.getX());
        My = Math.min(other.getY(), this.getY());
        return new Vector2f(Mx,My);
    }

    public Vector2f add(Vector2f other){
        return new Vector2f(this.getX() + other.getX(), this.getY() + other.getY());
    }

    public Vector2f multiply(double a){
        return new Vector2f(this.getX() * a, this.getY() * a);
    }

    public Vector2f divide(double a){
        return new Vector2f(this.getX() / a, this.getY() / a);
    }

    public Vector2f subtract(Vector2f other){
        return new Vector2f(this.getX() - other.getX(), this.getY() - other.getY());
    }

    public Vector2f opposite() {
        return new Vector2f(this.getX() * (-1), this.getY() * (-1));
    }

    public double length(){
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
    }

    public Vector2f normalize(){
        return this.divide(this.length());
    }

}

