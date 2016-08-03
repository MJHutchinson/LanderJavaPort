package graphics.listener;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import graphics.GraphicsConfiguration;
import graphics.math.Vector3.Vector3;

import static graphics.instrument.InstrumentHelper.*;
import static graphics.GraphicsConfiguration.*;
import static graphics.text.TextHelper.*;

/**
 * Created by Michael Hutchinson on 03/08/2016 at 09:14 for LanderJavaPort in graphics.listener.
 */
public class GLEventListenerInstrumentCanvas implements GLEventListener {
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL4bc gl = glAutoDrawable.getGL().getGL4bc();
        GLU glu = new GLU();

//        gl.glClearColor(0f,0f,0f,1f);
//        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
//        gl.glLoadIdentity();
//        glu.gluOrtho2D(0, GraphicsConfiguration.PREFERRED_WIDTH,0, GraphicsConfiguration.PREFERRED_INSTRUMENT_HEIGHT);

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glViewport(0,0,viewWidth, instrumentHeight);
        glu.gluOrtho2D(0f, (float)(viewWidth), 0f, (float)instrumentHeight);
        gl.glDrawBuffer(GL.GL_BACK);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        int parachute_status = 0;
        double altitude = 125000;
        double climb_speed = -12.3;
        double ground_speed = 45520;
        double simulation_speed = 6.0;
        double simulation_time = 288.48;
        double throttle = 0.4;
        double fuel = 0.3;
        double FUEL_CAPACITY = 25000;
        double LANDER_SIZE = 1;
        boolean autopilot_enabled = true;
        boolean stabilized_attitude = true;
        boolean landed = false;
        boolean paused = false;
        String scenario = "Test";
        Vector3 position = new Vector3(25555, 2541.0, 2641853.5445);
        Vector3 velocity_from_positions = new Vector3(23524566, 2346346, 0);
        Vector3 thrust_wrt_world = new Vector3(54,85.6,555.266);

        GL4bc gl = glAutoDrawable.getGL().getGL4bc();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // draw altimeter
        drawDial(viewWidth2 - 400, instrumentHeight / 2, altitude, "Altitude", "m", glAutoDrawable);

        // Draw auto-pilot lamp
        drawIndicatorLamp (viewWidth2 - 400, instrumentHeight - 18, "Auto-pilot off", "Auto-pilot on", autopilot_enabled, glAutoDrawable);

        // Draw climb rate meter
        if (climb_speed >= 0.0) drawDial(viewWidth - 150, instrumentHeight/2, landed ? 0.0 : climb_speed, "Climb rate", "m/s", glAutoDrawable);
        else drawDial(viewWidth2 - 150, instrumentHeight / 2, landed ? 0.0 : -climb_speed, "Descent rate", "m/s", glAutoDrawable);

        // Draw attitude stabilizer lamp
        drawIndicatorLamp (viewWidth2 - 150, instrumentHeight-18, "Attitude stabilizer off", "Attitude stabilizer on", stabilized_attitude, glAutoDrawable);

        // Draw ground speed meter
        drawDial (viewWidth2 +100, instrumentHeight/2, landed ? 0.0 : ground_speed, "Ground speed", "m/s", glAutoDrawable);

        switch (parachute_status) {
            case 0:
                drawIndicatorLamp (viewWidth2 + 100, instrumentHeight-18, "Parachute not deployed", "Do not deploy parachute", false, glAutoDrawable); //false should be replaced with is ok to deploy parachute.
                break;
            case 1:
                drawIndicatorLamp (viewWidth2 + 100, instrumentHeight-18, "Parachute deployed", "", false, glAutoDrawable);
                break;
            case 2:
                drawIndicatorLamp (viewWidth2 + 100, instrumentHeight-18, "", "Parachute lost", true, glAutoDrawable);
                break;
        }

        // Draw speed bar
        drawControlBar(viewWidth2 + 240, instrumentHeight-18, simulation_speed/10.0, 0.0, 0.0, 1.0, "Simulation speed", glAutoDrawable);

