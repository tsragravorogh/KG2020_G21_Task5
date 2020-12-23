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
 * Класс, описывающий весь мир, в целом.
 *
 * @author Alexey
 */
public class World {
    private Ball b;
    private Field f;
    private ForceSource externalForce;

    public World(Ball p, Field f) {
        this.b = p;
        this.f = f;
        this.externalForce = new ForceSource(f.getRectangle().getCenter());
    }


    public void updateNew(double dt) {
        Vector2 np = b.getPosition();
                /*.add(p.getVelocity().mul(dt))
                .add(p.getAcceleration().mul(-f.getG() * dt*dt*0.5));*/
        if (b.getTypeOfMove() == Ball.TypeOfMove.movement) {
            double dx = b.getVelocity().getX() * dt;
            double dy = b.getVelocity().getY() * dt - f.getG() * dt * dt * 0.5;
            Walls border = behindTheWall(np, dx, dy);

            if (border == Walls.INSIDE) {
                np.setX(np.getX() + dx);
                np.setY(np.getY() + dy);
            } else {
                shiftToBorder(np, dx, dy, border);
                b.setTypeOfMove(Ball.TypeOfMove.compression);
                b.setWall(border);
            }
            b.getVelocity().setY(b.getVelocity().getY() - f.getG() * dt);
            Vector2 nv = b.getVelocity()
                    .add(b.getAcceleration().mul(dt));
            Vector2 newV = changeVelocity(nv, border);
            if (b.getTypeOfMove() == Ball.TypeOfMove.compression) {
                double dCompr;
                if (border == Walls.DOWN || border == Walls.UP) {
                    dCompr = Math.sqrt(b.getM() / b.getK()) * Math.abs(nv.getY());
                    b.setMinYR(b.getYr() - dCompr);
                    b.setMinXR(b.getXr()) ;
                    //System.out.println(dCompr);
                } else if (border == Walls.LEFT || border == Walls.RIGHT) {
                    dCompr = Math.sqrt(b.getM() / b.getK()) * Math.abs(nv.getX());
                    b.setMinXR(b.getXr() - dCompr);
                    b.setMinYR(b.getYr());
                    //System.out.println(dCompr);
                }
            }
            Vector2 Fvn = externalForce.getForceAt(np);
            Vector2 Ftr = b.getVelocity().normolized().mul(-f.getMu() * b.getM() * f.getG());
            Vector2 F = Ftr.add(Fvn);

            b.setAcceleration(F.mul(1 / b.getM()));
            b.setVelocity(newV);
            b.setPosition(np);
        } else if (b.getTypeOfMove() == Ball.TypeOfMove.compression) {
            double d;
            Vector2 newP;
            if (b.getWall() == Walls.UP || b.getWall() == Walls.DOWN) {
                if (b.getMinYR() < b.getYr()) {
                    d = dt * Math.abs(b.getVelocity().getY() / Math.PI);
                    System.out.println(d);
                    if (b.getWall() == Walls.UP) {
                        newP = new Vector2(b.getPosition().getX(), b.getPosition().getY() + d);
                    } else {
                        newP = new Vector2(b.getPosition().getX(), b.getPosition().getY() - d);
                    }
                    b.setPosition(newP);
                    b.setXr(b.getXr() + d);
                    b.setYr(b.getYr() - d);
                } else b.setTypeOfMove(Ball.TypeOfMove.decompression);
            } else {
                d = dt * Math.abs(b.getVelocity().getX() / Math.PI);
                if (b.getMinXR() < b.getXr()) {
                    if (b.getWall() == Walls.LEFT) {
                        newP = new Vector2(b.getPosition().getX() - d, b.getPosition().getY());
                    } else {
                        newP = new Vector2(b.getPosition().getX() + d, b.getPosition().getY());
                    }
                    b.setPosition(newP);
                    b.setXr(b.getXr() - d);
                    b.setYr(b.getYr() + d);
                } else b.setTypeOfMove(Ball.TypeOfMove.decompression);
            }
            System.out.println(b.getPosition().getX() + " " + b.getPosition().getY());
        } else if (b.getTypeOfMove() == Ball.TypeOfMove.decompression) {
            double d;
            Vector2 newP;
            if (b.getWall() == Walls.UP || b.getWall() == Walls.DOWN) {
                if (b.getYr() < b.getNormalRadius()) {
                    d = dt * Math.abs(b.getVelocity().getY() / Math.PI);
                    System.out.println(d);
                    if (b.getWall() == Walls.UP) {
                        newP = new Vector2(b.getPosition().getX(), b.getPosition().getY() - d);
                    } else {
                        newP = new Vector2(b.getPosition().getX(), b.getPosition().getY() + d);
                    }
                    b.setPosition(newP);
                    b.setXr(b.getXr() - d);
                    b.setYr(b.getYr() + d);
                } else b.setTypeOfMove(Ball.TypeOfMove.movement);
            } else {
                d = dt * Math.abs(b.getVelocity().getX() / Math.PI);
                if (b.getXr() < b.getNormalRadius()) {
                    if (b.getWall() == Walls.LEFT) {
                        newP = new Vector2(b.getPosition().getX() + d, b.getPosition().getY());
                    } else {
                        newP = new Vector2(b.getPosition().getX() - d, b.getPosition().getY());
                    }
                    b.setPosition(newP);
                    b.setXr(b.getXr() + d);
                    b.setYr(b.getYr() - d);
                } else b.setTypeOfMove(Ball.TypeOfMove.movement);
            }
        }
    }

