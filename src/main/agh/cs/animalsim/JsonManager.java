package agh.cs.animalsim;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonManager {
    private JSONObject jsonObject;

    public JsonManager(String filePath) {
        JSONParser parser = new JSONParser();

        String directoryPath = new File("").getAbsolutePath().concat("\\data\\");

        try (Reader reader = new FileReader(directoryPath.concat(filePath))) {
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public int getInt(String what){
        return ((Long) jsonObject.get(what)).intValue();
    }

    public double getDouble(String what){
        return (double) jsonObject.get(what);
    }
}
