/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task5animation.model;

import kg2019examples_task5animation.math.Vector2;

/**
 *
 * Класс, описывающий шайбу.
 * @author Alexey
 */
public class Ball {
    private double m, r, k;
    private double Xr;
    private double Yr;
    private double minXR;
    private double minYR;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private TypeOfMove typeOfMove;
    private Walls wall;
    private final double normalRadius;

    /**
     * Создаём шайбу с нулевой скоростью и ускорением
     * @param m Масса шайбы [кг]
     * @param r Радиус шайбы [м]
     * @param position Положение шайбы относительно начала координат [м]
     */
    public Ball(double m, double r, double k,  Vector2 position) {
        this.m = m;
        this.Xr = r;
        this.Yr = r;
        this.k = k;
        this.wall = Walls.INSIDE;
        this.normalRadius = r;
        this.typeOfMove =  TypeOfMove.movement;
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
    }

    public enum TypeOfMove {
        compression, decompression, movement
    }

    public TypeOfMove getTypeOfMove() {
        return typeOfMove;
    }

    public void setTypeOfMove(TypeOfMove typeOfMove) {
        this.typeOfMove = typeOfMove;
    }

    public Walls getWall() {
        return wall;
    }

    public void setWall(Walls wall) {
        this.wall = wall;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getXr() {
        return Xr;
    }

    public void setXr(double xr) {
        Xr = xr;
    }

    public double getYr() {
        return Yr;
    }

    public void setYr(double yr) {
        Yr = yr;
    }

    public double getMinYR() {
        return minYR;
    }

    public void setMinYR(double minYR) {
        this.minYR = minYR;
    }

    public double getMinXR() {
        return minXR;
    }

    public void setMinXR(double minXR) {
        this.minXR = minXR;
    }

    public double getNormalRadius() {
        return normalRadius;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    
    
}
