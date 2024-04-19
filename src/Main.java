/*
Students:-
> Mahmued Alardawi - 2135209 - mmalardawi@stu.kau.edu.sa
> Monther Mosa - 2142804 - manwarmosa@stu.kau.edu.sa

Course: CPCS-324
Section: CS3
submission: 5/29/2023
*/

import java.io.*;
import java.util.*;

public class Main {
    // Main
    public static void main(String [] args) throws FileNotFoundException {
        Algorithms algorithms = new Algorithms();
        while (true) {
            System.out.println("""
                    1. Comparison between Horspool and Brute force algorithms
                    2. Finding minimum spanning tree using Prim’s algorithm
                    3. Finding minimum spanning tree using Kruskal’s algorithm
                    4. Finding shortest path using Dijkstra’s algorithm
                    5. Quit""");
            System.out.println();

            System.out.print("Choose a number: ");
            int input = userInput().nextInt();
            switch (input) {
                case 1 -> op1(algorithms, userInput(), fileInput(new File("input.txt")),
                        new PrintWriter("pattern.txt"));
                case 2 -> op2(algorithms, fileInput(new File("input1.txt")));
                case 3 -> op3(algorithms, fileInput(new File("input1.txt")));
                case 4 -> op4(algorithms, userInput(), fileInput(new File("input2.txt")));
                default -> System.exit(1);
            }
        }
    }

    // Scanners
    public static Scanner userInput() {return new Scanner(System.in);}
    public static Scanner fileInput(File file) throws FileNotFoundException {return new Scanner(file);}

    // Option 1: Comparison between Horspool and Brute force algorithms
    public static void op1(Algorithms algorithms, Scanner userInput, Scanner fileInput, PrintWriter patternFile) {
        // User inputs
        System.out.println("How many lines you want to read from the text file? ");
        int numberOfLines = userInput.nextInt();
        System.out.println("How many patterns to be generated? ");
        int numberOfPatterns = userInput.nextInt();
        System.out.println("What is the length of each pattern? ");
        int length = userInput.nextInt();
        System.out.println();

        // Saving text
        StringBuilder text = new StringBuilder();
        for (int l = 0; l < numberOfLines; l++) {
            text.append(fileInput.nextLine().toLowerCase().replaceAll("[^a-zA-Z]+", ""));
        }

        char[] alphabets = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'z' };
        Set<String> patterns = new HashSet<>();
        for (int p = 0; p < numberOfPatterns; p++) {
            StringBuilder pattern = new StringBuilder();
            for (int i = 0; i < length; i++)
                pattern.append(alphabets[(int) (Math.random() * 100 % 26)]);
            patterns.add(pattern.toString());
            patternFile.println(pattern);
        }
        patternFile.close();
        System.out.println(numberOfPatterns + " Patterns, each of length " + length + " have been generated in" +
                " file pattern.txt\n");

        // Shifting tables
        for (String pattern : patterns) {
            System.out.println("Shift table of pattern: (" + pattern + "):");
            System.out.println(algorithms.ShiftTable(pattern).keySet());
            System.out.println(algorithms.ShiftTable(pattern).values());
            System.out.println();
        }

        // Brute force algorithm
        long averageTimeBruteForce = 0, start, end;
        for (String pattern: patterns) {
            start = System.nanoTime();
            int bruteForceSolution = algorithms.BruteForceStringMatch(pattern, text);
            end = System.nanoTime();
            averageTimeBruteForce += end - start;
        }
        averageTimeBruteForce /= patterns.size();

        // Horspool algorithm
        long averageTimeHorspool = 0;
        for (String pattern: patterns) {
            start = System.nanoTime();
            int horspoolSolution = algorithms.HorspoolMatch(pattern, text);
            end = System.nanoTime();
            averageTimeHorspool += end - start;
        }
        averageTimeHorspool /= patterns.size();

        // Output
        System.out.println("Average time of search in Brute Force Approach: " + averageTimeBruteForce + " ns");
        System.out.println("Average time of search in Horspool Approach: " + averageTimeHorspool + " ns");

