import java.util.*;
public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Tile[][] puzzle = Board.getTiles(); //puzzle will be equal to initial state

        Solution solution = new Solution();
        EightPuzzle ep = new EightPuzzle();  //creating instances




        int[][] goal = new int[3][3];
        int a = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goal[i][j] = a;
                a++;
            }
        }
        goal[2][2] = 0; //empty cell



        //printing states for debugging
        System.out.println("\nThe Goal State is:");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                System.out.print(goal[row][col] + " ");
            }
            System.out.println();
        }

        System.out.println("The Initial State is:");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Tile tile = puzzle[row][col];
                if (tile != null) {
                    System.out.print(tile.getValue() + " ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }


        if (hasSolution(puzzle)) {
            System.out.println("The Puzzle is solvable!");
            System.out.println("The solution is: ");
            List<String> SolutionPath = solution.heuristic_algorithm(puzzle, goal, board);
            printSolutionPath(SolutionPath);
            //if its solvable it calls the algorithm method and prints the solution path
            ep.draw(board, SolutionPath);
            board.drawSolution(SolutionPath);





        } else {
            System.out.println("The Puzzle is not solvable!");
            ep.draw(board, null);

        }




    }
    public static void printSolutionPath(List<String> solution) { //prints the solution path
        if (solution != null) {
            System.out.print("Solution Path:");
            for (int i = 0; i <= solution.size() - 1; i++) {
                System.out.print(solution.get(i) + " ");
            }
            System.out.println();
        } else {
            System.out.println("No solution found.");
        }

    }



    public static boolean hasSolution(Tile[][] puzzle) // Counts inverses and checks if the puzzle is solvable
    {

        int[] tempArray = new int[9];
        int k = 0;

        // Copy matrix into 1D array
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (puzzle[i][j] != null)
                    tempArray[k++] = puzzle[i][j].getValue();
                else
                    tempArray[k++] = 0;
            }

        int inverseCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (tempArray[i] != 0 && tempArray[j] != 0 && tempArray[i] > tempArray[j]) {
                    inverseCount++;
                }
            }
        }
        return inverseCount % 2 == 0;


    }


}
