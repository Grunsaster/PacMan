
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author tgrun
 */
public class Main extends JFrame {
    Main() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(705, 420));
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
