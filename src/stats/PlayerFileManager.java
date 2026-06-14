package stats;

import java.io.*;
import java.util.*;

public class PlayerFileManager {

    private static final String FILE_NAME = "players.txt";

    public static Map<String, String[]> loadPlayers() {

        Map<String, String[]> players = new HashMap<>();

        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                file.createNewFile();
                return players;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    players.put(parts[0], new String[]{parts[1], parts[2], parts[3]});
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }

    public static void savePlayer(String name, int score, int games, long time) {

        Map<String, String[]> players = loadPlayers();

        if (players.containsKey(name)) {

            String[] old = players.get(name);

            int bestScore = Math.max(Integer.parseInt(old[0]), score);
            int totalGames = Integer.parseInt(old[1]) + games;
            long totalTime = Long.parseLong(old[2]) + time;

            players.put(name, new String[]{String.valueOf(bestScore), String.valueOf(totalGames), String.valueOf(totalTime)});

        } else {

            players.put(name, new String[]{String.valueOf(score), String.valueOf(games), String.valueOf(time)});
        }

        writeAll(players);
    }
    private static void writeAll(Map<String, String[]> players) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (String name : players.keySet()) {

                String[] data = players.get(name);

                bw.write(name + "," + data[0] + "," + data[1] + "," + data[2]);
                bw.newLine();
            }

            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
