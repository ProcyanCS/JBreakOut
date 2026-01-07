package main;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		// Create the window
        JFrame window = new JFrame("JBreakOut!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Create game panel (left)
        GamePanel gamePanel = new GamePanel();
        gamePanel.requestFocusInWindow();


        // Create UI panel (right)
        UIPanel uiPanel = new UIPanel(gamePanel);

        // Let GamePanel talk to UIPanel for score updates
        gamePanel.setUIPanel(uiPanel);

        // Create a container that holds both panels side-by-side
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        container.add(gamePanel);  // left
        container.add(uiPanel);    // right

        // Add container to window
        window.add(container, BorderLayout.CENTER);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.launchGame();
    }


}
