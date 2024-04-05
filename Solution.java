import java.util.*;


public class Solution {



    public List<String> heuristic_algorithm(Tile[][] puzzle, int[][] goal, Board board) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(); //contains the nodes to be evaluated
        HashSet<Node> closedSet = new HashSet<>(); //contains the nodes that have already been evaluated
        openSet.add(new Node(puzzle,0,manhattan_distance(goal, puzzle), null));
        //Define the initial state object and add it into openSet


        long startTime = System.currentTimeMillis(); // Starting time of the algorithm
        long timeLimit = 10000; //I Defined this because some puzzle states were taking too much time to solve








        while(!openSet.isEmpty())
        {

            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > timeLimit) {
                //Time limit exceeded
                System.out.println( "Puzzle not solvable within time limit");
                return null;
            }



            Node currentNode = openSet.poll();
            if(compareArrays(currentNode.getState(), goal)) {//checking if the current node's state reached the goal state
                return solutionPath(currentNode);
            }
            closedSet.add(currentNode);



            List<Node> neighbor = generateNeighbor(currentNode, goal);
            //neighbor represent the possible next states resulting from valid moves from the current state


            //this for loop ensures that all possible states have been explored or until the goal state is reached.
            for (Node i : neighbor) {  //"i" represents each neighbor node
                if (closedSet.contains(i)) {
                    continue; // Skip the loop if the neighbor is already evaluated
                } else if (!openSet.contains(i)) {
                    openSet.add(i); // Add the neighbor to openSet if it's not already there

                } else {
                    // If a better path to this node is found
                    Node existing = openSet.stream().filter(n -> n.equals(i)).findFirst().orElse(null);
                    if (existing != null && existing.getFCost() > i.getgCost()) { // Check if the current path is better by comparing
                        openSet.remove(existing); // Remove the existing node from openSet
                        openSet.add(i); // Add the neighbor with the better path to openSet
                    }
                }
            }
        }
        return null;
    }







    public List<Node> generateNeighbor(Node currentNode, int[][] goal)
    {

        List<Node> neighbors = new ArrayList<>(); //array to keep possible next states


        //find empty cell
        int emptyCellRow = -1;
        int emptyCellCol = -1;
        Tile[][] currentState = currentNode.getState();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentState[i][j] == null ) {
                    emptyCellRow = i;
                    emptyCellCol = j;
                    break;
                }

            }
        }
        // Generate neighbors by swapping the empty cell with its adjacent cells
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // U, D, L, R
        for (int[] direction : directions) {
            int newRow = emptyCellRow + direction[0];
            int newCol = emptyCellCol + direction[1];

            if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) { //checking valid positions
                Tile[][] newState = new Tile[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (currentState[i][j] != null) {
                            newState[i][j] = new Tile(currentState[i][j].getValue());
                        } else {
                            newState[i][j] = null;
                        }
                    }
                }

                Tile temp = newState[emptyCellRow][emptyCellCol];
                newState[emptyCellRow][emptyCellCol] = newState[newRow][newCol];
                newState[newRow][newCol] = temp;

                // Calculate gCost, hCost, and create the neighbor node
                int gCost = currentNode.getgCost() + 1;
                int hCost = manhattan_distance(goal, newState);
                Node neighbor = new Node(newState, gCost, hCost, currentNode);

                neighbors.add(neighbor);
            }
        }
        return neighbors;


    }

    public int manhattan_distance(int[][] goal, Tile[][] puzzle)
    {
        int dist=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (puzzle[i][j] != null  && goal[i][j] != puzzle[i][j].getValue()) {
                    int row_num = find_row_num(puzzle[i][j].getValue(), goal);
                    int col_num = find_col_num(puzzle[i][j].getValue(), goal);
                    dist += Math.abs(row_num - i) + Math.abs(col_num - j);
                } //let puzzle[1][0]==3, dist = abs(0-1) + abs (2-0) = 3

            }

        }
        return dist;
    }



    //These two methods below is for finding which row and column a number should be
    public int find_row_num(int num_to_be_find, int[][] goal)
    {


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (goal[i][j]  == num_to_be_find) {
                    return i;
                }

            }

        }


        return -1;


    }
    public int find_col_num(int num_to_be_find, int[][] goal)
    {


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (goal[i][j]  == num_to_be_find) {
                    return j;
                }
            }

        }

        return -1;
    }

    public List<String> solutionPath(Node node) {
        List<String> moves = new ArrayList<>();

        while (node != null && node.getParent() != null) {
            List<String> path = getMove(node.getParent(), node);
            moves.addAll(0, path); // Prepend to reverse the order
            node = node.getParent();
        }

        return moves; // Return the list of moves
    }


    private  List<String> getMove(Node from, Node to) {
        List<String> moves = new ArrayList<>();

        // Determine the move required to transition from 'from' state to 'to' state
        Tile[][] fromState = from.getState();
        Tile[][] goalState = to.getState();

        int emptyRow_from = -1;
        int emptyCol_from = -1;

        int emptyRow_to = -1;
        int emptyCol_to = -1;

        // First, find the empty states
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (fromState[i][j] == null) {
                    emptyRow_from = i;
                    emptyCol_from = j;
                }
                if (goalState[i][j] == null) {
                    emptyRow_to = i;
                    emptyCol_to = j;
                }
            }
        }
        int diffCol = emptyCol_from - emptyCol_to; //Find the difference between them to determine the move that has made
        int diffRow = emptyRow_from - emptyRow_to;

        if (diffCol == 1) {
            moves.add("L");
        } else if (diffCol == -1) {
            moves.add("R");
        } else if (diffRow == 1) {
            moves.add("U");
        } else if (diffRow == -1) {
            moves.add("D");
        }

        return moves;
    }



    public boolean compareArrays(Tile[][] currentState, int[][] goalState) {
        // Convert Tile[][] to int[][] for using Arrays.deepEquals() function
        int[][] currentStateValues = new int[3][3];
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState[0].length; j++) {
                if (currentState[i][j] != null) {
                    currentStateValues[i][j] = currentState[i][j].getValue();
                } else {
                    currentStateValues[i][j] = 0; // Represent empty cell as 0
                }
            }
        }


        return Arrays.deepEquals(currentStateValues, goalState);
    }

}