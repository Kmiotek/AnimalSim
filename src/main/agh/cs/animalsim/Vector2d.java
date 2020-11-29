package agh.cs.animalsim;

import java.util.Objects;

public class Vector2d {
    final private int x;
    final private int y;


    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
        return this.getX() == that.getX() && this.getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public String toString(){
        return "(" + this.getX() + "," + this.getY() + ")";
    }

    public boolean precedes(Vector2d other){
        return other.getX() > this.getX() && other.getY() > this.getY();
    }

    public boolean weakPrecedes(Vector2d other){
        return other.getX() >= this.getX() && other.getY() >= this.getY();
    }

    public boolean follows(Vector2d other){
        return other.getX() < this.getX() && other.getY() < this.getY();
    }

    public boolean weakFollows(Vector2d other){
        return other.getX() <= this.getX() && other.getY() <= this.getY();
    }

    public Vector2d upperRight(Vector2d other){
        int Mx, My;
        Mx = Math.max(other.getX(), this.getX());
        My = Math.max(other.getY(), this.getY());
        return new Vector2d(Mx,My);
    }

    public Vector2d lowerLeft(Vector2d other){
        int Mx, My;
        Mx = Math.min(other.getX(), this.getX());
        My = Math.min(other.getY(), this.getY());
        return new Vector2d(Mx,My);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.getX() + other.getX(), this.getY() + other.getY());
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.getX() - other.getX(), this.getY() - other.getY());
    }

    public Vector2d opposite() {
        return new Vector2d(this.getX() * (-1), this.getY() * (-1));
    }

    public double length(){
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY());
    }

    public double dist(Vector2d other){
        return Math.sqrt(Math.pow(this.x - other.x , 2) + Math.pow(this.y - other.y, 2));
    }

    public Vector2d distVectorWithMod(Vector2d other){                        //TODO trzeba to zrobic
        return null;
    }

    public double distWithDualModulo(Vector2d other, Vector2d mod){
        Vector2d other1 = other.dualMod(mod);
        return Math.min(Math.min(other1.dist(dualMod(mod)), other1.add(new Vector2d(0, mod.y)).dist(dualMod(mod))),
                Math.min(Math.min(other1.add(new Vector2d(0, (-1) * mod.y)).dist(dualMod(mod)),
                        other1.add(new Vector2d(mod.x, 0)).dist(dualMod(mod))),
                other1.add(new Vector2d((-1) * mod.x, 0)).dist(dualMod(mod))));
    }

    public Vector2d mod(int a){
        return new Vector2d(Math.floorMod(x, a), Math.floorMod(y, a));
    }

    public Vector2d dualMod(Vector2d other){
        return new Vector2d(Math.floorMod(x, other.x), Math.floorMod(y, other.y));
    }

}
