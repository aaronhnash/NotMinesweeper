package NotMinesweeper;

import java.util.*;


public class Gameboard{

    private ArrayList<ArrayList<Node>> board;
    //The <E> indicates what's going to be inside of the array. In this case, our "template" is the Person class.
    //ArrayList<E>
    // Creates an array of arrays?
    private int width;
    private int height;
    private int mine_count;
    private int tile_count;
    private int flag_count;
    private boolean lost;
    private Random random;
    private int[][] coordArray;
    //private ArrayList<ArrayList<int>>; // for the coordinate list. Couldn't I just use the list of nodes, though?


    public Gameboard(int width, int height, int mine_count) {
        this.width = width;
        this.height = height;
        this.mine_count = mine_count;
        ArrayList < ArrayList<Node> > board = new ArrayList<>(height);
        this.board = board;
        this.lost = false;
        this.tile_count = 0;
        this.flag_count = this.mine_count;
        this.random = new Random();
        this.coordArray = new int[this.width*this.height][2];

        System.out.println(this.coordArray.length);

        //int[][] coordArray = new int[width*height][2];
        int count = 0;


        for (int ix = 0; ix < this.width; ix++) {
            // How many rows will there be? That's determined by the height variable.
            // Create a new row.
            ArrayList<Node> row = new ArrayList<>();
            for (int iy = 0; iy < this.height; iy++) {
                // What's going to be in each column?
                Node current = new Node(ix, iy);
                //System.out.println("New node at: " +current.x +" , "+current.y);
                row.add(current);

                // Also build a list full of coordinates, used when placing mines.
                this.coordArray[count][0] = ix;
                this.coordArray[count][1] = iy;
                count += 1;

            }
            this.board.add(row);
        }
        AddMines(this.mine_count);
        PopulateCount();
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
        int ylabel = 0;
        String header = " ";
        for (int xlabel = 0; xlabel < this.width; xlabel++) {
            header += " _ "+xlabel;
        }
         System.out.println(header);

        // This takes care of counting the number of unhidden tiles.
        this.tile_count = this.width*this.height;

        for (int ix = 0; ix < this.width; ix++) {
            String row = ylabel+" | ";
            for (int iy = 0; iy < this.height; iy++) {
                Node curr = this.board.get(ix).get(iy);
                String symbol = curr.value;

                if (curr.flagged) {
                    symbol = "F";

                    // Since this has been identified as a "flag", it isn't hidden, so we can subtract one.
                    this.tile_count -= 1;
                }
                else if (curr.hidden) {
                    symbol = "-";
                }
                else if (curr.value.equals("0")){
                    symbol = " ";

                    // Since this has been identified as a normal space, we can subtract one.
                    this.tile_count -=1;
                }
                else {
                    this.tile_count -=1;
                }
                row +=symbol+"   ";
            }
            System.out.println(row);
            ylabel+=1;
        }
        System.out.println();
        System.out.println(this.flag_count+"/"+this.mine_count+" mines left");
        System.out.println(this.tile_count+" tiles left");
    }


    private void AddMines(int count) {
        // Function that adds as many mines as count says
        /* Possible: Create a list of coordinates, and then use something like
        python's random.choice function to choose a mine location. Once the coordinates are chosen,
        remove that entry from the list.
        */
        // Basic function to add mines.

        // Takes the list of coordinate points and
        List<int[]> coordList = Arrays.asList(this.coordArray);
        Collections.shuffle(coordList);
        for (int n = 0; n < count; n++) {
            int[] point;
            point = coordList.get(n);
            this.board.get(point[0]).get(point[1]).value = "X";
        }
    }

    private void RollMine() {
        int new_x = random.nextInt(this.width);
        int new_y = random.nextInt(this.height);
        Node curr = this.board.get(new_x).get(new_y);
        if (curr.value.equals("X")) {RollMine();} // if there's already a mine, try again
        else {curr.value = "X";}
    }

    private void PopulateCount() {

        for (int ix = 0; ix < this.width; ix++) {
            for (int iy = 0; iy < this.height; iy++) {
                Node curr = this.board.get(ix).get(iy);
                SetCount(curr);
                curr.hidden = true; //TODO: remove this line of code once the game is in working order
            }
        }

    }
    private void SetCount(Node curr) {
        if (!curr.value.equals("X")) {
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
            try {if (this.board.get(x).get(y-1).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e1) {}
            try {if (this.board.get(x-1).get(y-1).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e2) {}
            try {if (this.board.get(x-1).get(y).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e3) {}
            try {if (this.board.get(x-1).get(y+1).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e4) {}
            try {if (this.board.get(x).get(y+1).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e5) {}
            try {if (this.board.get(x+1).get(y+1).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e6) {}
            try {if (this.board.get(x+1).get(y).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e7) {}
            try {if (this.board.get(x+1).get(y-1).value.equals("X")) {n+=1;}} catch (IndexOutOfBoundsException e8) {}


            curr.value = Integer.toString(n);

        }
    }

    public void Reveal(int x, int y) {

        Node curr = this.board.get(x).get(y);
        // Check if this tile is flagged or hidden. If revealed or flagged, ignore the "press".
        if (!curr.flagged) {
            if (curr.hidden) {
                if (!(curr.value.equals("X"))) {
                    // Let's test it to see if we can reveal more around it in this recursive entry
                    CascadingReveal(curr);
                }
                else {
                    // If the player just revealed a mine... game over!
                    this.lost = true;
                }
            }
        }
    }
    private void CascadingReveal(Node curr) {
        // Has this node been revealed yet, or is it flagged? If not, let's test around it
        if (!curr.flagged) {
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
                        CascadingReveal(this.board.get(x-1).get(y + 1));
                    } catch (IndexOutOfBoundsException e1) {
                    } // test up left
                    try {
                        CascadingReveal(this.board.get(x+1).get(y + 1));
                    } catch (IndexOutOfBoundsException e1) {
                    } // test up right

                    try {
                        CascadingReveal(this.board.get(x).get(y - 1));
                    } catch (IndexOutOfBoundsException e2) {
                    } // test down
                    try {
                        CascadingReveal(this.board.get(x-1).get(y - 1));
                    } catch (IndexOutOfBoundsException e2) {
                    } // test down left
                    try {
                        CascadingReveal(this.board.get(x+1).get(y - 1));
                    } catch (IndexOutOfBoundsException e2) {
                    } // test down right

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

    public void SetFlag(int x, int y) {
        Node curr = this.board.get(x).get(y);
        // We only want to flag hidden flags
            if (curr.hidden) {
                curr.flagged = true;
                this.flag_count -= 1;
            }
    }
    public void RemoveFlag(int x, int y) {
        Node curr = this.board.get(x).get(y);
        curr.flagged = false;
        this.flag_count += 1;
    }

    public void CheckWin() {
        // Checks if all of the mines have been flagged, and if all non-flagged spaces have been revealed.
        // Also checks if the player landed on a mine during this turn.
        if (this.lost) {
            System.out.println("Kaboom! Sorry, you blew up.");
            System.exit(0);
        }
        else {
            if (this.tile_count == 0) {
                if (this.flag_count ==0) {
                    System.out.println("You found them all, congrats!");
                    System.exit(0);
                }
            }
        }
    }

}