        // Draw digital clock
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        String s = "Time " + String.format("%.02f", simulation_time) + " s";
        printTextToScreen(viewWidth2 + 400, instrumentHeight-58, s, glAutoDrawable);
        if (paused) {
            gl.glColor3f(1.0f, 0.0f, 0.0f);
            printTextToScreen(viewWidth2 + 338, instrumentHeight-32, "PAUSED", glAutoDrawable);
        }

        // Display coordinates
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        s = "x position " + String.format("%.02f", position.x) + " m";
        printTextToScreen(viewWidth2 + 240, instrumentHeight-97, s, glAutoDrawable);
        s = "velocity " + String.format("%.02f", velocity_from_positions.x) + " m/s";
        printTextToScreen(viewWidth2 + 380, instrumentHeight-97, s, glAutoDrawable);
        s = "y position " + String.format("%.02f", position.y) + " m";
        printTextToScreen(viewWidth2 + 240, instrumentHeight-117, s, glAutoDrawable);
        s = "velocity " + String.format("%.02f", velocity_from_positions.y) + " m/s";
        printTextToScreen(viewWidth2 + 380, instrumentHeight-117, s, glAutoDrawable);
        s = "z position " + String.format("%.02f", position.z) + " m";
        printTextToScreen(viewWidth2 + 240, instrumentHeight-137, s, glAutoDrawable);
        s = "velocity " + String.format("%.02f", velocity_from_positions.z) + " m/s";
        printTextToScreen(viewWidth2 + 380, instrumentHeight-137, s, glAutoDrawable);

        // Draw thrust bar
        s = "Thrust " + String.format("%.02f", thrust_wrt_world.abs()) + " N";
        drawControlBar(viewWidth2 + 240, instrumentHeight-170, throttle, 1.0, 0.0, 0.0, s, glAutoDrawable);

        // Draw fuel bar
        s = "Fuel " + String.format("%.02f",fuel*FUEL_CAPACITY) + " litres"; //25000 should be fuel capacity
        if (fuel > 0.5) drawControlBar(viewWidth2 + 240, instrumentHeight-242, fuel, 0.0, 1.0, 0.0, s, glAutoDrawable);
        else if (fuel > 0.2) drawControlBar(viewWidth2 + 240, instrumentHeight-242, fuel, 1.0, 0.5, 0.0, s, glAutoDrawable);
        else drawControlBar(viewWidth2 + 240, instrumentHeight-242, fuel, 1.0, 0.0, 0.0, s, glAutoDrawable);

        // Display simulation status
        if (landed) gl.glColor3f(1.0f, 1.0f, 0.0f);
        else gl.glColor3f(1.0f, 1.0f, 1.0f);
        s = "Scenario " + scenario;
        if (!landed) s += " testy test"; // scenario_description[scenario];
        printTextToScreen(viewWidth2 - 488, 17, s, glAutoDrawable);
        if (landed) {
            if (altitude < LANDER_SIZE/2.0) printTextToScreen(80, 17, "Lander is below the surface!", glAutoDrawable);
            else {
                s = "Fuel consumed " + String.format("%.02f",FUEL_CAPACITY*(1.0-fuel)) + " litres";
                printTextToScreen(viewWidth2 - 427, 17, s, glAutoDrawable);
                s = "Descent rate at touchdown " + String.format("%.02f", -climb_speed) + " m/s";
                printTextToScreen(viewWidth2 - 232, 17, s, glAutoDrawable);
                s = "Ground speed at touchdown " + String.format("%.02f", ground_speed) + " m/s";
                printTextToScreen(viewWidth2 + 16, 17, s, glAutoDrawable);
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL4bc gl = glAutoDrawable.getGL().getGL4bc();
        GLU glu = new GLU();

        // Resize and initialize the instrument window
        viewWidth = width;
        viewWidth2 = width/2;
        instrumentHeight = height;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0f, (float)(viewWidth), 0f, (float)instrumentHeight);
        gl.glDrawBuffer(GL.GL_BACK);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
