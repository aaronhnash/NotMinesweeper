package NotMinesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Gameboard{

    private ArrayList<ArrayList<Node>> board;
    //The <E> indicates what's going to be inside of the array. In this case, our "template" is the Person class.
    //ArrayList<E>
    // Creates an array of arrays?
    private int width;
    private int height;
    private int mine_count;
    //private ArrayList<ArrayList<int>>; // for the coordinate list. Couldn't I just use the list of nodes, though?


    public Gameboard(int width, int height, int mine_count) {
        this.width = width;
        this.height = height;
        this.mine_count = mine_count;
        ArrayList < ArrayList<Node> > board = new ArrayList<ArrayList<Node> >(height);
        this.board = board;
        //this.coord_list =

        for (int ix = 0; ix < this.width; ix++) {
            // How many rows will there be? That's determined by the height variable.

            // Create a new row.
            ArrayList<Node> row = new ArrayList<Node>();
            for (int iy = 0; iy < this.height; iy++) {
                // What's going to be in each column?
                Node current = new Node(ix, iy);
                //System.out.println("New node at: " +current.x +" , "+current.y);
                row.add(current);
            }
            this.board.add(row);
        }
        AddMines(this.mine_count);
    }

    public void DebugBoard() {
        for (int ix = 0; ix < this.width; ix++) {
            for (int iy = 0; iy < this.height; iy++) {
                Node curr = this.board.get(ix).get(iy);
                System.out.println("Node: " +curr.x+", "+curr.y+": "+curr.value);
            }
        }
    }

    public void PrintBoard() {
        for (int ix = 0; ix < this.width; ix++) {
            String row = "";
            for (int iy = 0; iy < this.height; iy++) {
                Node curr = this.board.get(ix).get(iy);
                String symbol = curr.value;
                if (curr.hidden) {
                    symbol = "-";
                }
                else {
                    symbol = curr.value;
                }
                row +=symbol+"   ";
            }
            System.out.println(row);
        }
        System.out.println("");

    }


    private void AddMines(int count) {
        // Function that adds as many mines as count says
        /* Possible: Create a list of coordinates, and then use something like
        python's random.choice function to choose a mine location. Once the coordinates are chosen,
        remove that entry from the list.
        */
        // Basic function to add mines.
        // TODO: enhance mine placing function, since some mines could overlap right now.
        Random random = new Random();

        for (int n = 0; n < count; n++) {
            int new_x = random.nextInt(width);
            int new_y = random.nextInt(height);
            this.board.get(new_x).get(new_y).value = "X";
        }
    }
    public void PopulateCount() {

        for (int ix = 0; ix < this.width; ix++) {
            for (int iy = 0; iy < this.height; iy++) {
                Node curr = this.board.get(ix).get(iy);
                SetCount(curr);
                curr.hidden = true; //TODO: remove this line of code once the game is in working order
            }
        }

    }
    private void SetCount(Node curr) {
        if (curr.value != "X") {
            int x = curr.x;
            int y = curr.y;
            int n = 0;

            // Up: x, y-1
            // Up left: x-1, y-1
            // left: x-1, y
            // down left: x-1, y+1
            // down: x, y+1
            // down right: x+1, y+1
            // right: x+1, y
            // up right: x+1, y-1

            // Since there could be an array out of bounds error, putting each statement should fix it
            try {if (this.board.get(x).get(y-1).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e1) {}
            try {if (this.board.get(x-1).get(y-1).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e2) {}
            try {if (this.board.get(x-1).get(y).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e3) {}
            try {if (this.board.get(x-1).get(y+1).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e4) {}
            try {if (this.board.get(x).get(y+1).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e5) {}
            try {if (this.board.get(x+1).get(y+1).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e6) {}
            try {if (this.board.get(x+1).get(y).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e7) {}
            try {if (this.board.get(x+1).get(y-1).value=="X") {n+=1;}} catch (IndexOutOfBoundsException e8) {}

            curr.value = Integer.toString(n);

        }
    }

    public void Reveal(int x, int y) {

        Node curr = this.board.get(x).get(y);
        if (curr.hidden == true) {
            if (!(curr.value.equals("X"))) {
                // Let's test it to see if we can reveal more around it in this recursive entry
                CascadingReveal(curr);
            }
        }
    }
    private void CascadingReveal(Node curr) {
        // Has this node been revealed yet? If not, let's test around it
        if (curr.hidden) {
            curr.hidden = false;
            if (curr.value.equals("0")) {
                // There's no mine here, no numbers, nothing
                // AKA if this is empty, test to see if we can reveal any adjacent tiles.
                // Otherwise, the party ends here.
                int x = curr.x;
                int y = curr.y;
                try {
                    CascadingReveal(this.board.get(x).get(y + 1));
                } catch (IndexOutOfBoundsException e1) {
                } // test up
                try {
                    CascadingReveal(this.board.get(x).get(y - 1));
                } catch (IndexOutOfBoundsException e2) {
                } // test down
                try {
                    CascadingReveal(this.board.get(x - 1).get(y));
                } catch (IndexOutOfBoundsException e3) {
                } // test left
                try {
                    CascadingReveal(this.board.get(x + 1).get(y));
                } catch (IndexOutOfBoundsException e4) {
                } // test right
            }
        }
    }

}

