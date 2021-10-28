package NotMinesweeper;
import java.util.Scanner;

public class Director {
    // Things that I need to do:
    // Each point will be an object, linked to what's around it (like a linked

    /* In REPL, it will look for the main function
       in a class called Main.  In other tools, you don't
       need to call the class Main.
    */
    public static void main(String[] args) {
        Gameboard board = new Gameboard(10,10, 10);
        //board.DebugBoard();
        //board.PrintBoard(); // This prints out the board in an un-initialized state, AKA cheating mode
        //board.PrintBoard();
        //board.Reveal(4,4);
        //board.PrintBoard();

        String inputStr;
        String command;
        // If no coordinates are provided, then the game defaults to 0,0 or whatever was last used.
        int inputX = 0;
        int inputY = 0;
        Scanner scan = new Scanner(System.in);
        while (true) {
            board.PrintBoard();
            board.CheckWin();
            inputStr = scan.nextLine();
            String[] inputArray = inputStr.split(" ");
            if (inputArray.length == 3) {
                inputX = Integer.parseInt(inputArray[2]);
                inputY = Integer.parseInt(inputArray[1]);

            }
            command = inputArray[0];

            switch (command) {
                case "flag" -> board.SetFlag(inputX, inputY);
                case "click" -> board.Reveal(inputX, inputY);
                case "remove" -> board.RemoveFlag(inputX, inputY);
                case "quit" -> System.exit(0);
            }
            }

    }
}
