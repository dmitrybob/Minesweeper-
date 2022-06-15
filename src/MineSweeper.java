import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


//Dmitry Bobrov

public class MineSweeper extends JPanel {
    private Square[][] board;

    public static final int SIZE = 40;
    public boolean firstClick = true;


    public MineSweeper(int width, int height) {
        setSize(width, height);
        board = new Square[15][15];
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board[0].length; c++){
                boolean mine = true;
                if(Math.random() <= .2)
                    mine = true;
                else
                    mine = false;
                board[r][c] = new Square(mine,r,c, board);
            }
        }
        //TODO Go to each index in board and assign a new Square object
        //     Each square should have a 15% chance of being a mine
        //  ALT: have a fixed number of squares contain mines.




        // Here is a good spot to calc each Square's neighborMines
        // (after all squares are initialized-not in the above loops

        setupMouseListener();
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for(int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c].draw(g2);
            }
        }

        // TODO Go to each square in board and tell it to draw.

    }

    public void setupMouseListener(){
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int r = y / SIZE;
                int c = x / SIZE;
                if(firstClick){
                    for(int t = 1; t >= -1; t--) {
                        for (int v = 1; v >= -1; v--) {
                            if(board[0][0].isInBounds(r + v, c + t) && board[r + v][c + t].isMine()== true)
                                board[r + v][c + t].removeMine(r + v,c + t);
                        }
                    }
                    firstClick = false;
                    board[r][c].click();
                }
                if (e.getButton() == MouseEvent.BUTTON1)
                        board[r][c].click();
                else
                    if(!board[r][c].isRevealed())
                        board[r][c].isFlagged = !board[r][c].isFlagged;




                repaint();
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    //sets ups the panel and frame.  Probably not much to modify here.
    public static void main(String[] args) {
        JFrame window = new JFrame("Minesweeper");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 600, 600 + 22); //(x, y, w, h) 22 due to title bar.

        MineSweeper panel = new MineSweeper(600, 600);

        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(false);
    }

}