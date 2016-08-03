import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import graphics.GraphicsConfiguration;
import graphics.listener.GLEventListenerCloseUpCanvas;
import graphics.listener.GLEventListenerInstrumentCanvas;
import graphics.listener.GLEventListenerOrbitalCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Michael Hutchinson on 30/07/2016 at 15:34 for JavaLanderPort in graphics.
 */
public class Lander implements Runnable, WindowListener{

    private GLCanvas instrumentCanvas, orbitalCanvas, closeUpCanvas;
    private Animator animator;
    private JFrame frame;

    public static void main(String[] agrs){

       new Lander().run();

    }

    @Override
    public void run() {
        GLCapabilities config = new GLCapabilities(GLProfile.get(GLProfile.GL4bc));

        closeUpCanvas = new GLCanvas(config);
        orbitalCanvas = new GLCanvas(config);
        instrumentCanvas = new GLCanvas(config);

        closeUpCanvas.addGLEventListener(new GLEventListenerCloseUpCanvas());
        orbitalCanvas.addGLEventListener(new GLEventListenerOrbitalCanvas());
        instrumentCanvas.addGLEventListener(new GLEventListenerInstrumentCanvas());

        frame = new JFrame("Mars Lander (Java port from c++ code by Michael Hutchinson August 2016)");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(this);
        frame.getContentPane().setLayout(new GridBagLayout());

        GraphicsConfiguration.topRatio = (float) GraphicsConfiguration.PREFERRED_TOP_HEIGHT / (float)(GraphicsConfiguration.PREFERRED_TOP_HEIGHT + GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT);
        GraphicsConfiguration.instrumentRatio = (float) GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT / (float)(GraphicsConfiguration.PREFERRED_TOP_HEIGHT + GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT);


        GridBagConstraints c = new GridBagConstraints(0,0,1,1,0.5,0.5,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
        c.weighty = GraphicsConfiguration.topRatio;
        frame.add(closeUpCanvas, c);
        c.gridx = 1;
        frame.add(orbitalCanvas, c);
        c.weighty = GraphicsConfiguration.instrumentRatio;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        frame.add(instrumentCanvas, c);
        frame.getContentPane().add(instrumentCanvas, c);
        frame.setSize(GraphicsConfiguration.PREFERRED_WIDTH, GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT + GraphicsConfiguration.PREFERRED_TOP_HEIGHT);
        GraphicsConfiguration.instrumentHeight = (int)(GraphicsConfiguration.instrumentRatio * (float)(GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT + GraphicsConfiguration.PREFERRED_TOP_HEIGHT));
        GraphicsConfiguration.topHeight = (int)(GraphicsConfiguration.topRatio * (float)(GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT + GraphicsConfiguration.PREFERRED_TOP_HEIGHT));
        GraphicsConfiguration.viewWidth = GraphicsConfiguration.PREFERRED_WIDTH;
        GraphicsConfiguration.viewWidth2 = GraphicsConfiguration.viewWidth/2;
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(GraphicsConfiguration.PREFERRED_WIDTH, GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT + GraphicsConfiguration.PREFERRED_TOP_HEIGHT));
//        frame.setResizable(false);

        animator = new Animator(instrumentCanvas);
        animator.start();
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
