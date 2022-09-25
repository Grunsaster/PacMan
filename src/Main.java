
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author tgrun
 */
public class Main extends JFrame {
    public final int APP_HEIGHT = 800;
    public final int APP_WIDTH = 700;
    
    Main() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setResizable(false);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        Container c = getContentPane();
        c.add(new Maze());
    }
    
    /**
     * 
     * @param args: Argument från kommandotolken.
     */
    public static void main(String[] args) {
        new Main();
    }
}
