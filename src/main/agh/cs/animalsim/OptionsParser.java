package agh.cs.animalsim;

import java.util.ArrayList;

public class OptionsParser {
    public static MoveDirection[] parse(String[] strings){
        ArrayList<MoveDirection> result = new ArrayList<>();
        for (String str : strings){
            MoveDirection parsed = parseSingle(str);
            if (parsed != null){
                result.add(parsed);
            }
        }
        return result.toArray(new MoveDirection[0]);
    }

    public static MoveDirection parseSingle(String single){
        switch (single){
            case "f":
            case "forward":
                return MoveDirection.FORWARD;
            case "b":
            case "backward":
                return (MoveDirection.BACKWARD);
            case "r":
            case "right":
                return (MoveDirection.RIGHT);
            case "l":
            case "left":
                return (MoveDirection.LEFT);
            default:
                // nothing
                return null;
        }
    }
}
