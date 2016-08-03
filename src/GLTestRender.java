import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import graphics.instrument.InstrumentHelper;
import graphics.text.TextHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Michael Hutchinson on 03/08/2016 at 00:04 for LanderJavaPort in PACKAGE_NAME.
 */
public class GLTestRender implements GLEventListener, WindowListener{

    Animator animator;
    static double i = 1;

    static public void main(String[] args){

        GLTestRender test = new GLTestRender();
        test.run();

    }

    public void run(){
        GLCapabilities config = new GLCapabilities(GLProfile.get(GLProfile.GL4bc));
        //config.setSampleBuffers(true);

        GLCanvas instrumentCanvas = new GLCanvas(config);
        GLCanvas mainCanvas = new GLCanvas(config);
        GLCanvas closeCanvas = new GLCanvas(config);
        instrumentCanvas.addGLEventListener(this);

        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(this);
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(0,0,1,1,0.5,0.5,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
        frame.add(closeCanvas, c);
        c.gridx = 1;
        frame.add(mainCanvas, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        frame.add(instrumentCanvas, c);
        frame.getContentPane().add(instrumentCanvas, c);
        frame.setSize(500,500);
        frame.setVisible(true);

        animator = new Animator(instrumentCanvas);
        animator.start();

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL4bc gl = glAutoDrawable.getGL().getGL4bc();
        GLU glu = new GLU();

        gl.glClearColor(0f,0f,0f,1f);
//        gl.glViewport(0,0,500,500);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0,500,0,250);

//
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
//        InstrumentHelper.drawDial(50, 50, 34560, "Test", "m/s", glAutoDrawable);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL4bc gl = glAutoDrawable.getGL().getGL4bc();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glDisable(GLLightingFunc.GL_LIGHTING);
        gl.glColor3d(0.5,0.5,0.5);
//        gl.glBegin(GL.GL_POINTS);
//        gl.glVertex2f(100,100);
//        gl.glEnd();
        InstrumentHelper.drawDial(250, 250, i*=1.1, "Test", "m/s", glAutoDrawable);
        InstrumentHelper.drawControlBar(0, 500, 0.7, 1,1,0,"Test", glAutoDrawable);
//        TextHelper.printTextToScreen(250,250,"rgjofjiaigjr",glAutoDrawable);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(animator != null) animator.stop();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
