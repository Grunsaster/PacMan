
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author tgrun
 */
public class Cell {
    public final int CELL = 15;
    public char type;
    public int x;
    public int y;
    
    Cell(int row, int col, char typeOfCell) {
        x = col;
        y = row; 
        type = typeOfCell;
    }
    
    /**
     * Ritar cellen beroende på cell-typ.
     * 
     * @param g: Grafiska funktioner
     */
    public void drawCell(Graphics g) {
        int xBase;
        int yBase;
        
        switch(type) {
            case '1' -> {
                // 1:a kvadranthörn
                xBase = x*CELL - CELL/2;
                yBase = y*CELL + CELL/2;
                drawCorner(g, xBase, yBase);
            }
            case '2' -> {
                // 2:a kvadranthörn
                xBase = x*CELL + CELL/2;
                yBase = y*CELL + CELL/2;
                drawCorner(g, xBase, yBase);
            }
            case '3' -> {
                // 3:e kvadranthörn
                xBase = x*CELL - CELL/2;
                yBase = y*CELL - CELL/2;
                drawCorner(g, xBase, yBase);
            }
            case '4' -> {
                // 4:e kvadranthörn
                xBase = x*CELL + CELL/2;
                yBase = y*CELL - CELL/2;
                drawCorner(g, xBase, yBase);
            }
            case 'h' -> {
                // horisontell vägg
                g.setColor(Color.BLUE);
                g.fillRect(x*CELL, y*CELL + CELL/2 - 1, CELL, 3);
            }
            case 'v' -> {
                // vertikal vägg
                g.setColor(Color.BLUE);
                g.fillRect(x*CELL + CELL/2 - 1, y*CELL, 3, CELL);
            }
            case 'd' -> {
                // cell med pellet
                g.setColor(Color.WHITE);
                g.fillOval(x*CELL + CELL/2 - 1, y*CELL + CELL/2 - 1, 3, 3);
            }
            case 'p' -> {
                // cell med super-pellet
                g.setColor(Color.ORANGE);
                g.fillOval(x*CELL + CELL/2 - 5, y*CELL + CELL/2 - 5, 10, 10);
            }
            case 'e' -> {
                // spökhem dörr
                g.setColor(Color.WHITE);
                g.fillRect(x*CELL, y*CELL + CELL/2, CELL, 3);
            }
            case 't' -> {
                // teleportör 1
                g.setColor(Color.WHITE);
                g.fillRect(x*CELL + CELL/2 - 1, y*CELL, 3, CELL);
            }
            case 'q' -> {
                // teleportör 2
                g.setColor(Color.WHITE);
                g.fillRect(x*CELL + CELL/2 - 1, y*CELL, 3, CELL);
            }
            case 'A' -> {
                // teleportör 2
                g.setColor(Color.CYAN);
                g.fillRect(x*CELL + CELL/2 - 2, y*CELL + CELL/2 - 2, 5, 5);
            }
        }
    }
    
    /**
     * Målar rundade hörn genom att använda cirklar och att manipulera ritområdet.
     * 
     * @param g: Grafiska funktioner
     * @param xb: Där cirkelns x-position börjar
     * @param yb: Där cirkelns y-position börjar
     */
    public void drawCorner(Graphics g, int xb, int yb) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle oldClip = g.getClipBounds(); // Gamla ritområdet.
        
        g2.setColor(Color.BLUE);
        g2.setClip(x*CELL, y*CELL, CELL, CELL); // Sätter cellen som ritområdet.
        
        g2.setStroke(new BasicStroke(3));
        g2.draw(new Ellipse2D.Double(xb, yb, CELL, CELL)); // Ritar en cirkel som är i mitten av cellen och dess 3 grannar, men eftersom ritområdet kommer bara ett hörn visas.
        g2.setClip(oldClip); // Återgår till vanliga ritområdet.
    }
    
    /**
     * Ändrar celltyp, exempelvis efter pellets blir uppätna.
     * 
     * @param nType: Ny celltyp
     */
    public void changeType(char nType) {
        type = nType;
    }
    
    public char getType() {
        return type;
    }
    
    public int getCol() {
        return x;
    }
    
    public int getRow() {
        return y;
    }
}
