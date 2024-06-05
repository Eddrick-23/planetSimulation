import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class planet {
    private final Font font = new Font("Arial", Font.BOLD,10);
    public final String name;
    private final double timeStep = 86400/2; // 1 day
    private final double G = 6.6743e-11; // gravitational field constant
    private static final double AU = 1.496e8 * 1000; // in m
    public final double scale = 100/AU; // 1AU = 10 pixel, scale for coordinates
    private final double mass;
    private double x; // in m
    private double y; // in m
    private double vX; // horizontal velocity
    private double vY; // vertical velocity
    private final double r; // radius of planet
    private final double scaledRadius;
    private final boolean isStar; // true for the sun only
    private final Color color;

    public planet(String n,double m, double initialX, double initialY, double velocityX, double velocityY, double radius, boolean star, Color c){
        name = n;
        mass = m;
        x = initialX; // set initial positions
        y = initialY;
        vX = velocityX; // set initial vertical and horizontal velocities
        vY = velocityY;
        r = radius;
        scaledRadius = Math.log(r); // pre compute scaled radius
        isStar = star;
        color = c;
    }

    public void move(){ // move the planet based on its velocities
        x = x + vX*timeStep;
        y = y + vY*timeStep;
    }

    private double getDistFromSun(){
        double distFromSun = Math.sqrt(x*x + y*y)/10e3;
        BigDecimal bd = new BigDecimal(distFromSun);
        bd = bd.round(new MathContext(4)); // round to 5s.f.
        return bd.doubleValue();
    }

    public void draw(){
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x*scale,y*scale, scaledRadius); // only do scaling at end since we use actual radius for calc.
        StdDraw.setPenColor();
        StdDraw.setFont(font);
        StdDraw.text(x*scale,y*scale+scaledRadius,name); // display the name above the planet
        if (!isStar) {
            StdDraw.line(x*scale, y*scale, 0, 0); // draw line connecting planet and sun
            StdDraw.text(x*scale, y*scale-scaledRadius, getDistFromSun()+"km");
        }
    }

    private void updateVelocity(double aX, double aY, double timeStep){
        vX += aX*timeStep; // update the velocities based on time step of 1 day
        vY += aY*timeStep;
    }

    public void accelerate(planet other){ //accelerates the bodies due to gravity

        if (!this.equals(other)) { // only update if planets are not identical
            double rSquared = (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
            double F = (G * other.mass * this.mass) / rSquared;
            double a = F / this.mass; //calc magnitude of acceleration on this body

            double aX = a * (other.x - this.x) / Math.sqrt(rSquared); // aCos(theta)
            double aY = a * (other.y - this.y) / Math.sqrt(rSquared); // aSin(theta)

            updateVelocity(aX, aY, timeStep);
        }

    }


    public static void main(String[] args) {
        StdDraw.setCanvasSize(500,500);
        StdDraw.setScale(-250, 250); // so that centre is at 0,0
        StdDraw.enableDoubleBuffering();
        planet earth = new planet("Earth",5.972 * 10e24,-0.9832899*AU,0,0,29.783 * 1000,6371, false, Color.blue ); // set at perihelion speed
        planet mercury = new planet("Mercury",0.33010 * 10e24,0.387*AU,0,0,-47.4 * 1000,2439.7, false, Color.orange);
        planet sun = new planet ("Sun",1.989e30,0,0,0,0,696340e3, true, Color.yellow); // sun at centre of solar system

//        earth.accelerate(sun);
//        sun.accelerate(earth);
//
//        earth.draw();
//        sun.draw();
//        StdDraw.show();
        for (double t = 0.0; true; t += 0.02) {
            StdDraw.clear(); // clear off-screen canvas
            earth.accelerate(sun);
            earth.accelerate(mercury);
            earth.move(); // update positions
            sun.accelerate(earth);
            sun.accelerate(mercury);
            sun.move();
            mercury.accelerate(sun);
            mercury.accelerate(earth);
            mercury.move();
            earth.draw(); // draw on off-screen canvas
            mercury.draw();
            sun.draw();
            StdDraw.show(); // copy to on-screen canvas and display
            StdDraw.pause(15); // slight delay for animation speed
        }

    }
}