        if (averageTimeBruteForce < averageTimeHorspool) {
            System.out.println("For this instance Brute Force approach is better than Horspool approach");
        }
        else if (averageTimeBruteForce > averageTimeHorspool) {
            System.out.println("For this instance Horspool approach is better than Brute Force approach");
        }
        else {
            System.out.println("For this instance Brute Force approach is as good as Horspool approach");
        }
        System.out.println();
    }

    // Option 2 Finding minimum spanning tree using Prim’s algorithm
    public static void op2(Algorithms algorithms, Scanner fileInput) {
        // Creating adjacency matrix
        int verticesN = fileInput.nextInt();
        int edgesN = fileInput.nextInt();
        int[][] adjacencyMatrix_undirected = algorithms.AdjacencyMatrix_undirected(fileInput, verticesN);

        // Printing weighted matrix
        System.out.println("Adjacency matrix:");
        for (int[] Ints: adjacencyMatrix_undirected) {
            System.out.println(Arrays.toString(Ints));
        }
        System.out.println();

        // Creating adjacency list
        ArrayList<ArrayList<Node_v_w>> adjacencyList = algorithms.AdjacencyList(adjacencyMatrix_undirected);

        // (i) Unordered array as min-priority queue
        Algorithms.PrimMST_priorityQueue(adjacencyList, verticesN);


        // (ii) Min-heap as min-priority queue
        Algorithms.PrimMST_minHeap(adjacencyList, verticesN);

        System.out.println();
    }

    // Option 3: Finding minimum spanning tree using Kruskal’s algorithm
    public static void op3(Algorithms algorithms, Scanner fileInput) {
        int verticesN = fileInput.nextInt();
        int edgesN = fileInput.nextInt();
        int[][] adjacencyMatrix_undirected = algorithms.AdjacencyMatrix_undirected(fileInput, verticesN);

        // Printing weighted matrix
        System.out.println("Adjacency matrix:");
        for (int[] Ints: adjacencyMatrix_undirected) {
            System.out.println(Arrays.toString(Ints));
        }
        System.out.println();

        // Creating adjacency list
        ArrayList<ArrayList<Node_v_w>> adjacencyList = algorithms.AdjacencyList(adjacencyMatrix_undirected);

        // Stack for storing the weights of the edges
        Algorithms.KruskalMST(adjacencyList);

        System.out.println();
    }

    // Option 4: Finding the shortest path using Dijkstra’s algorithm
    public static void op4(Algorithms algorithms, Scanner userInput, Scanner fileInput) {
        // Creating adjacency matrix
        int verticesN = fileInput.nextInt();
        int edgesN = fileInput.nextInt();
        int[][] adjacencyMatrix_directed = algorithms.AdjacencyMatrix_directed(fileInput, verticesN);

        // Printing weighted matrix
        System.out.println("Weight Matrix: ");
        System.out.print("   ");
        for (int i = 0; i < verticesN; i++) {System.out.print(0 + "  ");}
        System.out.println();
        for (int i = 0; i < adjacencyMatrix_directed.length; i++) {
            System.out.print(i +"  ");
            for (int j = 0; j < adjacencyMatrix_directed[i].length; j++) {
                System.out.print(adjacencyMatrix_directed[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();

        // Creating adjacency list
        ArrayList<ArrayList<Node_v_w>> adjacencyList = algorithms.AdjacencyList(adjacencyMatrix_directed);

        // Printing adjacency list
        System.out.println("# of vertices is: " + verticesN + ", # of edges is: " + edgesN);
        for (int i = 0; i < adjacencyList.size(); i++) {
            System.out.print(i +":  ");
            for (int j = 0; j < adjacencyList.get(i).size(); j++) {
                System.out.print(i + "-" + adjacencyList.get(i).get(j).getVertx() + " " +
                        adjacencyList.get(i).get(j).getWeight() + "   ");
            }
            System.out.println();
        }
        System.out.println();

        // User input for starting vertex
        System.out.print("Enter Source vertex: ");
        int source = userInput.nextInt();
        System.out.println();

        // (i) Unordered array as min-priority queue
        long priorityQueue = Algorithms.DijkstraMST_priorityQueue(adjacencyList, verticesN, source);


        // (ii) Min-heap as min-priority queue
        long minHeap = Algorithms.DijkstraMST_minHeap(adjacencyList, verticesN, source);

        System.out.println("Comparison Of the running time :");
        System.out.println("Running time of Dijkstra using priority queue is: " + priorityQueue + " nano seconds");
        System.out.println("Running time of Dijkstra using min Heap is: " + minHeap + " nano seconds");
        if (priorityQueue < minHeap) {System.out.println("Running time of Dijkstra using priority queue is better");}
        else {System.out.println("Running time of Dijkstra using min Heap is better");}
        System.out.println();
    }
}
