
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

/**
 *
 * Skapar och ritar en labyrint med data från txt-filer. Hanterar kollisioner, game-over-logik och input.
 * 
 * @author tgrun
 */
public class Maze extends JPanel implements ActionListener {
    public final int CELL = 15;
    public Cell[][] cells;
    public String map = "src/maps/level_1.txt";
    public int lives = 3;
    public int score = 3;
    public int gridHeight;
    public int gridLength;
    public Timer timer;
    public boolean running;
    
    public PacMan player;
    public Ghost blinky;
    public Ghost clyde;
    public Ghost inky;
    public Ghost pinky;
    
    Maze() {
        createCellArray(map);
        
        System.out.println(gridLength);
        
        setBackground(Color.BLACK);
        setBounds(0, 0, CELL*gridLength, CELL*gridHeight);
        setFocusable(true);
        
        running = true;
        timer = new Timer(20, this);
        timer.start();
    }
    
    /**
     * Läser och sparar data från en txt-fil vars karaktärer innebär en viss typ av cell (p -> pellet, h -> horisontell vägg, etc). 
     * 
     * @param mapFile: Väg till banans txt-fil.
     */
    public void createCellArray(String mapFile) {
        Scanner fileScanner;
        ArrayList<String> lineArray = new ArrayList<String>();
        
        // Fil kan bli korrupt/raderad vilket skulle resultera i error, behövs try-catch.
        try {
            fileScanner = new Scanner(new File(mapFile));
            
            while(true) {
                String line = null;
                
                try {
                    line = fileScanner.nextLine();
                } catch(Exception e) {
                    System.out.println("SISTA TEXTRADEN");
                }
               
                // Txt-fil slut.
                if(line == null)  {
                    break;
                }
                
                lineArray.add(line);
            }
        } catch(Exception e) {
            System.out.println("KUNDE EJ LADDA " + mapFile + ": " + e);
        }
        
        gridHeight = lineArray.size(); // Antal linjer (rader)
        gridLength = lineArray.get(0).length(); // Längd på linje (kolumner)
        
        cells = new Cell[gridHeight][gridLength];
        
        for(int row = 0; row < gridHeight; row++) {
            String line = lineArray.get(row);
            
            for(int col = 0; col < gridLength; col++) {
                char typeOfCell = line.charAt(col);
                cells[row][col] = new Cell(row, col, typeOfCell);
            }
        }
    }
    
    /**
     * Kollar ifall spelarens rad och kolumn motsvarar en av spökenas.
     */
    public void checkCollisions() {
        
    }
    
    /**
     * Itererar genom cells och målar korrekt bakgrundstyp. Målar spöken och spelare. 
     * 
     * @param g: Grafiska funktioner.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Ritar labyrintens celler
        for(int row = 0; row < gridHeight; row++) {
            for(int col = 0; col < gridLength; col++) {
                cells[row][col].drawCell(g);
            }
        }
        
        // player.drawPacMan()
        // blinky.drawGhost()
        // ...
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            
        }
        
        repaint();
    }
    
    /**
     * Minskar liv och hanterar game-over-logik.
     */
    public void loseLife() {
        lives--;
        
        // if lives < 0 then ...
    }
    
    public Cell[][] getCells() {
        return cells;
    }
    
    public int getLives() {
        return lives;
    }
    
    public int getScore() {
        return score;
    }
}
