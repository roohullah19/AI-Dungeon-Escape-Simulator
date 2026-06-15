package stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class AlgorithmManager {

    private ArrayList<AlgorithmStats> bfsHistory = new ArrayList<>();
    private ArrayList<AlgorithmStats> dfsHistory = new ArrayList<>();
    private ArrayList<AlgorithmStats> aStarHistory = new ArrayList<>();
    private static final int MAX_HISTORY = 500;
    private static final String FILE_NAME = "algorithm_stats.txt";


    public void addBFSStats(AlgorithmStats stats) {
        bfsHistory.add(copyStats(stats));
        if (bfsHistory.size() > MAX_HISTORY) {
            bfsHistory.remove(0);
        }
    }

    public void addDFSStats(AlgorithmStats stats) {
        dfsHistory.add(copyStats(stats));
        if (dfsHistory.size() > MAX_HISTORY) {
            dfsHistory.remove(0);
        }
    }

    public void addAStarStats(AlgorithmStats stats) {
        aStarHistory.add(copyStats(stats));
        if (aStarHistory.size() > MAX_HISTORY) {
            aStarHistory.remove(0);
        }
    }

    private AlgorithmStats copyStats(AlgorithmStats stats) {
        AlgorithmStats copy = new AlgorithmStats();

        copy.visitedNodes = stats.visitedNodes;
        copy.pathLength = stats.pathLength;
        copy.executionTime = stats.executionTime;
        copy.heapOperations = stats.heapOperations;

        return copy;
    }

    public double getAverageBFSTime() {
        return averageTime(bfsHistory);
    }

    public double getAverageDFSTime() {
        return averageTime(dfsHistory);
    }

    public double getAverageAStarTime() {
        return averageTime(aStarHistory);
    }

    private double averageTime(ArrayList<AlgorithmStats> list) {

        if (list.isEmpty()) {
            return 0;
        }
        long total = 0;
        for (AlgorithmStats stats : list) {
            total += stats.executionTime;
        }

        return (double) total / list.size();
    }

    public double getAverageBFSVisited() {
        return averageVisited(bfsHistory);
    }

    public double getAverageDFSVisited() {
        return averageVisited(dfsHistory);
    }

    public double getAverageAStarVisited() {
        return averageVisited(aStarHistory);
    }

    private double averageVisited(ArrayList<AlgorithmStats> list) {

        if (list.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (AlgorithmStats stats : list) {
            total += stats.visitedNodes;
        }

        return (double) total / list.size();
    }

    public int getBFSRuns() {
        return bfsHistory.size();
    }

    public double getAverageBFSPorthLength() {
        return averagePathLength(bfsHistory);
    }

    public double getAverageDFSPorthLength() {
        return averagePathLength(dfsHistory);
    }

    public double getAverageAStarPathLength() {
        return averagePathLength(aStarHistory);
    }

    private double averagePathLength(ArrayList<AlgorithmStats> list) {
        if (list.isEmpty()) return 0;

        int total = 0;
        for (AlgorithmStats stats : list) {
            total += stats.pathLength;
        }
        return (double) total / list.size();
    }

    public int getDFSRuns() {
        return dfsHistory.size();
    }

    public int getAStarRuns() {
        return aStarHistory.size();
    }

    public ArrayList<AlgorithmStats> getBFSHistory() {
        return bfsHistory;
    }

    public ArrayList<AlgorithmStats> getDFSHistory() {
        return dfsHistory;
    }

    public ArrayList<AlgorithmStats> getAStarHistory() {
        return aStarHistory;
    }

    public static void savePlayerStats(
            String name,
            double bfsP,
            double bfsV,
            double bfsT,
            double dfsP,
            double dfsV,
            double dfsT,
            double aP,
            double aV,
            double aT) {

        try (BufferedWriter bw =
                     new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            bw.write("------------------------------");
            bw.newLine();
            bw.write("Player: " + name);
            bw.newLine();

            bw.write("BFS  -> Path=" + bfsP +
                    ", Visited=" + bfsV +
                    ", Time=" + bfsT);
            bw.newLine();

            bw.write("DFS  -> Path=" + dfsP +
                    ", Visited=" + dfsV +
                    ", Time=" + dfsT);
            bw.newLine();

            bw.write("A*   -> Path=" + aP +
                    ", Visited=" + aV +
                    ", Time=" + aT);
            bw.newLine();

            bw.write("------------------------------");
            bw.newLine();
            bw.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}