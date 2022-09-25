
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
    
    public void drawCell(Graphics g) {
        int xBase;
        int yBase;
        
        switch(type) {
            case 'h': // horisontell vägg
                g.setColor(Color.BLUE);
                g.fillRect(x*CELL, y*CELL + CELL/2 - 1, CELL, 3);
                break;
            case 'v': // vertikal vägg
                g.setColor(Color.BLUE);
                g.fillRect(x*CELL + CELL/2 - 1, y*CELL, 3, CELL);
                break;
            case '1': // 1:a kvadranthörn 
                xBase = x*CELL - CELL/2;
                yBase = y*CELL + CELL/2;
                drawCorner(g, xBase, yBase);
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
        Rectangle oldClip = g.getClipBounds(); // Gamla ritområdet
        
        g2.setColor(Color.BLUE);
        g2.setClip(x*CELL, y*CELL, CELL, CELL); // Sätter cellen som ritområdet
        
        g2.setStroke(new BasicStroke(3));
        g2.draw(new Ellipse2D.Double(xb, yb, CELL, CELL)); // Ritar en cirkel som täcker
        g2.setClip(oldClip);
    }
}
