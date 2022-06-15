import java.awt.*;
import java.util.ArrayList;

public class Square {

    private boolean isMine, isRevealed;  // Is this a mine?  Is this revealed?
    private int numNeighborMines;  // how many mines are around this square
    private int r, c;  // what index this Square is in board
    private Square[][] board; // the entire board
    boolean isFlagged;

    public Square(boolean isMine, int r, int c, Square[][] board) {
        this.isMine = isMine;
        this.r = r;
        this.c = c;
        this.isRevealed = false;
        this.board = board;
        this.isFlagged = false;
        numNeighborMines = 0;
    }

    public void calcNeighborMines(int r, int c){
        numNeighborMines = 0;
        for(int i = 1; i >= -1; i--){
            for(int j = 1; j >= -1; j--){
                if(isInBounds(r+j,c+i))
                    if(board[r+j][c+i].isMine())
                        numNeighborMines++;
            }
        }
    }

    public void draw(Graphics2D g2){
        int size = MineSweeper.SIZE;
        if (isRevealed) {
            if(isMine) {
                g2.setColor(Color.RED);
            }else{
                g2.setColor(Color.BLACK);
            }
            g2.fillRect(c * size, r * size, size, size);
        }else{
            if(isFlagged){
                g2.setColor(Color.GREEN);
                g2.fillRect(c * size, r * size, size, size);
            }
            else {
                g2.setColor(Color.GRAY);
                g2.fillRect(c * size, r * size, size, size);
            }
        }
        g2.setColor(Color.BLACK);
        g2.drawRect(c * size, r * size, size, size);

        if(isRevealed && !isMine) {
            calcNeighborMines(r, c);
            g2.setColor(Color.WHITE);
            if(numNeighborMines != 0)
                g2.drawString("" + numNeighborMines, c * size + 10, r * size + 25);
        }
    }

    public boolean isInBounds(int row, int col){
        return row < board.length && row > -1 && col < board[0].length && col > -1;
    }

    public void click(){
        if(!isFlagged) {
            calcNeighborMines(r, c);
            revealCell();
        }
    }
    public boolean isMine() {
        return isMine;
    }

    public void removeMine(int r, int c) {
        board[r][c].isMine = false;
    }

    public void revealCell() {
        if(!board[r][c].isRevealed)
            board[r][c].isRevealed = true;
        if(board[r][c].getNumNeighborMines() == 0){
            for(int i = 1; i >= -1; i--){
                for(int j = 1; j >= -1; j--) {
                    if (isInBounds(r + j, c + i) && !board[r + j][c + i].isRevealed)
                        board[r + j][c + i].click();
                }
            }
        }
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getNumNeighborMines() {
        return numNeighborMines;
    }
}