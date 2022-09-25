
import java.awt.*;

/**
 *
 * @author tgrun
 */
public class PacMan {
    public final int CELL = 15;
    public int x;
    public int y;
    public char currDir;
    public char nextDir;
    public Cell[][] cells;
    
    PacMan(Cell[][] sCells, int row, int col, char dir) {
        cells = sCells;
        x = col;
        y = row;
        currDir = dir;
    }
    
    /**
     * Kollar ifall mötande cell är av laglig typ och returnerar resultatet.
     * 
     * @param dir: Karaktär som representerar riktning (u/d/r/l).
     * @return boolean
     */
    public boolean isMoveLegal(char dir) {
        String illegals = "1234hvxeg";
        Cell checkedCell = null;
        
        switch(dir) {
            case 'u' -> checkedCell = cells[y-1][x];
            case 'd' -> checkedCell = cells[y+1][x];
            case 'r' -> checkedCell = cells[y][x+1];
            case 'l' -> checkedCell = cells[y][x-1];
        }
        
        if(checkedCell == null) {
            return false; // Cell finns inte.
        }
        
        if(illegals.indexOf(checkedCell.getType()) != -1) {
            return false; // Möter blockerad väg.
        }
        
        return true;
    }

    /**
     * Ändrar nästa riktning.
     * 
     * @param dir: Karaktär som representerar riktning (u/d/r/l). 
     */
    public void changeDir(char dir) {
        System.out.println("SHEESH");
        
        switch(dir) {
            case 'u' -> nextDir = 'u';
            case 'd' -> nextDir = 'd';
            case 'r' -> nextDir = 'r';
            case 'l' -> nextDir = 'l';
        }
    }
    
    /**
     * Kollar ifall nästa riktning är laglig och ökar/minskar x eller y.
     */
    public void move() {
        if(isMoveLegal(nextDir)) {
            currDir = nextDir;
        }
        
        if(isMoveLegal(currDir)) {
            switch(currDir) {
                case 'u' -> y--;
                case 'd' -> y++;
                case 'r' -> x++;
                case 'l' -> x--;
            }
        }
    }
    
    /**
     * Målar PacMan karaktären.
     * 
     * @param g: Grafiska funktioner
     */
    public void drawPacMan(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x*CELL + CELL/2 - 7, y*CELL + CELL/2 - 7, 15, 15);
    }
    
    public int getRow() {
        return x;
    }
    
    public int getCol() {
        return y;
    }
}
