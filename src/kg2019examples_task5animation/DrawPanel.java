/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task5animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import kg2019examples_task5animation.timers.AbstractWorldTimer;
import javax.swing.JPanel;
import javax.swing.Timer;
import kg2019examples_task5animation.math.Rectangle;
import kg2019examples_task5animation.math.Vector2;
import kg2019examples_task5animation.model.Field;
import kg2019examples_task5animation.model.Puck;
import kg2019examples_task5animation.model.World;
import kg2019examples_task5animation.timers.UpdateWorldTimer;
import kg2019examples_task5animation.utils2d.ScreenConverter;

/**
 *
 * @author Alexey
 */
public class DrawPanel extends JPanel implements ActionListener {
    private ScreenConverter sc;
    private World w;
    private AbstractWorldTimer uwt;
    private Timer drawTimer;
    
    public DrawPanel() {
        super();
        Field f = new Field(
                new Rectangle(0, 10, 10, 10),
            0.1, 9.8);
        w = new World(new Puck(1, 0.3, f.getRectangle().getCenter()), f);
        sc = new ScreenConverter(f.getRectangle(), 450, 450);
        
        (uwt = new UpdateWorldTimer(w, 10)).start();
        drawTimer = new Timer(40, this);
        drawTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        w.draw((Graphics2D)bi.getGraphics(), sc);
        g.drawImage(bi, 0, 0, null);
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
}
