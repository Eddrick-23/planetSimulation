/*
compile: javac-introcs draw.java
run: java-introcs draw.java
 */
public class draw {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(800,800);
        StdDraw.setScale(-400, 400); // so that centre is at 0,0

        double centerX = 0;
        double centerY = 0;
        double radius = 100;
        StdDraw.filledCircle(0,0,radius); //centre
        StdDraw.filledCircle(400,-400,radius); // bottom right
        StdDraw.filledCircle(-400,400,radius); //top left
        StdDraw.filledCircle(-400,-400,radius); //bottom left
        StdDraw.filledCircle(400,400,radius); // top right
        StdDraw.show();


        //StdDraw.enableDoubleBuffering(); // causes all drawing to take place on off screen canvas, only when we call StdDraw.show does it appear on onscreen canvas

        // 4 steps for drawing
        // clear offscreen canvas StdDraw.clear()
        // draw objects on offscreen canvas --> StdDraw.filledCircle()
        // copy offscreen canvas to onscreen canvas
        // wait for a short while


//        for (double t = 0.0; true; t += 0.02) {
//            double x = Math.sin(t);
//            double y = Math.cos(t);
//            StdDraw.clear();
//            StdDraw.filledCircle(x, y, 0.1);
//            StdDraw.filledCircle(-x, -y, 0.1);
//            StdDraw.show();
//            StdDraw.pause(20);
//        }
    }
}
