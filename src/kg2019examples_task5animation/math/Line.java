package kg2019examples_task5animation.math;


public class Line {
    private Point f, s;

    public Line(Point f, Point s) {
        this.f = f;
        this.s = s;
    }

    public Point getF() {
        return f;
    }

    public void setF(Point f) {
        this.f = f;
    }

    public Point getS() {
        return s;
    }

    public void setS(Point s) {
        this.s = s;
    }

    public boolean isConnectWithWalls(Point point) {
        double dx1 = s.getX() - f.getX();
        double dy1 = s.getY() - f.getY();

        double dx = point.getX() - f.getX();
        double dy = point.getY() - f.getY();

        return dx1 * dy - dx * dy1 == 0;
    }
}
