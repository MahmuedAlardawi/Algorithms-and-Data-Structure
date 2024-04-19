/*
Students:-
> Mahmued Alardawi - 2135209 - mmalardawi@stu.kau.edu.sa
> Monther Mosa - 2142804 - manwarmosa@stu.kau.edu.sa

Course: CPCS-324
Section: CS3
submission: 5/29/2023
*/

import java.util.*;

public class Algorithms {
    public HashMap<Character, Integer> ShiftTable(String pattern) {
        HashMap<Character, Integer> table = new HashMap<>();

        for (int i = 0; i <= pattern.length() - 2; i++) {
            table.put(pattern.charAt(i), pattern.length() - 1 - i);
        }

        return table;
    }

    public int[][] AdjacencyMatrix_undirected(Scanner fileInput, int vertices) {
        int[][] matrix = new int[vertices][vertices];
        for (int[] ints : matrix) {Arrays.fill(ints, 0);}
        while (fileInput.hasNext()) {
            int v = fileInput.nextInt();
            int u = fileInput.nextInt();
            int w = fileInput.nextInt();
            matrix[v][u] = w;
            matrix[u][v] = w;
        }
        return matrix;
    }
    public int[][] AdjacencyMatrix_directed(Scanner fileInput, int vertices) {
        int[][] matrix = new int[vertices][vertices];
        for (int[] ints : matrix) {Arrays.fill(ints, 0);}
        while (fileInput.hasNext()) {
            int v = fileInput.nextInt();
            int u = fileInput.nextInt();
            int w = fileInput.nextInt();
            matrix[v][u] = w;
        }
        return matrix;
    }

    public ArrayList<ArrayList<Node_v_w>> AdjacencyList(int[][] adjacencyMatrix) {
        ArrayList<ArrayList<Node_v_w>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (int parentV = 0; parentV < adjacencyMatrix.length; parentV++) {
            for (int v = 0; v < adjacencyMatrix[parentV].length; v++) {
                if (adjacencyMatrix[parentV][v] > 0) {
                    adjacencyList.get(parentV).add(new Node_v_w(v, adjacencyMatrix[parentV][v]));
                }
            }
        }

        return adjacencyList;
    }

    public static Stack<Node_v_u_w> PriorityStack(Stack<Node_v_u_w> input) {
        Stack<Node_v_u_w> tempSTack = new Stack<>();

        while (!input.isEmpty()) {
            Node_v_u_w temp = input.pop();

            while (!tempSTack.isEmpty() && tempSTack.peek().getW() < temp.getW()) {
                input.push(tempSTack.pop());
            }
            tempSTack.push(temp);
        }
        return tempSTack;
    }

