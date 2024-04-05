import java.awt.*;
import java.awt.event.KeyEvent; // for the constants of the keys on the keyboard
import java.util.List;

// A program that partially implements the 8 puzzle.
public class EightPuzzle {
   // The main method is the entry point where the program starts execution.
   public void draw(Board board, List<String> solution) {

      // StdDraw setup
      // -----------------------------------------------------------------------
      // set the size of the canvas (the drawing area) in pixels
      StdDraw.setCanvasSize(500, 500);
      // set the range of both x and y values for the drawing canvas
      StdDraw.setScale(0.5, 3.5);
      // enable double buffering to animate moving the tiles on the board
      StdDraw.enableDoubleBuffering();


      // create a random board for the 8 puzzle

      if(solution == null){
         StdDraw.pause(500);
         StdDraw.clear(Color.red);
         StdDraw.setPenColor(Color.WHITE);
         Font font = new Font("Arial", Font.BOLD,15);
         StdDraw.setFont(font);
         StdDraw.text(2,2,"PUZZLE IS NOT SOLVABLE!");
         StdDraw.show();
         StdDraw.pause(5000);
      }


      for (String move : solution) {
         board.draw();
         StdDraw.show();
         StdDraw.pause(300); //ms
         switch (move) {
            case "U":
               board.moveUp();
               break;
            case "D":
               board.moveDown();
               break;
            case "R":
               board.moveRight();
               break;
            case "L":
               board.moveLeft();
               break;
         }

      }
      StdDraw.pause(500);
      StdDraw.clear(Color.green);
      StdDraw.setPenColor(Color.WHITE);
      Font font = new Font("Arial", Font.BOLD,15);
      StdDraw.setFont(font);
      StdDraw.text(2,2,"PUZZLE IS SOLVED!");
      StdDraw.show();
      StdDraw.pause(5000);










      // The main animation and user interaction loop
      // -----------------------------------------------------------------------
      while (true) {
         // draw the board, show the resulting drawing and pause for a short time
         board.draw();
         StdDraw.show();
         StdDraw.pause(100); // 100 ms
         // if the user has pressed the right arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
            // move the empty cell right
            board.moveRight();
         // if the user has pressed the left arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
            // move the empty cell left
            board.moveLeft();
         // if the user has pressed the up arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
            // move the empty cell up
            board.moveUp();
         // if the user has pressed the down arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
            // move the empty cell down
            board.moveDown();
      }



   }





}