
import java.awt.*;
import java.util.ArrayList;

/**
 * @author tgrun
 */
public class Ghost {
    public final int CELL = 15;
    public Cell[][] cells;
    public Color color;
    public String illegals;
    public int x;
    public int y;
    public Cell prevCell;
    public Cell pathCell;
    public ArrayList<Cell> cellSequence;
    public ArrayList<Cell> triedCells;
    public boolean isMoving = false;
    
    Ghost(Cell[][] nCells, int row, int col, Color nColor) {
        cells = nCells;
        x = col;
        y = row;
        color = nColor;
        illegals = "1234hvx";
    }
    
    public int getRandom(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    /**
     * Genererar slumpad cell och anropar findPath.
     */
    public void generatePath() {
        if(isMoving) {
            return;
        }
        
        isMoving = true;
        
        // Sortera celler till endast lagliga typer
        ArrayList<Cell> sorted = new ArrayList<>();
        for(int row = 0; row < cells.length; row++) {
            for(int col = 0; col < cells[0].length; col++) {
                if(illegals.indexOf(cells[row][col].getType()) == -1) {
                    sorted.add(cells[row][col]);
                }
            }
        }
        
        // Generera slumpad cell
        pathCell = sorted.get(getRandom(0, sorted.size()));
        
        // Generera väg till slumpad cell
        triedCells = new ArrayList<>();
        cellSequence = new ArrayList<>();
        findPath(y, x);
    }
    
    /**
     * Rekursiv funktion som finner kortast lagliga väg (liknar Dijkstras algoritm).
     * 
     * @param row: Y-axeln.
     * @param col: X-axeln.
     * @return boolean
     */
    public boolean findPath(int row, int col) {
        if(!isMoveLegal(row, col)) {
            return false;
        }
        
        // Väg hittad! (tidigare funktionsanrop kommer nu också returnera sant och därmed lägga till celler i cellSequence)
        if(pathCell.getRow() == row && pathCell.getCol() == col) {
            cellSequence.add(cells[row][col]);
            return true;
        } else {
            triedCells.add(cells[row][col]);
        }
        
        // Upp
        if(findPath(row+1, col)) {
            cellSequence.add(cells[row+1][col]);
            return true;
        }
        
        // Ned
        if(findPath(row-1, col)) {
            cellSequence.add(cells[row-1][col]);
            return true;
        }
        
        // Höger
        if(findPath(row, col+1)) {
            cellSequence.add(cells[row][col+1]);
            return true;
        }        
        
        // Vänster
        if(findPath(row, col-1)) {
            cellSequence.add(cells[row][col-1]);
            return true;
        }        
        
        return false;
    }
    
    /**
     * Går till senaste cellen i cellSequence.
     */
    public void move() {
        int c = cellSequence.size()-1;

        y = cellSequence.get(c).getRow();
        x = cellSequence.get(c).getCol();
        cellSequence.remove(c);

        if(cellSequence.isEmpty()) {
            isMoving = false;
        }
    }
    
    /**
     * Kollar ifall mötande cell är av laglig typ och returnerar resultatet.
     * 
     * @param row: Y-axel.
     * @param col: X-axel.
     * @return boolean
     */
    public boolean isMoveLegal(int row, int col) {
        Cell checkedCell = null;
        
        try { // Kan indexa utom rutnätet, behövs try-catch.
            checkedCell = cells[row][col];
        } catch(Exception e) {
            System.out.println("UTOM BANANS RUTNÄT");
        }
        
        if(checkedCell == null) {
            return false; // Cell finns inte.
        }
        
        if(illegals.indexOf(checkedCell.getType()) != -1) {
            return false; // Möter blockerad väg.
        }
        
        for(Cell c : triedCells) {
            if(checkedCell.getRow() == c.getRow() && checkedCell.getCol() == c.getCol()) {
                return false; // Prövad cell.
            }
        }
        
        return true;
    }
    
    /**
     * Målar spökkaraktären.
     * 
     * @param g: Grafiska funktioner
     * @param ticksBeforeDeadly: Antal spel-loopar innan spöken blir dödliga
     */
    public void drawGhost(Graphics g, int ticksBeforeDeadly) {
        g.setColor(color);
        
        if(ticksBeforeDeadly > 0) {
            g.setColor(Color.WHITE);
        }
        
        g.fillRect(x*CELL + CELL/2 - 7, y*CELL + CELL/2 - 7, 15, 15);
    }
    
    /**
     * Kollar ifall inputtad cell är i spökets cell eller i de fyra närmsta cellerna.
     * 
     * @param c: Cell som kollas
     * @return 
     */
    public boolean isInCell(Cell c) {
        // Kan indexa utanför cells storlek, behövs try-catch.
        try {
            return c == cells[y][x] || c == cells[y+1][x] || c == cells[y-1][x] || c == cells[y][x+1] || c == cells[y][x-1];
        } catch(Exception e) {
            return false;
        }
    }
    
    public int getCol() {
        return x;
    }
    
    public int getRow() {
        return y;
    }
}