    public int BruteForceStringMatch(String pattern, StringBuilder text) {
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;

            while (j < pattern.length() && pattern.charAt(j) == text.charAt(i + j)) {j += 1;}
            if (j == pattern.length()) {return i;}
        }
        return -1;
    }
    public int HorspoolMatch(String pattern, StringBuilder text) {
        HashMap<Character, Integer> table = ShiftTable(pattern);
        int i = pattern.length() - 1;

        while (i <= text.length() - 1) {
            int j = 0;

            while (j <= pattern.length() - 1 && pattern.charAt(pattern.length() - 1 - j) == text.charAt(i - j)) {
                j += 1;
            }
            if (j == pattern.length()) {
                return i - pattern.length() + 1;
            }
            else {
                if (table.containsKey(text.charAt(i))) {i += (int) table.get(text.charAt(i));}
                else {i += pattern.length();}
            }
        }
        return -1;
    }

    public static void PrimMST_priorityQueue(ArrayList<ArrayList<Node_v_w>> adjacencyList, int verticesN) {
        long start, end, finalTime;

        int[] order = new int[verticesN];
        int[] parent = new int[verticesN];
        int[] weights = new int[verticesN];
        boolean[] inMST = new boolean[verticesN];
        for (int i = 0; i < verticesN; i++) {
            parent[i] = -1;
            weights[i] = 1000000;
            inMST[i] = false;
        }
        Queue<Node_v_w> priorityQueue = new PriorityQueue<>(verticesN, new Node_v_w());
        start = System.nanoTime();

        weights[0] = 0;
        priorityQueue.add(new Node_v_w(0, weights[0]));

        for (int i = 0; i < verticesN; i++) {
            int v = Objects.requireNonNull(priorityQueue.poll()).getVertx();
            inMST[v] = true;
            order[i]= v;

            for (Node_v_w edge : adjacencyList.get(v)) {
                if (!inMST[edge.getVertx()] && edge.getWeight() < weights[edge.getVertx()]) {
                    parent[edge.getVertx()] = v;
                    weights[edge .getVertx()] = edge.getWeight();
                    priorityQueue.add(new Node_v_w(edge.getVertx(), weights[edge.getVertx()]));
                }
            }
        }
        end = System.nanoTime();
        finalTime = end - start;
        int totalWeight = 0;
        for (int w: weights) {totalWeight += w;}

        System.out.println("Total weight of MST by Prim's algorithm ((Using unordered Min-Priority queue): "
                + totalWeight);
        System.out.println("The edges in the MST are:");
        for (int i = 1; i < order.length; i++) {
            System.out.println("Edge from " + parent[order[i]] + " to " + order[i] + " has weight "
                    + (float) weights[order[i]]);
        }
        System.out.println("Running time of Prim’s algorithm using Min-Heap is " + finalTime + " Nano seconds");
        System.out.println();
    }
    public static void PrimMST_minHeap(ArrayList<ArrayList<Node_v_w>> adjacencyList, int verticesN) {
        long start, end, finalTime;

        int[] order = new int[verticesN];
        int[] parent = new int[verticesN];
        int[] weights = new int[verticesN];
        boolean[] inMST = new boolean[verticesN];
        for (int i = 0; i < verticesN; i++) {
            parent[i] = -1;
            weights[i] = 1000000;
            inMST[i] = false;
        }
        PriorityQueue<Node_v_w> minHeap = new PriorityQueue<>(verticesN, new Node_v_w());
        start = System.nanoTime();

        weights[0] = 0;
        minHeap.add(new Node_v_w(0, weights[0]));

        for (int i = 0; i < verticesN; i++) {
            int v = Objects.requireNonNull(minHeap.poll()).getVertx();
            inMST[v] = true;
            order[i] = v;

            for (Node_v_w edge : adjacencyList.get(v)) {
                if (!inMST[edge.getVertx()] && edge.getWeight() < weights[edge.getVertx()]) {
                    parent[edge.getVertx()] = v;
                    weights[edge .getVertx()] = edge.getWeight();
                    minHeap.add(new Node_v_w(edge.getVertx(), weights[edge.getVertx()]));
                }
            }
        }
        end = System.nanoTime();
        finalTime = end - start;
        int totalWeight = 0;
        for (int w: weights) {totalWeight += w;}

        System.out.println("Total weight of MST by Prim's algorithm (Using Min-Heap): " + totalWeight);
        System.out.println("The edges in the MST are:");
        for (int i = 1; i < order.length; i++) {
            System.out.println("Edge from " + parent[order[i]] + " to " + order[i] + " has weight "
                    + (float) weights[order[i]]);
        }
        System.out.println("Running time of Prim’s algorithm using Min-Heap is " + finalTime + " Nano seconds");
        System.out.println();
    }

    public static void KruskalMST(ArrayList<ArrayList<Node_v_w>> adjacencyList) {
        long start, end, finalTime;
        DisjointSet disjointSet = new DisjointSet(adjacencyList.size());
        Stack<Node_v_u_w> unsortedStack = new Stack<>();
        int totalWeight = 0;

        for (int v = 0; v < adjacencyList.size(); v++) {
            for (int u = 0; u < adjacencyList.get(v).size(); u++) {
                unsortedStack.add(new Node_v_u_w(v, adjacencyList.get(v).get(u).getVertx(),
                        adjacencyList.get(v).get(u).getWeight()));
            }
        }
        Stack<Node_v_u_w> sortedStack = PriorityStack(unsortedStack);
        ArrayList<Node_v_u_w> edges = new ArrayList<>();

        start = System.nanoTime();
        while (!sortedStack.isEmpty()) {
            Node_v_u_w edge = sortedStack.pop();

            if (!disjointSet.findSet(edge.getV(), edge.getU())) {
                disjointSet.union(edge.getV(), edge.getU());
                edges.add(edge);
                totalWeight += edge.getW();
            }
        }
        end = System.nanoTime();
        finalTime = end - start;

        System.out.println("Total weight of MST by Kruskal's algorithm: " + totalWeight);
        System.out.println("The edges in the MST are:");
        for (Node_v_u_w e : edges) {
            System.out.println("Edge from " + e.getV() + " to " + e.getU() + " has weight " + (float) e.getW());
        }
        System.out.println("Running Time of Kruskal’s algorithm using Union-Find approach is " +
                finalTime +" Nano seconds.");
    }

    public static long DijkstraMST_priorityQueue(ArrayList<ArrayList<Node_v_w>> adjacencyList,
                                                 int verticesN, int vertex) {
        long start, end;

        int[] parent = new int[verticesN];
        int[] distance = new int[verticesN];
        Arrays.fill(distance, 1000000);
        Queue<Node_v_w> priorityQueue= new PriorityQueue<>(verticesN, new Node_v_w());

        parent[vertex] = vertex;
        distance[vertex] = 0;
        priorityQueue.add(new Node_v_w(vertex, distance[vertex]));

        start = System.nanoTime();
        while (!priorityQueue.isEmpty()) {
            int v = priorityQueue.peek().getVertx();
            int d = Objects.requireNonNull(priorityQueue.poll()).getWeight();

            for (int i = 0; i < adjacencyList.get(v).size(); i++) {
                int edgeNode = adjacencyList.get(v).get(i).getVertx();
                int edgeWeight = adjacencyList.get(v).get(i).getWeight();


                if (distance[v] + edgeWeight < distance[edgeNode]) {
                    distance[edgeNode] = d + edgeWeight;
                    priorityQueue.add(new Node_v_w(edgeNode, distance[edgeNode]));
                    parent[edgeNode] = v;
                }
            }
        }
        end = System.nanoTime();

        ArrayList<ArrayList<Integer>> path = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            path.add(new ArrayList<>());
            path.get(i).add(i);
            int p = i;

            for (int j = 0; j < parent.length; j++) {
                if (p == vertex) {
                    break;
                }
                for (int l = 0; l < parent.length; l++) {
                    if (parent[p] == l) {
                        path.get(i).add(l);
                        p = l;
                        break;
                    }
                }
            }
        }

        System.out.println("Dijkstra using priority queue: ");
        System.out.println("Shortest paths from vertex " + vertex + " are: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print("A path from " + vertex + " to " + i + ": ");
            for (int j = path.get(i).size() - 1; j >= 0; j--) {
                System.out.print(path.get(i).get(j) + " ");
            }
            System.out.println("(Length:" + (float) distance[i] + ")");
        }
        System.out.println();

        return end - start;
    }
    public static long DijkstraMST_minHeap(ArrayList<ArrayList<Node_v_w>> adjacencyList,
                                           int verticesN, int vertex) {
        long start, end;

        int[] parent = new int[verticesN];
        int[] distance = new int[verticesN];
        Arrays.fill(distance, 1000000);
        PriorityQueue<Node_v_w> minHeap = new PriorityQueue<>(verticesN, new Node_v_w());

        parent[vertex] = vertex;
        distance[vertex] = 0;
        minHeap.add(new Node_v_w(vertex, distance[vertex]));

        start = System.nanoTime();
        while (!minHeap.isEmpty()) {
            int v = minHeap.peek().getVertx();
            int d = Objects.requireNonNull(minHeap.poll()).getWeight();

            for (int i = 0; i < adjacencyList.get(v).size(); i++) {
                int edgeNode = adjacencyList.get(v).get(i).getVertx();
                int edgeWeight = adjacencyList.get(v).get(i).getWeight();


                if (distance[v] + edgeWeight < distance[edgeNode]) {
                    distance[edgeNode] = d + edgeWeight;
                    minHeap.add(new Node_v_w(edgeNode, distance[edgeNode]));
                    parent[edgeNode] = v;
                }
            }
        }
        end = System.nanoTime();

        ArrayList<ArrayList<Integer>> path = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            path.add(new ArrayList<>());
            path.get(i).add(i);
            int p = i;

            for (int j = 0; j < parent.length; j++) {
                if (p == vertex) {
                    break;
                }
                for (int l = 0; l < parent.length; l++) {
                    if (parent[p] == l) {
                        path.get(i).add(l);
                        p = l;
                        break;
                    }
                }
            }
        }

        System.out.println("Dijkstra using min heap: ");
        System.out.println("Shortest paths from vertex " + vertex + " are: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print("A path from " + vertex + " to " + i + ": ");
            for (int j = path.get(i).size() - 1; j >= 0; j--) {
                System.out.print(path.get(i).get(j) + " ");
            }
            System.out.println("(Length:" + (float) distance[i] + ")");
        }
        System.out.println();

        return end - start;
    }
}


