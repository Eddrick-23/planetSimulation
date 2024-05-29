public class planet {
    private final double G = 6.6743e-11; // gravitational field constant
    private final double AU = 1.496e8; // in km
    private final double scale = 2000/AU; // 1AU = 20 pixels
    private final double mass;
    private final double x;
    private final double y;
    private final double r; // radius of planet
    private final boolean isStar; // true for the sun only

    public planet(double m, double initialX, double initialY, double radius, boolean star){
        mass = m;
        x = initialX;
        y = initialY;
        r = radius;
        isStar = star;
    }

    public void draw(){
        StdDraw.filledCircle(x,y, 100); // only do scaling at end since we use actual radius for calc.
    }
    public static void main(String[] args) {

        StdDraw.setCanvasSize(800,800);
        StdDraw.setScale(-400, 400); // so that centre is at 0,0
        planet earth = new planet(5.972 * 10e24, 0,0,6371, false );
        earth.draw();
        StdDraw.show();

    }
}
