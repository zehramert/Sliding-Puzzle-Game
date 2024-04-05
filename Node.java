import java.util.Arrays;

public class Node implements Comparable<Node> {
    private Tile[][] puzzle_state;
    private int gCost; // Cost from start node to current node g(n)
    private int hCost; // Estimated cost from current node to goal node h(n)
    private Node parent; // Parent node

    public Node(Tile[][] puzzle_state, int gCost, int hCost, Node parent) {
        this.puzzle_state = puzzle_state;
        this.gCost = gCost;
        this.hCost = hCost;
        this.parent = parent;  //defining the object
    }


    //getter methods
    public Tile[][] getState() {
        return puzzle_state;
    }

    public int getFCost() {
        return gCost + hCost;
    }

    public int getgCost() { return gCost;}

    public Node getParent() {
        return parent;
    }


    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getFCost(), other.getFCost());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return Arrays.deepEquals(this.puzzle_state, other.puzzle_state);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(puzzle_state);
    }
}

