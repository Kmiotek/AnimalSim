package agh.cs.animalsim.swing;

import javax.swing.*;

public class Client {

    public Client() {

    }

    public void start(){
        javax.swing.SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI(){
        JFrame f=new JFrame("Evolution Client");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ClientPainter painter = new ClientPainter();
        f.setContentPane(painter);
        painter.setOpaque(true);

        f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
    }

}
