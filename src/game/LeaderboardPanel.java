package game;

import javax.swing.*;
import java.awt.*;

public class LeaderboardPanel extends JPanel {

    JTextArea area;
    GameWindow window;

    public LeaderboardPanel(GameWindow window) {

        this.window = window;

        setLayout(null);
        setBackground(Color.BLACK);

        JLabel title = new JLabel("LEADERBOARD");
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBounds(250, 30, 300, 40);

        add(title);

        area = new JTextArea();

        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(area);

        scroll.setBounds(150,100,500,400);

        add(scroll);

        JButton menu = new JButton("MAIN MENU");

        menu.setBounds(300,540,200,40);

        menu.addActionListener(e -> {
            window.showMenu();
        });

        add(menu);
    }

    public void setData(String text) {
        area.setText(text);
    }
}