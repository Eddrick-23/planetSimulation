import java.awt.*;
import java.util.ArrayList;

public class miniSolarSystem {
    private static final double AU = 1.496e8 * 1000; // in m
    private ArrayList<planet> celestialBodies = new ArrayList<>();
    int width;
    int height;
    private final double timeStep;
    double timeElapsed = 0.0;

    public miniSolarSystem(int w, int h, double ts){
        width = w;
        height = h;
        timeStep = ts; 
    }

    public void addBody(planet b){
        celestialBodies.add(b);
    }

    private void updateAllPositions(double timeStep){
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

    private void drawAll(boolean connectingLine){
        for (planet p:celestialBodies){
            p.draw(connectingLine);
        }
    }

    private void displayInfoOnHover(){
        double mX = StdDraw.mouseX();
        double mY = StdDraw.mouseY();

        for (planet p: celestialBodies){
            if (p.isMouseOver(mX, mY)){
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
    public void animate(boolean connectingLine){
        StdDraw.setCanvasSize(width,height);
        StdDraw.setScale((double) -width /2, (double) height /2); // so that centre is at 0,0
        StdDraw.enableDoubleBuffering();


        for (double i = 0.0; true; i+= 0.01){
            StdDraw.clear();
            updateAllPositions(timeStep);
            drawAll(connectingLine);
            displayElapsedTime();
            displayInfoOnHover();
            StdDraw.show();
            StdDraw.pause(0);
        }


    }
    public static void main(String[] args) {
        planet earth = new planet("Earth",5.972 * 10e24,-0.9832899*AU,0,0,29.783 * 1000,6371, false, Color.blue ); // set at perihelion speed
        planet mercury = new planet("Mercury",0.33010 * 10e24,0.387*AU,0,0,-47.4 * 1000,2439.7, false, Color.orange);
        planet mars = new planet("Mars",6.39*10e23, -1.524*AU,0,0,24.077 * 1000,3389.5, false, Color.red);
        planet venus = new planet("Venus",4.8685 * 10e24,0.723*AU,0,0,-35.02 * 1000,6051.8,false, Color.pink);
        planet sun = new planet ("Sun",1.989e30,0,0,0,0,696340e3, true, Color.yellow); // sun at centre of solar system

        miniSolarSystem solarSystem = new miniSolarSystem(500,500, 86400/32);
        solarSystem.addBody(sun); // add bodies
        solarSystem.addBody(earth);
        solarSystem.addBody(mars);
        solarSystem.addBody(mercury);
        solarSystem.addBody(venus);
        // animate
        solarSystem.animate(false);


    }
}
