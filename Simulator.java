import java.awt.*;
import java.util.ArrayList;

public class Simulator {
    public static final double AU = 1.496e8 * 1000; // in m
    public ArrayList<planet> celestialBodies = new ArrayList<>();
    int width;
    int height;
    public final double timeStep;
    double timeElapsed = 0.0;

    public Simulator(int w, int h, double ts){
        width = w;
        height = h;
        timeStep = ts;
    }

    public void addBody(planet b){
        celestialBodies.add(b);
    }

    public void updateAllPositions(double timeStep){
        for (int i = 0; i < celestialBodies.size(); i++){ // loop through all bodies
            planet A = celestialBodies.get(i);
            for (int j = i+1; j < celestialBodies.size(); j++){
                planet B = celestialBodies.get(j);
                A.accelerate(B, timeStep); // accelerate and update velocities
                B.accelerate(A, timeStep);
            }
            A.move(timeStep); // only update after taking into account all accelerations
        }

    }

    private void drawAll(boolean connectingLine, int pixelLimit){
        for (planet p:celestialBodies){
            p.draw(connectingLine, pixelLimit);
        }
    }

    private void displayInfoOnHover(int pixelLimit){
        double mX = StdDraw.mouseX();
        double mY = StdDraw.mouseY();

        for (planet p: celestialBodies){
            if (p.isMouseOver(mX, mY, pixelLimit)){
                p.displayInfo(width, height);
            }
        }

    }

    private void displayElapsedTime(){
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(new Font("Arial", Font.BOLD,20));
        StdDraw.text(0, height/2.0 - 20, "Elapsed Time(Earth Days): " + Math.round(timeElapsed));
        timeElapsed += timeStep/86400;
    }
    public void animate(boolean connectingLine, int pixelLimit, boolean benchmark){
        StdDraw.setCanvasSize(width,height);
        StdDraw.setScale((double) -width /2, (double) height /2); // so that centre is at 0,0
        StdDraw.enableDoubleBuffering();

        if (!benchmark) {
            for (double i = 0.0; true; i += 0.01) {
                StdDraw.clear();
                updateAllPositions(timeStep);
                drawAll(connectingLine, pixelLimit);
                displayElapsedTime();
                displayInfoOnHover(pixelLimit);
                StdDraw.show();
                StdDraw.pause(0);
            }
        }
        else {
            while (timeElapsed <= 10000) { // For Benchmarking
                StdDraw.clear();
                updateAllPositions(timeStep);
                drawAll(connectingLine, pixelLimit);
                displayElapsedTime();
                displayInfoOnHover(pixelLimit);
                StdDraw.show();
                StdDraw.pause(0);
            }
        }

    }

    public static void main(String[] args) {
        planet earth = new planet("Earth",5.972 * 10e24,-0.9832899*AU,0,0,29.783 * 1000,6371, false, Color.blue ); // set at perihelion speed
        planet mercury = new planet("Mercury",0.33010 * 10e24,0.387*AU,0,0,-47.4 * 1000,2439.7, false, Color.orange);
        planet mars = new planet("Mars",6.39*10e23, -1.524*AU,0,0,24.077 * 1000,3389.5, false, Color.red);
        planet venus = new planet("Venus",4.8685 * 10e24,0.723*AU,0,0,-35.02 * 1000,6051.8,false, Color.pink);
        planet sun = new planet ("Sun",1.989e30,0,0,0,0,696340e3, true, Color.yellow); // sun at centre of solar system

        Simulator solarSystem = new Simulator(500,500, 86400/32);
        solarSystem.addBody(sun); // add bodies
        solarSystem.addBody(earth);
        solarSystem.addBody(mars);
        solarSystem.addBody(mercury);
        solarSystem.addBody(venus);
        // animate
        long startTime = System.nanoTime();
        solarSystem.animate(false, 1500, true);
        long endTime = System.nanoTime();
        long timeTaken = Math.round((endTime-startTime)/1e9);
        System.out.println("time for 365 days simulation: " + timeTaken + "s");



    }
}
