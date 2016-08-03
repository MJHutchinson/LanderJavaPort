package graphics.text;

import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Created by Michael Hutchinson on 02/08/2016 at 18:02 for LanderJavaPort in graphics.test.
 */
public class TextHelper {

    public static void printTextToScreen(float x, float y, String s, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();
        GLUT glut = new GLUT();

        gl.glRasterPos2f(x, y);
        for(int i = 0; i < s.length(); i++){
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_10, s.charAt(i));
        }
    }

    public static void printTextToScreen(double x, double y, String s, GLAutoDrawable drawable){
        printTextToScreen((float)x, (float)y, s, drawable);
    }
}
