package kg2019examples_task5animation.model;

import kg2019examples_task5animation.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Ball {
    private double r, m;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private List<Vector2> pointList;

    public Ball(double m, double r, Vector2 position) {
        this.m = m;
        this.r = r;
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        createBall(position, r);
    }

    private void createBall(Vector2 position, double r) {
        List<Vector2> pointList = new ArrayList<>();
        for(double i = 22.5; i < 360; i += 45) {
            double x = Math.sin(i) * r;
            double y = Math.cos(i) * r;
            pointList.add(new Vector2(x, y));
        }
        this.pointList = pointList;
    }

    public void setPointList(List<Vector2> pointList) {
        this.pointList = pointList;
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

    public List<Vector2> getPointList() {
        return pointList;
    }
}
