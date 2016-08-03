package graphics.listener;

import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import graphics.lighting.GLLightingHelper;
import static graphics.GraphicsConfiguration.*;

/**
 * Created by Michael Hutchinson on 03/08/2016 at 09:15 for LanderJavaPort in graphics.listener.
 */
public class GLEventListenerCloseUpCanvas implements GLEventListener {

    double closeup_offset, closeup_xr, closeup_yr, terrain_angle;
    boolean texture_available = false;
    boolean do_texture = false;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL4bc gl = glAutoDrawable.getGL().getGL4bc();

        gl.glDrawBuffer(GL.GL_BACK);
        GLLightingHelper.setupLights(false, glAutoDrawable);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GLLightingFunc.GL_LIGHTING);
        gl.glEnable(GL.GL_CULL_FACE); // we only need back faces for the parachute
        gl.glDisable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GLLightingFunc.GL_NORMALIZE);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GLLightingFunc. GL_AMBIENT_AND_DIFFUSE);
        gl.glLightModeli(GL2ES1.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE); // we need two-sided lighting for the parachute
        gl.glEnable(GLLightingFunc.GL_COLOR_MATERIAL);
        gl.glFogi(GL2ES1.GL_FOG_MODE, GL2ES1.GL_EXP);
//        texture_available = generate_terrain_texture();
        if (!texture_available) do_texture = false;
        closeup_offset = 50.0;
        closeup_xr = 10.0;
        closeup_yr = 0.0;
        terrain_angle = 0.0;

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL4bc gl = glAutoDrawable.getGL().getGL4bc();

        viewWidth = width * 2;
        viewWidth2 = width;
        topHeight = height;
        gl.glDrawBuffer(GL.GL_BACK);
    }
}
