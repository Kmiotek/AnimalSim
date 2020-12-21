package agh.cs.animalsim.swing;

import javax.swing.*;

public class Client {

    public Client() {

    }

    public void start(){
        javax.swing.SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI(){
        JFrame frame=new JFrame("Evolution Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ClientPainter painter = new ClientPainter();
        frame.setContentPane(painter);
        painter.setOpaque(true);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