    private Walls behindTheWall(Vector2 np, double dx, double dy){
        Walls b;
        if (np.getX() + dx - this.b.getXr() < f.getRectangle().getLeft()){
            b = Walls.LEFT;
        }
        else if (np.getX() + dx + this.b.getXr() > f.getRectangle().getRight()){
            b = Walls.RIGHT;
        }
        else if (np.getY() + dy - this.b.getYr() < f.getRectangle().getBottom()){
            b = Walls.DOWN;
        }
        else if (np.getY() + dy + this.b.getYr() > f.getRectangle().getTop()){
            b = Walls.UP;
        } else {
            b = Walls.INSIDE;
        }
        return b;
    }

    private void shiftToBorder(Vector2 np, double dx, double dy, Walls wall){
        double newX = np.getX() + dx;
        double newY = np.getY() + dy;
        switch (wall){
            case LEFT ->
                    newX = f.getRectangle().getLeft() + b.getXr();
            case RIGHT ->
                    newX = f.getRectangle().getRight() - b.getXr();
            case DOWN ->
                    newY = f.getRectangle().getBottom() + b.getYr();
            case UP ->
                    newY = f.getRectangle().getTop() - b.getYr();
        }
        np.setY(newY);
        np.setX(newX);
    }
    private Vector2 changeVelocity(Vector2 nv, Walls wall){
        Vector2 changedV = new Vector2(nv.getX(), nv.getY());
        if (wall == Walls.UP || wall == Walls.DOWN){
            //System.out.println("UP <-> DOWN");
            changedV.setY(-nv.getY());
        }
        if (wall == Walls.LEFT || wall == Walls.RIGHT){
            //System.out.println("LEFT <-> RIGHT");
            changedV.setX(-nv.getX());
        }
        if (changedV.length() < 1e-5)
            changedV = new Vector2(0, 0);
        return changedV;
    }

