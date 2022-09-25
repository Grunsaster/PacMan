
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author tgrun
 */
public class Main extends JFrame {
    public final int APP_HEIGHT = 370;
    public final int APP_WIDTH = 705;
    
    Main() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setResizable(false);
        setLayout(null);
        
        Container c = getContentPane();
        c.add(new Maze());
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * 
     * @param args: Argument från kommandotolken.
     */
    public static void main(String[] args) {
        new Main();
    }
}
