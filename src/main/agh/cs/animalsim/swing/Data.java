package agh.cs.animalsim.swing;

import java.util.ArrayList;

public class Data {

    public ArrayList<Double> dataX;
    public ArrayList<Double> dataY;
    String updateCommand;

    public Data(){
        dataX = new ArrayList<>();
        dataY = new ArrayList<>();
    }

    public Data(ArrayList<Double> dataX, ArrayList<Double> dataY){
        this.dataX = dataX;
        this.dataY = dataY;
    }
}
