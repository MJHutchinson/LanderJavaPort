package graphics.instrument;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import static java.lang.Math.*;
import static graphics.text.TextHelper.*;

/**
 * Created by Michael Hutchinson on 02/08/2016 at 19:06 for LanderJavaPort in graphics.instrument.
 */
public class InstrumentHelper {

    public static final double INNER_DIAL_RADIUS = 65.0;
    public static final double OUTER_DIAL_RADIUS = 75.0;

    /**
     * Draws a single instrument dial, position (x, y), value val, title
     * @param x x position
     * @param y y position
     * @param val value of the dial
     * @param title title of the dial
     * @param units units of the dial
     * @param drawable the GLAutoDrawable object
     */
    public static void drawDial(double x, double y, double val, String title, String units, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();

        int i, e;
        double a;
        String s;

        // Work out value mantissa and exponent
        if (val <= 0.0) {
            e = 0;
            a = 0.0;
        } else {
            e = 0;
            a = val;
            while (a >= 10.0) {
                e++;
                a /= 10.0;
            }
        }

        // Draw dial ticks
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_LINES);
        for (i=30; i<=330; i+=30) {
            gl.glVertex2d(x - OUTER_DIAL_RADIUS * sin(i*PI/180.0), y - OUTER_DIAL_RADIUS * cos(i*PI/180.0));
            gl.glVertex2d(x - INNER_DIAL_RADIUS * sin(i*PI/180.0), y - INNER_DIAL_RADIUS * cos(i*PI/180.0));
        }
        gl.glEnd();

        // Draw dial needle
        gl.glColor3f(0.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(x, y);
        gl.glVertex2d(x - INNER_DIAL_RADIUS * sin((a*30+30)*PI/180.0), y - INNER_DIAL_RADIUS * cos((a*30+30)*PI/180.0));
        gl.glEnd();

        // Draw exponent indicator, value and title
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        s = "x 10 ^ " + e + " " + units;
        printTextToScreen( (float)(x + 10 - 3.2 * s.length()), (float) y + 10, s, drawable);
        printTextToScreen((float)(x + 10 - 3.2 * title.length()), (float)(y - OUTER_DIAL_RADIUS - 15), title, drawable);
        s = String.format("%.02f", val) +" " + units;
        printTextToScreen((float)(x + 10 - 3.2 * s.length()), (float)(y - OUTER_DIAL_RADIUS- 30), s, drawable);

        // Draw tick labels
        for (i=0; i<=10; i++) {
            switch(i) {
                case 0:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) - 8, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) - 9, "0", drawable);
                    break;
                case 1:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) - 7, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) - 6, "1", drawable);
                    break;
                case 2:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) - 9, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) - 4, "2", drawable);
                    break;
                case 3:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) - 9, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) - 1, "3", drawable);
                    break;
                case 4:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) - 8, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) + 3, "4", drawable);
                    break;
                case 5:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) - 3, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) + 4, "5", drawable);
                    break;
                case 6:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) + 2, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) + 3, "6", drawable);
                    break;
                case 7:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) + 4, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0), "7", drawable);
                    break;
                case 8:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) + 3, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) - 4, "8", drawable);
                    break;
                case 9:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) + 3, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) - 6, "9", drawable);
                    break;
                case 10:
                    printTextToScreen(x - OUTER_DIAL_RADIUS * sin((i*30+30)*PI/180.0) + 3, y - OUTER_DIAL_RADIUS * cos((i*30+30)*PI/180.0) - 8, "10", drawable);
                    break;
            }
        }
    }

    /**
     * Draws control bar, top left (x, y), val (fraction, range 0-1), colour (red, green, blue), title
     * @param x top left corner x
     * @param y top left corner y
     * @param val value of the control bar, a fraction range 0-1
     * @param red
     * @param green
     * @param blue
     * @param title title of the control bar
     * @param drawable the GLAutoDrawable object
     */
    public static void drawControlBar(double x, double y, double val, double red, double green, double blue, String title, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();

        gl.glColor3f((float)red, (float)green, (float)blue);
        gl.glBegin(GL2GL3.GL_QUADS);
        gl.glVertex2d(x+0.5, y-19.5);
        gl.glVertex2d(x+0.5+239.0*val, y-19.5);
        gl.glVertex2d(x+0.5+239.0*val, y-0.5);
        gl.glVertex2d(x+0.5, y-0.5);
        gl.glEnd();
        gl.glColor3f((float)1.0, (float)1.0, (float)1.0);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2d(x, y-20.0);
        gl.glVertex2d(x+240.0, y-20.0);
        gl.glVertex2d(x+240.0, y);
        gl.glVertex2d(x, y);
        gl.glEnd();
        printTextToScreen(x, y-40, title, drawable);
    }

    /**
     * Draws indicator lamp, top centre (x, y), appropriate text and background colour depending on on/off
     * @param x top center x
     * @param y top center y
     * @param offText text to display when indicator off
     * @param onText text to display when indicator on
     * @param on is on
     * @param drawable the GLAutoDrawable object
     */
    public static void drawIndicatorLamp(double x, double y, String offText, String onText, boolean on, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();

        if (on) gl.glColor3f(0.5f, 0.0f, 0.0f);
        else gl.glColor3f(0.0f, 0.5f, 0.0f);
        gl.glBegin(GL2GL3.GL_QUADS);
        gl.glVertex2d(x-74.5, y-19.5);
        gl.glVertex2d(x+74.5, y-19.5);
        gl.glVertex2d(x+74.5, y-0.5);
        gl.glVertex2d(x-74.5, y-0.5);
        gl.glEnd();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glVertex2d(x-75.0, y-20.0);
        gl.glVertex2d(x+75.0, y-20.0);
        gl.glVertex2d(x+75.0, y);
        gl.glVertex2d(x-75.0, y);
        gl.glEnd();
        if (on) printTextToScreen(x-70.0, y-14.0, onText, drawable);
        else printTextToScreen(x-70.0, y-14.0, offText, drawable);
    }

}
