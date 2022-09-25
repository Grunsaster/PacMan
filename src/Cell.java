
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
                g.setColor(Color.YELLOW);
                g.fillOval(x*CELL + CELL/2 - 5, y*CELL + CELL/2 - 5, 10, 10);
            }
            case 'e' -> {
                // spökhem dörr
                g.setColor(Color.WHITE);
                g.fillRect(x*CELL, y*CELL + CELL/2, CELL, 3);
            }
        }
    }
    
    /**
     * För att göra runda hörn ritas en cirkel in i mitten av 4 celler, så att varje kvadrant är i sin egna cell,
     * sedan bestäms ritområdet så att
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
    
    public char getType() {
        return type;
    }
}
