package NotMinesweeper;

public class Node {

    public String value;
    public boolean hidden;
    public boolean flagged;
    private Node up;
    private Node lup;
    private Node left;
    private Node ldown;
    private Node down;
    private Node rdown;
    private Node right;
    private Node rup;
    public int x;
    public int y;
    // The constructor class here. Returns a fully initialized version of the class. Like __init__ in python.
    public Node(int x, int y){
        this.value = "0"; // initialize as empty
        this.hidden = false; // Initializes as hidden, as everything should. TODO: temp set to false, reset to true eventually
        this.up = null;
        this.lup = null;
        this.left = null;
        this.ldown = null;
        this.down = null;
        this.rdown = null;
        this.right = null;
        this.rup = null;
        this.x = x;
        this.y = y;
    }

}
