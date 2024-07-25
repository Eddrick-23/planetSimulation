import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class planet {
    private final Font font = new Font("Arial", Font.BOLD,10);
    public final String name;
    private final double G = 6.6743e-11; // gravitational field constant
    private static final double AU = 1.496e8 * 1000; // in m
    public final double scale = 30/AU; // 1AU = 50 pixel, scale for coordinates
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

    public void move(double timeStep){ // move the planet based on its velocities
        x = x + vX*timeStep;
        y = y + vY*timeStep;
    }

    private double rounder(double input){
        BigDecimal bd = new BigDecimal(input);
        bd = bd.round(new MathContext(4));
        return bd.doubleValue();
    }

    private double getDistFromSun(){
        double distFromSun = Math.sqrt(x*x + y*y)/10e3;
        return rounder(distFromSun);
    }

    public boolean isMouseOver(double mX, double mY, int pixelLimit){ //checks whether mouse is hovering over planet, based on scaled planet coordinates
        // x coord should be within [planetX - radius, planetX + radius]
        // y coord should be within [planetY - radius, planetY + radius]

        double xPos = scaleCoords(x*scale, pixelLimit);
        double yPos = scaleCoords(y*scale, pixelLimit);

        return (mX >= xPos - scaledRadius && mX <= xPos + scaledRadius &&
                mY >= yPos - scaledRadius && mY <= yPos + scaledRadius);
    }

    public void displayInfo(double width, double height){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(font);
        StdDraw.textLeft(-width/2.0 + 20, height / 2.0 - 30, "Name: " + name);
        StdDraw.textLeft(-width/2.0 + 20, height / 2.0 - 60, "Position: (" + rounder(x) + ", " + rounder(y) + ")");
        StdDraw.textLeft(-width/2.0 + 20, height / 2.0 - 90, "Velocity: (" + rounder(vX) + ", " + rounder(vY) + ")");
        StdDraw.textLeft(-width/2.0 + 20, height / 2.0 - 120, "Mass: " + rounder(mass));
        StdDraw.textLeft(-width/2.0 + 20, height / 2.0 - 150, "Radius: " + r);
        if (!isStar){
            StdDraw.textLeft(-width/2.0 + 20, height / 2.0 - 180, "Distance from star : " + getDistFromSun());
        }
    }

    private double scaleCoords(double n, int pixelLimit){
        // -0.002 is the rate, reduce to make the scaling more linear until the extreme values
        double b = pixelLimit * Math.signum(n) * (1 - Math.exp(-0.002 * Math.abs(n)));
        return b;

    }

    public void draw(boolean connectingLine, int pixelLimit){
        double xPos = scaleCoords(x*scale , pixelLimit);
        double yPos = scaleCoords(y*scale, pixelLimit);
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(xPos,yPos, scaledRadius); // only do scaling at end since we use actual radius for calc.
        StdDraw.setPenColor();
        StdDraw.setFont(font);
        StdDraw.text(xPos,yPos+scaledRadius,name); // display the name above the planet
        if (!isStar && connectingLine) {
            StdDraw.line(xPos, yPos, 0, 0); // draw line connecting planet and sun
            StdDraw.text(xPos, yPos-scaledRadius, getDistFromSun()+"km");
        }
    }

    private void updateVelocity(double aX, double aY, double timeStep){
        vX += aX*timeStep; // update the velocities based on time step of 1 day
        vY += aY*timeStep;
    }

    public void accelerate(planet other, double timeStep){ //accelerates the bodies due to gravity

        if (!this.equals(other)) { // only update if planets are not identical
            double rSquared = (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
            double F = (G * other.mass * this.mass) / rSquared;
            double a = F / this.mass; //calc magnitude of acceleration on this body

            double aX = a * (other.x - this.x) / Math.sqrt(rSquared); // aCos(theta)
            double aY = a * (other.y - this.y) / Math.sqrt(rSquared); // aSin(theta)

            updateVelocity(aX, aY, timeStep);
        }

    }
//    public synchronized void acceleratev2(ArrayList<planet> others,double timeStep){
//        for (planet p: others){
//            if (!this.equals(p)) accelerate(p, timeStep);
//        }
//    }

    public static void main(String[] args) {
        StdDraw.setCanvasSize(500,500);
        StdDraw.setScale(-250, 250); // so that centre is at 0,0
        StdDraw.enableDoubleBuffering();
        planet earth = new planet("Earth",5.972 * 10e24,-0.9832899*AU,0,0,29.783 * 1000,6371, false, Color.blue ); // set at perihelion speed
        planet mercury = new planet("Mercury",0.33010 * 10e24,0.387*AU,0,0,-47.4 * 1000,2439.7, false, Color.orange);
        planet sun = new planet ("Sun",1.989e30,0,0,0,0,696340e3, true, Color.yellow); // sun at centre of solar system
        double timeStep = 86400;

    }
}
