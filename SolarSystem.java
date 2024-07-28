import java.awt.*;

public class SolarSystem {
    public static final double AU = 1.496e8 * 1000;
    public static void main(String[] args) {
        planet earth = new planet("Earth",5.972 * 10e24,-0.9832899* AU,0,0,29.783 * 1000,6371, false, Color.blue ); // set at perihelion speed
        planet mercury = new planet("Mercury",0.33010 * 10e24,0.387*AU,0,0,-47.4 * 1000,2439.7, false, Color.orange);
        planet mars = new planet("Mars",6.39*10e23, -1.524*AU,0,0,24.077 * 1000,3389.5, false, Color.red);
        planet venus = new planet("Venus",4.8685 * 10e24,0.723*AU,0,0,-35.02 * 1000,6051.8,false, Color.pink);
        planet jupiter = new planet("Jupiter", 1898.13* 10e24, 5.035*AU,0,0,13.72*1000,69911, false, Color.green);
        planet uranus = new planet("Uranus",86.811*10e24, 18.578*AU, 0,0,7.13*1000,25362, false, Color.cyan);
        planet neptune = new planet("Neptune",102.409*10e24, -30.396*AU, 0,0,5.47*1000,24622, false, Color.MAGENTA);
        planet sun = new planet ("Sun",1.989e30,0,0,0,0,696340e3, true, Color.yellow); // sun at centre of solar system



        Simulator solarSystem = new Simulator(700,700, 86400*2);
        solarSystem.addBody(sun); // add bodies
        solarSystem.addBody(earth);
        solarSystem.addBody(mars);
        solarSystem.addBody(mercury);
        solarSystem.addBody(venus);
        solarSystem.addBody(jupiter);
        solarSystem.addBody(uranus);
        solarSystem.addBody(neptune);
        // animate
        long startTime = System.nanoTime();
        solarSystem.animate(false,300);
        long endTime = System.nanoTime();
        long timeTaken = Math.round((endTime-startTime)/1e9);
        System.out.println("time for 365 days simulation: " + timeTaken + "s");
    }
}
