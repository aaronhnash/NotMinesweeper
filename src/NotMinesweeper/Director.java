package NotMinesweeper;

public class Director {
    // Things that I need to do:
    // Each point will be an object, linked to what's around it (like a linked

    /* In REPL, it will look for the main function
       in a class called Main.  In other tools, you don't
       need to call the class Main.
    */
    public static void main(String[] args) {
        System.out.println("Hallo Warudo");
        Gameboard board = new Gameboard(10,10, 10);
        //board.DebugBoard();
        board.PrintBoard(); // This prints out the board in an un-initialized state, AKA cheating mode
        board.PopulateCount();
        board.PrintBoard();
        board.Reveal(4,4);
        board.PrintBoard();
    }
}
