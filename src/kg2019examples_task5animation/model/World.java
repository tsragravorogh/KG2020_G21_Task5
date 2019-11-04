/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task5animation.model;

import java.awt.Color;
import java.awt.Graphics2D;
import kg2019examples_task5animation.math.Vector2;
import kg2019examples_task5animation.utils2d.ScreenConverter;
import kg2019examples_task5animation.utils2d.ScreenPoint;

/**
 *
 * Класс, описывающий весь мир, в целом.
 * @author Alexey
 */
public class World {
    private Puck p;
    private Field f;

    public World(Puck p, Field f) {
        this.p = p;
        this.f = f;
    }
    
    /**
     * Метод обновления состояния мира за указанное время
     * @param dt Промежуток времени, за который требуется обновить мир.
     */
    public void update(double dt) {
        
    }
    
    /**
     * Метод рисует ткущее состояние мира.
     * На самом деле всю логику рисования стоит вынести из этого класса
     * куда-нибудь в WroldDrawer, унаследованный от IDrawer
     * @param g Графикс, на котором надо нарисовать текущее состояние.
     * @param sc Актуальный конвертер координат.
     */
    public void draw(Graphics2D g, ScreenConverter sc) {
        ScreenPoint tl = sc.r2s(f.getRectangle().getTopLeft());
        int w = sc.r2sDistanceH(f.getRectangle().getWidth());
        int h = sc.r2sDistanceV(f.getRectangle().getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(tl.getI(), tl.getJ(), w, h);
        g.setColor(Color.BLACK);
        g.drawRect(tl.getI(), tl.getJ(), w, h);
        ScreenPoint pc = sc.r2s(p.getPosition());
        int rh = sc.r2sDistanceH(p.getR());
        int rv = sc.r2sDistanceV(p.getR());
        g.fillOval(pc.getI() - rh, pc.getJ() - rv, rh + rh, rv + rv);
    }

    public Field getF() {
        return f;
    }

    public void setF(Field f) {
        this.f = f;
    }

    public Puck getP() {
        return p;
    }

    public void setP(Puck p) {
        this.p = p;
    }
    
    
}
