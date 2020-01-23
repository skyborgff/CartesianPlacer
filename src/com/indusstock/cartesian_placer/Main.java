package com.indusstock.cartesian_placer;

import javax.swing.*;

public class Main {
        public static void main(String[] args) {
            JFrame main_gui = new JFrame("Cartesian Placer");
            main_gui.setContentPane(new MainGUI().panelMain);
            main_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                main_gui.pack();
            main_gui.setSize(1200,700);
            main_gui.setVisible(true);
        }
    }