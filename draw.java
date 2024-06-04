/*
compile: javac-introcs draw.java
run: java-introcs draw.java
 */
public class draw {

    private static void checkHover(double x, double y){ // takes in coordinates of circle
        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();

        // check if mouseX and mouseY within radius of circle

        if ((mouseX - x)<0.1 && (mouseY - y)<0.1){
            displayInfo();
        }

    }

    private static void displayInfo(){
        //Draw information box near mouse cursor or at fixed position
        double infoBoxWidth = 200;
        double infoBoxHeight = 100;
        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();

        // Draw background
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(mouseX + 20, mouseY + 20, infoBoxWidth / 2, infoBoxHeight / 2); // draw to the right of mouse cursor

        // Draw border
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(mouseX + 20, mouseY + 20, infoBoxWidth / 2, infoBoxHeight / 2);

        // Draw text
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(mouseX + 20, mouseY + 20, "Planet Name: ");
        StdDraw.text(mouseX + 20, mouseY + 15, "Distance from Sun: " );
    }
    public static void main(String[] args) {
//        StdDraw.setCanvasSize(800,800);
//        StdDraw.setScale(-400, 400); // so that centre is at 0,0
//
//        double centerX = 0;
//        double centerY = 0;
//        double radius = 100;
//        StdDraw.filledCircle(0,0,radius); //centre
//        StdDraw.filledCircle(400,-400,radius); // bottom right
//        StdDraw.filledCircle(-400,400,radius); //top left
//        StdDraw.filledCircle(-400,-400,radius); //bottom left
//        StdDraw.filledCircle(400,400,radius); // top right
//        StdDraw.show();

        StdDraw.setScale(-2.0, +2.0);
        StdDraw.enableDoubleBuffering(); // causes all drawing to take place on off screen canvas, only when we call StdDraw.show does it appear on onscreen canvas

        // 4 steps for drawing
        // clear offscreen canvas StdDraw.clear()
        // draw objects on offscreen canvas --> StdDraw.filledCircle()
        // copy offscreen canvas to onscreen canvas
        // wait for a short while


        for (double t = 0.0; true; t += 0.02) {
            double x = Math.sin(t);
            double y = Math.cos(t);
            StdDraw.clear();
            StdDraw.filledCircle(x, y, 0.1);
            StdDraw.filledCircle(-x, -y, 0.1);
            //check hover
            checkHover(x,y);
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}
