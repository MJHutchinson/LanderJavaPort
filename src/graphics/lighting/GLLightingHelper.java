package graphics.lighting;

import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;

/**
 * Created by Michael Hutchinson on 02/08/2016 at 17:40 for LanderJavaPort in graphics.lighting.
 */
public class GLLightingHelper {

    public static final float plus_y[] = new float[]{(float) 0.0, (float) 1.0, (float) 0.0, (float) 0.0};
    public static final float minus_y[] = {(float) 0.0, (float) -1.0, (float) 0.0, (float) 0.0};
    public static final float plus_z[] = {(float) 0.0, (float) 0.0, (float) 1.0, (float) 0.0};
    public static final float top_right[] = {(float) 1.0, (float) 1.0, (float) 1.0, (float) 0.0};
    public static final float straight_on[] = {(float) 0.0, (float) 0.0, (float) 1.0, (float) 0.0};

    public static final float none[] = {(float) 0.0, (float) 0.0, (float) 0.0, (float) 1.0};
    public static final float low[] = {(float) 0.15, (float) 0.15, (float) 0.15, (float) 1.0};
    public static final float medium[] = {(float) 0.5, (float) 0.5, (float) 0.5, (float) 1.0};
    public static final float high[] = {(float) 0.75, (float) 0.75, (float) 0.75, (float) 1.0};

    /**
     * Enables the appropriate lights for static/dynamic lighting
     * @param staticLighting true for static lighting, false otherwise
     * @param drawable the GLAutoDrawable object
     */
    public static void enableLights(boolean staticLighting, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();

        if (staticLighting) {
            gl.glDisable(GLLightingFunc.GL_LIGHT0);
            gl.glDisable(GLLightingFunc.GL_LIGHT1);
            gl.glEnable(GLLightingFunc.GL_LIGHT2);
            gl.glEnable(GLLightingFunc.GL_LIGHT3);
            gl.glDisable(GLLightingFunc.GL_LIGHT4);
            gl.glDisable(GLLightingFunc.GL_LIGHT5);
        } else {
            gl.glEnable(GLLightingFunc.GL_LIGHT0);
            gl.glEnable(GLLightingFunc.GL_LIGHT1);
            gl.glDisable(GLLightingFunc.GL_LIGHT2);
            gl.glDisable(GLLightingFunc.GL_LIGHT3);
            gl.glDisable(GLLightingFunc.GL_LIGHT4);
            gl.glDisable(GLLightingFunc.GL_LIGHT5);
        }
    }

    /**
     * Sets up the specific lights attributes for rendering
     * @param drawable the GLAutoDrawable object
     */
    public static void setupLights(boolean staticLighting, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();

        // Lights 0 and 1 are for the dynamic lighting model, with the lights fixed in the viewer's reference frame
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, high, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, top_right, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_AMBIENT, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_DIFFUSE, medium, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_SPECULAR, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_POSITION, straight_on, 0);

        // Lights 2 and 3 are for the static lighting model, with the lights fixed in the planetary reference frame
        gl.glLightfv(GLLightingFunc.GL_LIGHT2, GLLightingFunc.GL_AMBIENT, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT2, GLLightingFunc.GL_DIFFUSE, high, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT2, GLLightingFunc.GL_SPECULAR, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT3, GLLightingFunc.GL_AMBIENT, low, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT3, GLLightingFunc.GL_DIFFUSE, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT3, GLLightingFunc.GL_SPECULAR, none, 0);

        // Lights 4 and 5 are for highlighting the lander with static lights, to avoid flat views with ambient illumination only
        gl.glLightfv(GLLightingFunc.GL_LIGHT4, GLLightingFunc.GL_AMBIENT, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT4, GLLightingFunc.GL_DIFFUSE, low, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT4, GLLightingFunc.GL_SPECULAR, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT5, GLLightingFunc.GL_AMBIENT, none, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT5, GLLightingFunc.GL_DIFFUSE, low, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT5, GLLightingFunc.GL_SPECULAR, none, 0);

        enableLights(staticLighting, drawable);
    }

}
