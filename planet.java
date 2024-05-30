import java.awt.*;

public class planet {
    private final double timeStep = 86400; // 1 day
    private final double G = 6.6743e-11; // gravitational field constant
    private static final double AU = 1.496e8 * 1000; // in m
    private final double scale = 100/AU; // 1AU = 10 pixels
    private final double mass;
    private double x; // in m
    private double y; // in m
    private double vX; // horizontal velocity
    private double vY; // vertical velocity
    private final double r; // radius of planet
    private final boolean isStar; // true for the sun only
    private final Color color;

    public planet(double m, double initialX, double initialY, double velocityX, double velocityY, double radius, boolean star, Color c){
        mass = m;
        x = initialX; // set initial positions
        y = initialY;
        vX = velocityX; // set initial vertical and horizontal velocities
        vY = velocityY;
        r = radius;
        isStar = star;
        color = c;
    }

    public void move(){ // move the planet based on its velocities
        x = x + vX*timeStep;
        y = y + vY*timeStep;
    }

    public void draw(){
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x*scale,y*scale, 10); // only do scaling at end since we use actual radius for calc.
        StdDraw.setPenColor(); // reset after drawing
    }

    public void accelerate(planet other){ //accelerates the bodies due to gravity
        double rSquared = (this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y);
        double F = (G * other.mass * this.mass)/rSquared ;
        double a = F/this.mass; //calc magnitude of acceleration on this body
//        System.out.println("accelertation: "+ a);
        double aX = a * (other.x - this.x)/Math.sqrt(rSquared); // aCos(theta)
        double aY = a * (other.y - this.y)/Math.sqrt(rSquared) ; // aSin(theta)

//        System.out.println(aX);
//        System.out.println(aY);

        vX += aX*timeStep; // update the velocities based on time step of 1 day
        vY += aY*timeStep;

    }

    public static void main(String[] args) {
        StdDraw.setCanvasSize(800,800);
        StdDraw.setScale(-400, 400); // so that centre is at 0,0
        StdDraw.enableDoubleBuffering();
        planet earth = new planet(5.972 * 10e24,-0.9832899*AU,0,0,29.29e3,6371, false, Color.blue ); // set at perihelion speed
        planet sun = new planet (1.989e30,0,0,0,0,696340e3, true, Color.yellow); // sun at centre of solar system

//        earth.accelerate(sun);
//        sun.accelerate(earth);
//
//        earth.draw();
//        sun.draw();
//        StdDraw.show();
        for (double t = 0.0; true; t += 0.02) {
            StdDraw.clear(); // clear off-screen canvas
            earth.accelerate(sun);
            earth.move(); // update positions
            sun.accelerate(earth);
            sun.move();
            earth.draw(); // draw on off-screen canvas
            earth.draw();
            sun.draw();
            StdDraw.show(); // copy to on-screen canvas and display
            StdDraw.pause(0); // slight delay for animation speed
        }

    }
}
