package agh.cs.lab3;

public class Vector2d {
    final public int x;
    final public int y;


    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        if(this.x == that.x && this.y == that.y){
            return true;
        }
        return false;
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other){
        if(other.x > this.x && other.y > this.y){
            return true;
        }
        return false;
    }

    public boolean follows(Vector2d other){
        if(other.x < this.x && other.y < this.y){
            return true;
        }
        return false;
    }

    public Vector2d upperRight(Vector2d other){
        int Mx, My;
        if(other.x > this.x){
            Mx = other.x;
        } else {
            Mx = this.x;
        }
        if(other.y > this.y){
            My = other.y;
        } else {
            My = this.y;
        }
        return new Vector2d(Mx,My);
    }

    public Vector2d lowerLeft(Vector2d other){
        int Mx, My;
        if(other.x < this.x){
            Mx = other.x;
        } else {
            Mx = this.x;
        }
        if(other.y < this.y){
            My = other.y;
        } else {
            My = this.y;
        }
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



}
