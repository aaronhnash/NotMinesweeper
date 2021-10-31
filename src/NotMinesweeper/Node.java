package NotMinesweeper;

public class Node {

    public String value;
    public boolean hidden;
    public boolean flagged;
    public int x;
    public int y;
    // The constructor class here. Returns a fully initialized version of the class. Like __init__ in python.
    public Node(int x, int y){
        this.value = "0"; // initialize as empty
        this.hidden = false; // Initializes as hidden, as everything should. TODO: temp set to false, reset to true eventually
        this.x = x;
        this.y = y;
    }

}
