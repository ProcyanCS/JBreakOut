package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class UIPanel extends JPanel {

    JButton btnNewGame;

    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private JLabel directionLabel;

    public UIPanel(GamePanel gamePanel) {
        setPreferredSize(new Dimension(500, GamePanel.SCREEN_HEIGHT));
        setBackground(Color.black);
        setLayout(null);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        scoreLabel.setBounds(25, 120, 400, 40);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(scoreLabel);

        highScoreLabel = new JLabel("High Score: 0");
        highScoreLabel.setForeground(Color.white);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        highScoreLabel.setBounds(25, 170, 400, 40);
        highScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(highScoreLabel);

        JButton btnNewGame = new JButton("New Game");
        btnNewGame.setBounds(25, 25, 400, 60);
        add(btnNewGame);
        
     // Difficulty label
        JLabel difficultyLabel = new JLabel("Difficulty");
        difficultyLabel.setForeground(Color.white);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 22));
        difficultyLabel.setBounds(25, 250, 200, 40);
        add(difficultyLabel);

        // Spinner model: min=3, max=8, step=1
        SpinnerNumberModel difficultyModel = new SpinnerNumberModel(3, 3, 8, 1);

        JSpinner difficultySpinner = new JSpinner(difficultyModel);
        difficultySpinner.setBounds(25, 300, 80, 40);

        // Optional: make the text bigger
        JComponent editor = difficultySpinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setFont(new Font("Arial", Font.BOLD, 22));
        tf.setHorizontalAlignment(JTextField.CENTER);

        add(difficultySpinner);
        
        directionLabel = new JLabel("↓");   // default direction
        directionLabel.setForeground(Color.white);
        directionLabel.setFont(new Font("Arial", Font.BOLD, 48));
        directionLabel.setBounds(25, 380, 100, 60);
        directionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(directionLabel);
        
        btnNewGame.addActionListener(e -> {
            int difficulty = (int) difficultySpinner.getValue();
            gamePanel.resetGame(difficulty);
            gamePanel.requestFocusInWindow();
        });


    }
    
    public void updateDirection(String dir) {
        switch (dir) {
            case "up":    directionLabel.setText("↑"); break;
            case "down":  directionLabel.setText("↓"); break;
            case "left":  directionLabel.setText("←"); break;
            case "right": directionLabel.setText("→"); break;
        }
    }


    public void updateScore(long score, long highScore) {
        scoreLabel.setText("Score: " + score);
        highScoreLabel.setText("High Score: " + highScore);
    }

}