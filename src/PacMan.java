
import java.awt.*;

/**
 *
 * @author tgrun
 */
public class PacMan {
    public final int CELL = 15;
    public String illegals;
    public int x;
    public int y;
    public char currDir;
    public char nextDir;
    public Cell[][] cells;
    
    PacMan(Cell[][] nCells, int row, int col, char dir) {
        cells = nCells;
        x = col;
        y = row;
        currDir = dir;
        illegals = "1234hvxeg";
    }
    
    /**
     * Kollar ifall nästa riktning är laglig, hanterar teleportörlogik och ökar/minskar x eller y.
     */
    public void move() {
        if(isMoveLegal(nextDir)) {
            currDir = nextDir;
        }
        
        switch(cells[y][x].getType()) {
            case 't' -> {
                Cell c = findOtherTeleport('q');
                x = c.getCol();
                y = c.getRow();
            }
            case 'q' -> {
                Cell c = findOtherTeleport('t');
                x = c.getCol();
                y = c.getRow();
            }
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
     * Kollar ifall mötande cell är av laglig typ och returnerar resultatet.
     * 
     * @param dir: Karaktär som representerar riktning (u/d/r/l).
     * @return boolean
     */
    public boolean isMoveLegal(char dir) {
        Cell checkedCell = null;
        
        // Hamnar gubben x, y kordinater utanför rutnätet inträffar index-error, behövs try-catch.
        try {
            switch(dir) {
                case 'u' -> checkedCell = cells[y-1][x];
                case 'd' -> checkedCell = cells[y+1][x];
                case 'r' -> checkedCell = cells[y][x+1];
                case 'l' -> checkedCell = cells[y][x-1];
            }
        } catch(Exception e) {
            System.out.println("UTOM BANANS RUTNÄT");
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
        switch(dir) {
            case 'u' -> nextDir = 'u';
            case 'd' -> nextDir = 'd';
            case 'r' -> nextDir = 'r';
            case 'l' -> nextDir = 'l';
        }
    }
    
    /**
     * Returnerar teleportör-cell.
     * 
     * @param tele: Karaktär som representerar teleportör (t/q)
     * @return 
     */
    public Cell findOtherTeleport(char tele) {
        Cell c = null;
        
        for(int row = 0; row < cells.length; row++) {
            for(int col = 0; col < cells[0].length; col++) {
                if(cells[row][col].getType() == tele) {
                    c = cells[row][col];
                }
            }
        }
            
        return c;
    }
    
    /**
     * Målar PacMan karaktären.
     * 
     * @param g: Grafiska funktioner
     */
    public void drawPacMan(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x*CELL + CELL/2 - 9, y*CELL + CELL/2 - 9, 18, 18);
    }
    
    public int getCol() {
        return x;
    }
    
    public int getRow() {
        return y;
    }
}