        /**
         * Метод обновления состояния мира за указанное время
         *
         * @param dt Промежуток времени, за который требуется обновить мир.
         */
//        public void update ( double dt){
//            Vector2 np = p.getPosition()
//                    .add(p.getVelocity().mul(dt))
//                    .add(p.getAcceleration().mul(dt * dt * 0.5));
//
//            Vector2 nv = p.getVelocity()
//                    .add(p.getAcceleration().mul(dt));
//
//            double vx = nv.getX(), vy = nv.getY();
//            boolean reset = false;
//            boolean change = false;
//
//            if (np.getX() - p.getR() < f.getRectangle().getLeft()) {
//                change = true;
//                double dx = p.getPosition().getX() - f.getRectangle().getLeft();
//                double tg = nv.getY() / nv.getX();
//                double dy = dx / Math.atan(tg);
//                Vector2 wallPosition = new Vector2(dx, dy);
//                np
//            }
//
//            if (np.getX() - p.getR() < f.getRectangle().getLeft() || np.getX() + p.getR() > f.getRectangle().getRight()) {
//                vx = -vx;
//                reset = true;
//            }
//            if (np.getY() - p.getR() < f.getRectangle().getBottom() || np.getY() + p.getR() > f.getRectangle().getTop()) {
//                vy = -vy;
//                reset = true;
//            }
//            nv = new Vector2(vx, vy);
//            if (nv.length() < 1e-10)
//                nv = new Vector2(0, 0);
//            if (reset)
//                np = p.getPosition();
//
//            Vector2 Fvn = externalForce.getForceAt(np);
//            Vector2 Ftr = p.getVelocity().normolized().mul(-f.getMu() * p.getM() * f.getG());
//            Vector2 F = Ftr.add(Fvn);
//
//            p.setAcceleration(F.mul(1 / p.getM()));
//            p.setVelocity(nv);
//            p.setPosition(np);
//        }

        /**
         * Метод рисует ткущее состояние мира.
         * На самом деле всю логику рисования стоит вынести из этого класса
         * куда-нибудь в WroldDrawer, унаследованный от IDrawer
         *
         * @param g  Графикс, на котором надо нарисовать текущее состояние.
         * @param sc Актуальный конвертер координат.
         */
        public void draw (Graphics2D g, ScreenConverter sc){
//            ScreenPoint tl = sc.r2s(f.getRectangle().getTopLeft());
//            int w = sc.r2sDistanceH(f.getRectangle().getWidth());
//            int h = sc.r2sDistanceV(f.getRectangle().getHeight());
//            g.setColor(Color.WHITE);
//            g.fillRect(tl.getI(), tl.getJ(), w, h);
//            g.setColor(Color.RED);
//            g.drawRect(tl.getI(), tl.getJ(), w, h);
//
//            ScreenPoint pc = sc.r2s(b.getPosition());
//            int rh = sc.r2sDistanceH(b.getR());
//            int rv = sc.r2sDistanceV(b.getR());
//            g.setColor(Color.BLACK);
//            g.fillOval(pc.getI() - rh, pc.getJ() - rv, rh + rh, rv + rv);
//
//            g.drawString(String.format("Mu=%.2f", f.getMu()), 10, 30);
//            g.drawString(String.format("F=%.0f", externalForce.getValue()), 10, 50);

            ScreenPoint tl = sc.r2s(f.getRectangle().getTopLeft());
            int w = sc.r2sDistanceH(f.getRectangle().getWidth());
            int h = sc.r2sDistanceV(f.getRectangle().getHeight());
            g.setColor(Color.WHITE);
            g.fillRect(tl.getI(), tl.getJ(), w, h);
            g.setColor(Color.RED);
            g.drawRect(tl.getI(), tl.getJ(), w, h);
            ScreenPoint pc = sc.r2s(b.getPosition());
            int rh = sc.r2sDistanceH(b.getXr());
            int rv = sc.r2sDistanceV(b.getYr());
            g.setColor(Color.BLACK);
            g.fillOval(pc.getI() - rh, pc.getJ() - rv, rh + rh, rv + rv);

            g.drawString(String.format("Mu=%.2f", f.getMu()), 10, 30);
            g.drawString(String.format("F=%.0f", externalForce.getValue()), 10, 50);
        }

        public Field getF () {
            return f;
        }

        public void setF (Field f){
            this.f = f;
        }

        public Ball getB() {
            return b;
        }

        public void setB(Ball b){
            this.b = b;
        }

        public ForceSource getExternalForce () {
            return externalForce;
        }
    }
