
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

/**
 * 
 * @author tgrun
 */
public class Maze extends JPanel implements ActionListener {
    public final int CELL = 15;
    public int level = 1;
    public int score = 0;
    public String map;
    
    public Cell[][] cells;
    public int gridHeight;
    public int gridLength;
    public Timer timer;
    public boolean running;
    
    public PacMan player;
    public Ghost blinky;
    public Ghost clyde;
    public Ghost inky;
    public Ghost pinky;
    public int ticksBeforeDeadly;
    
    Maze() {
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyLs());
        
        timer = new Timer(150, this);
        timer.start();
        
        newGame();
    }
    
    /**
     * Startar om spelet och skapar nya spök- och spelarobjekt.
     */
    public void newGame() {
        if(running) {
            return;
        }
        
        running = true;
        map = "src/maps/level_" + level + ".txt";
        
        createCellArray(map);
        setBounds(0, 0, CELL*gridLength, CELL*gridHeight + 60);
        
        player = new PacMan(cells, 13, 20, 'l');
        blinky = new Ghost(cells, 9, 22, Color.GREEN);
        clyde = new Ghost(cells, 9, 22, Color.CYAN);
        inky = new Ghost(cells, 9, 22, Color.RED);
        pinky = new Ghost(cells, 9, 22, Color.PINK);
        
        playSound("src/sounds/musik.wav");
    }
    
    /**
     * Använder AudioSystem för att ladda in wav-filer och sedan spela upp dem.
     * 
     * @param fileName: Filväg till ljudfil
     */
    public void playSound(String fileName) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            
            Clip soundClip = (Clip) AudioSystem.getLine(info);
            soundClip.open(stream);
            soundClip.start();
            
        } catch(Exception e) {
            System.out.println("KUNDE EJ LADDA " + fileName + ": " + e);
        }
    }
    
    /**
     * Läser och sparar data från en txt-fil vars karaktärer innebär en viss typ av cell (d -> pellet, h -> horisontell vägg, etc). 
     * 
     * @param mapFile: Väg till banans txt-fil.
     */
    public void createCellArray(String mapFile) {
        Scanner fileScanner;
        ArrayList<String> lineArray = new ArrayList<String>(); // ArrayList används då array har fixerad max-index.
        
        try { // Fil kan bli korrupt/raderad/inte finnas vilket skulle resultera i error, behövs try-catch.
            fileScanner = new Scanner(new File(mapFile));
            
            while(true) {
                String line = null;
                
                try { // När skannern når filslutet returneras ett error, behövs try-catch.
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
     * Kollar ifall spelaren äter pellets och ifall spelares cell motsvarar en av spökenas.
     */
    public void checkCollisions() {
        Cell currCell = cells[player.getRow()][player.getCol()];
        
        // Pellets.
        if(currCell.getType() == 'd') {
            playSound("src/sounds/nomnom.wav");
            currCell.changeType('o');
            score = score+10;
        }
        
        // Super-pellets.
        if(currCell.getType() == 'p') {
            playSound("src/sounds/superpellet.wav");
            currCell.changeType('o');
            ticksBeforeDeadly = 40;
            
            score = score+50;
        }
        
        // Spöken
        if(blinky.isInCell(currCell)) {
            if(ticksBeforeDeadly > 0) {
                playSound("src/sounds/rekt.wav");
                playSound("src/sounds/outtahere.wav");
                score = score+300;
                
                blinky = new Ghost(cells, 9, 22, Color.GREEN);
            } else {
                playSound("src/sounds/död.wav");
                score = 0;
                running = false;
            }
        }
        if(clyde.isInCell(currCell)) {
            if(ticksBeforeDeadly > 0) {
                playSound("src/sounds/rekt.wav");
                playSound("src/sounds/outtahere.wav");
                score = score+300;
                
                clyde = new Ghost(cells, 9, 22, Color.CYAN);
            } else {
                playSound("src/sounds/död.wav");
                score = 0;
                running = false;
            }
        }
        if(inky.isInCell(currCell)) {
            if(ticksBeforeDeadly > 0) {
                playSound("src/sounds/rekt.wav");
                playSound("src/sounds/outtahere.wav");
                score = score+300;
                
                inky = new Ghost(cells, 9, 22, Color.RED);
            } else {
                playSound("src/sounds/död.wav");
                score = 0;
                running = false;
            }
        }
        if(pinky.isInCell(currCell)) {
            if(ticksBeforeDeadly > 0) {
                playSound("src/sounds/rekt.wav");
                playSound("src/sounds/outtahere.wav");
                score = score+300;
                
                pinky = new Ghost(cells, 9, 22, Color.PINK);
            } else {
                playSound("src/sounds/död.wav");
                score = 0;
                running = false;
            }
        }
    }
    
    /**
     * Kollar ifall alla pellets är uppätna.
     */
    public void checkWinningConditions() {
        for(int row = 0; row < gridHeight; row++) {
            for(int col = 0; col < gridLength; col++) {
                if(cells[row][col].getType() == 'd' || cells[row][col].getType() == 'p') {
                    return;
                }
            }
        }
        
        playSound("src/sounds/superstjärna.wav");
        running = false;
        level++;
        newGame();
    }
    
    /**
     * Itererar genom celler och målar korrekt bakgrundstyp, målar spöken och spelare och målar poäng-tavla. 
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
        
        // Ritar spelare/spöken.
        player.drawPacMan(g);
        blinky.drawGhost(g, ticksBeforeDeadly);
        clyde.drawGhost(g, ticksBeforeDeadly);
        inky.drawGhost(g, ticksBeforeDeadly);
        pinky.drawGhost(g, ticksBeforeDeadly);
        
        // Poäng-tavla
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        
        g.drawString("LEVEL: " + level, 20, CELL*gridHeight + 5);
        g.drawString(""+score, 20, CELL*gridHeight + 25);
    }
    
    /**
     * Primära spel-loopen, körs av timern.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            checkCollisions();
            checkWinningConditions();
            
            blinky.generatePath();
            //clyde.generatePath();
            //inky.generatePath();
            //pinky.generatePath();
            
            blinky.move();
            //clyde.move();
            //inky.move();
            //pinky.move();
            //player.move();
            
            ticksBeforeDeadly--;
        }
        
        repaint();
    }
    
    /**
     * Generell tangentlyssnar klass. Lyssnar på keyPressed.
     */
    class KeyLs extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_W -> player.changeDir('u');
                case KeyEvent.VK_S -> player.changeDir('d');
                case KeyEvent.VK_D -> player.changeDir('r');
                case KeyEvent.VK_A -> player.changeDir('l');
                case KeyEvent.VK_UP -> player.changeDir('u');
                case KeyEvent.VK_DOWN -> player.changeDir('d');
                case KeyEvent.VK_RIGHT -> player.changeDir('r');
                case KeyEvent.VK_LEFT -> player.changeDir('l');
                case KeyEvent.VK_SPACE -> newGame();
            }
        }     
    }
}