class Node_v_w implements Comparator<Node_v_w> {
    private int vertx;
    private int weight;

    public Node_v_w(int vertx, int weight) {
        this.vertx = vertx;
        this.weight = weight;
    }
    public Node_v_w() {}

    public int getVertx() {
        return vertx;
    }
    public void setVertx(int vertx) {
        this.vertx = vertx;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node_v_w{" +
                "vertx=" + vertx +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compare(Node_v_w n1, Node_v_w n2) {
        return Integer.compare(n1.weight, n2.weight);
    }
}
class Node_v_u_w implements Comparator<Node_v_u_w> {
    private int v;
    private int u;
    private int w;

    public Node_v_u_w(int v, int u, int w) {
        this.v = v;
        this.u = u;
        this.w = w;
    }

    public int getV() {
        return v;
    }
    public void setV(int v) {
        this.v = v;
    }
    public int getU() {
        return u;
    }
    public void setU(int u) {
        this.u = u;
    }
    public int getW() {
        return w;
    }
    public void setW(int w) {
        this.w = w;
    }

    @Override
    public String toString() {
        return "Node_v_u_w{" +
                "v=" + v +
                ", u=" + u +
                ", w=" + w +
                '}';
    }

    @Override
    public int compare(Node_v_u_w n1, Node_v_u_w n2) {
        return Integer.compare(n1.w, n2.w);
    }
}
class DisjointSet {
    ArrayList<Integer> parent = new ArrayList<>();
    ArrayList<Integer> rank = new ArrayList<>();

    public DisjointSet (int n) {
        for (int i = 0; i < n; i++) {
            this.parent.add(i);
            this.rank.add(0);
        }
    }

    public int findParent(int v) {
        if (v == parent.get(v)) {return v;}
        return findParent(parent.get(v));
    }

    public boolean findSet(int v, int u) {
        return findParent(v) == findParent(u);
    }

    public void union (int v, int u) {
        if (!findSet(v, u)) {
            int v_p = findParent(v);
            int u_p = findParent(u);

            if (rank.get(v_p) < rank.get(u_p)) {
                rank.set(u_p, rank.get(u_p) + 1);
                parent.set(v_p, u_p);
            } else {
                rank.set(v_p, rank.get(v_p) + 1);
                parent.set(u_p, v_p);
            }
        }
    }
}
