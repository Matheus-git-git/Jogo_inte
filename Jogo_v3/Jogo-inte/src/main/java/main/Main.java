package main;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import userInterface.*;

public class Main {

    public static void main(String[] args) {
        boolean iniciarJogo2 = false;
        int i = 0;
        boolean condicao1 = true;
        JFrame window = new JFrame();
        JFrame UIScreen = new JFrame();

        Login_01 login = new Login_01();
        Register register = new Register();
        // MainUI mainUI = new MainUI();

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Loading loading = new Loading();

        loading.setVisible(true);

        try {
            for (i = 0; i <= 100; i += 3) {
                // loading.remove(loading.BARRA1);
                Thread.sleep(70);
                loading.BARRA1.setValue(i);
                // barra.LABEL.setText(Integer.toString(i) + "%");
                // barra2.repaint();
                // loading.add(loading.BARRA1);
                loading.BARRA1.repaint();
                loading.BARRA1.revalidate();

            }
            loading.dispose();

            // new MainUI().setVisible(true);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "Erro!!!!!!");

        }
        i = 0;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new Loading().setVisible(true);
            }
        });

        UIScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIScreen.setResizable(false);
        UIScreen.setPreferredSize(new Dimension(768, 576));
        UIScreen.setTitle("2D Adventure");
        UIScreen.setLocationRelativeTo(null);
        UIScreen.setVisible(true);

        while (true) {

            if ((login.registerScreen == false && register.registerScreen2 == true && condicao1 == true) || i == 0) {
                System.out.println("\n\n\n\nAQUI");
                // UIScreen.removeAll();
                register.setVisible(false);
                login.setVisible(true);
                UIScreen.add(login);
                UIScreen.pack();
                UIScreen.setLocationRelativeTo(null);
                register.registerScreen2 = true;
                condicao1 = false;
            }

            if (login.registerScreen == true) {
                // UIScreen.removeAll();
                // UIScreen.remove(login);
                login.setVisible(false);
                register.setVisible(true);
                UIScreen.add(register);
                UIScreen.pack();
                System.out.println("aquiiii");
                // UIScreen.repaint();
                // UIScreen.revalidate();
                login.registerScreen = false;
                register.registerScreen2 = false;
                condicao1 = true;
            }

            if (login.iniciarJogo == true) {
                System.out.println("entreiiiiii");
                login.setVisible(false);
                register.setVisible(false);
                UIScreen.removeAll();
                UIScreen.pack();
                UIScreen.setVisible(false);
                UIScreen.dispose();
                iniciarJogo2 = true;
                System.out.println("VAI");
                break;
            }

            i++;
            if (i > 10) {
                i = 1;
            }
            // System.out.print("");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
        }

        if (iniciarJogo2) {
            System.out.println("ENTREIIIIIIII");

            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("2D Adventure");

            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);

            window.pack(); // apply the GamePanel size to the window;

            window.setLocationRelativeTo(null); // puts screen in center
            window.setVisible(true); // shows the screen

            gamePanel.setupGame();
            gamePanel.startGameThread();
        }

    }

}
