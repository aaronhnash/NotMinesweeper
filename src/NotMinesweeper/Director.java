package NotMinesweeper;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class Director {

    public static void main(String[] args) {
        /*
        JFrame frame = new JFrame("NotMinesweeper");
        JLabel emptyLabel = new JLabel(("1"));
        //emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.getContentPane().add(new JLabel(("2")), BorderLayout.CENTER);
        */


        Gameboard board = new Gameboard(10,10, 15);
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
                inputX = Integer.parseInt(inputArray[1]);
                inputY = Integer.parseInt(inputArray[2]);

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
