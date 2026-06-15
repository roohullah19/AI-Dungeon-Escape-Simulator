package game;

import javax.swing.*;
import java.awt.*;

public class EndGamePanel extends JPanel {

    private GameWindow window;

    private JLabel resultLabel;
    private JLabel scoreLabel;
    private JLabel timeLabel;

    public EndGamePanel(GameWindow window) {

        this.window = window;

        setLayout(null);
        setBackground(Color.BLACK);

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 36));
        resultLabel.setBounds(150, 100, 500, 50);

        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setBounds(150, 180, 500, 30);

        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setBounds(150, 220, 500, 30);

        JButton playAgain = new JButton("PLAY AGAIN");
        playAgain.setBounds(280, 320, 200, 40);

        JButton leaderboard=new JButton("leaderboard");
        leaderboard.setBounds(280,380,200,40);

        JButton menu = new JButton("MAIN MENU");
        menu.setBounds(280, 440, 200, 40);

        JButton exit = new JButton("EXIT");
        exit.setBounds(280, 500, 200, 40);

        playAgain.addActionListener(e -> window.showSetupScreen());

        leaderboard.addActionListener(e ->window.showLeaderboard());

        menu.addActionListener(e -> window.showMenu());

        exit.addActionListener(e -> System.exit(0));

        add(resultLabel);
        add(scoreLabel);
        add(timeLabel);

        add(playAgain);
        add(leaderboard);
        add(menu);
        add(exit);
    }

    public void updateResult(boolean won, String playerName, int score, long timePlayed) {
        if (won) {
            resultLabel.setText("YOU ESCAPED");
            resultLabel.setForeground(Color.GREEN);
        } else {
            resultLabel.setText("GAME OVER");
            resultLabel.setForeground(Color.RED);
        }

        scoreLabel.setText("Player: " + playerName + "   Score: " + score);

        timeLabel.setText("Time Played: " + timePlayed + " sec");
    }
}