package graphics.glut;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4bc;
import com.jogamp.opengl.GLAutoDrawable;

import java.util.Random;

import static java.lang.Math.*;

/**
 * Created by Michael Hutchinson on 02/08/2016 at 16:05 for LanderJavaPort in graphics.glut.
 */
public class GLUTHelper {

//    void fghCircleTable (double **sint, double **cost, const int n)
//    // Borrowed from freeglut source code, used to draw hemispheres and open cones
//    {
//        int i;
//        const int size = abs(n);
//        const double angle = 2*M_PI/(double)( ( n == 0 ) ? 1 : n );
//
//        *sint = (double*) calloc(sizeof(double), size+1);
//        *cost = (double*) calloc(sizeof(double), size+1);
//        if (!(*sint) || !(*cost)) exit(1);
//
//        (*sint)[0] = 0.0;
//        (*cost)[0] = 1.0;
//
//        for (i=1; i<size; i++) {
//            (*sint)[i] = sin(angle*i);
//            (*cost)[i] = cos(angle*i);
//        }
//
//        (*sint)[size] = (*sint)[0];
//        (*cost)[size] = (*cost)[0];
//    }

    /**
     * Creates an array containing values for the plot of the perimeter of a circle
     * Borrowed from freeglut source code, used to draw hemispheres and cones
     * @param n the number of
     * @return a 2 by n array containing the sine and cosine values. array[0] is sine values
     * and array[1] is cosine values
     */
    public static double[][] fghCircleTable(int n){

        n = abs(n);
        if(n == 0){
            return new double[][] {{0},{1}};
        }else{
            double angle = 2*PI/n;
            double[][] d = new double[2][n];
            for(int i = 0; i < n; i++){
                d[0][i] = sin(i * angle);
                d[1][i] = cos(i * angle);
            }
            return d;
        }

    }

//    void glutOpenHemisphere (GLdouble radius, GLint slices, GLint stacks)
//    // Modified from freeglut's glutSolidSphere
//    {
//        int i, j;
//        double z0, z1, r0, r1, *sint1, *cost1, *sint2, *cost2;
//
//        fghCircleTable(&sint1, &cost1, -slices);
//        fghCircleTable(&sint2, &cost2, stacks*2);
//        z1 = cost2[(stacks>0)?1:0];
//        r1 = sint2[(stacks>0)?1:0];
//
//        // Middle stacks
//        for (i=1; i<stacks-1; i++) {
//            z0 = z1; z1 = cost2[i+1];
//            r0 = r1; r1 = sint2[i+1];
//            if ((z1 > 0) || (z0 > 0)) continue; // hemisphere
//            glBegin(GL_QUAD_STRIP);
//            for (j=0; j<=slices; j++) {
//                glNormal3d(cost1[j]*r1, sint1[j]*r1, z1);
//                glVertex3d(cost1[j]*r1*radius, sint1[j]*r1*radius, z1*radius);
//                glNormal3d(cost1[j]*r0, sint1[j]*r0, z0);
//                glVertex3d(cost1[j]*r0*radius, sint1[j]*r0*radius, z0*radius);
//            }
//            glEnd();
//        }
//
//        // Bottom cap
//        z0 = z1; r0 = r1;
//        glBegin(GL_TRIANGLE_FAN);
//        glNormal3d(0,0,-1);
//        glVertex3d(0,0,-radius);
//        for (j=0; j<=slices; j++) {
//            glNormal3d(cost1[j]*r0, sint1[j]*r0, z0);
//            glVertex3d(cost1[j]*r0*radius, sint1[j]*r0*radius, z0*radius);
//        }
//        glEnd();
//
//        free(sint1); free(cost1);
//        free(sint2); free(cost2);
//    }

    /**
     * Draws an open hemisphere
     * Modified from freeglut's glutSolidSphere
     * @param radius the radius
     * @param slices number of horizontal slices
     * @param stacks number of vertical stacks
     * @param drawable the GLAutoDrawable object
     */
    void glutOpenHemisphere(double radius, int slices, int stacks, GLAutoDrawable drawable){

        int i, j;
        double z0, z1, r0, r1;

        GL4bc gl = drawable.getGL().getGL4bc();

        double[][] slice = GLUTHelper.fghCircleTable(-slices);
        double[][] stack = GLUTHelper.fghCircleTable(stacks*2);

        z1 = stack[1][(stacks>0) ? 1 : 0];
        r1 = stack[0][(stacks>0) ? 1 : 0];

        //middle stacks
        for(i = 1; i < stacks - 1; i++){
            z0 = z1;
            z1 = stack[1][i+1];
            r0 = r1;
            r1 = stack[0][i+1];

            if ((z1 > 0) || (z0 > 0)) continue;

            gl.glBegin(GL2.GL_QUAD_STRIP);
            for(j = 0; j <= slices; j++){
                gl.glNormal3d(slice[1][j] * r1, slice[0][j] * r1, z1);
                gl.glNormal3d(slice[1][j] * r1 * radius, slice[0][j] * r1 * radius, z1 * radius);
                gl.glNormal3d(slice[1][j] * r0, slice[0][j] * r0, z0);
                gl.glNormal3d(slice[1][j] * r0 * radius, slice[0][j] * r0 * radius, z0 * radius);
            }
            gl.glEnd();
        }

        //bottom cap
        z0 = z1;
        r0 = r1;
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glNormal3d(0,0,-1);
        gl.glVertex3d(0,0,-radius);
        for (j=0; j<=slices; j++) {
            gl.glNormal3d(slice[1][j] * r0, slice[0][j] * r0, z0);
            gl.glVertex3d(slice[1][j] * r0 * radius, slice[0][j] * r0 * radius, z0 * radius);
        }
        gl.glEnd();
    }

//    void glutMottledSphere (GLdouble radius, GLint slices, GLint stacks)
//    // Modified from freeglut's glutSolidSphere, we use this to draw a mottled sphere by modulating
//    // the vertex colours.
//    {
//        int i, j;
//        unsigned short rtmp = 0;
//        double z0, z1, r0, r1, *sint1, *cost1, *sint2, *cost2;
//        double *rnd1, *rnd2, *new_r, *old_r, *tmp;
//        double mottle = 0.2;
//
//        fghCircleTable(&sint1, &cost1, -slices);
//        fghCircleTable(&sint2, &cost2, stacks*2);
//        rnd1 = (double*) calloc(sizeof(double), slices+1);
//        rnd2 = (double*) calloc(sizeof(double), slices+1);
//        z0 = 1.0; z1 = cost2[(stacks>0)?1:0];
//        r0 = 0.0; r1 = sint2[(stacks>0)?1:0];
//
//        // Top cap
//        glBegin(GL_TRIANGLE_FAN);
//        glNormal3d(0,0,1);
//        glColor3f(0.63, 0.33, 0.22);
//        glVertex3d(0,0,radius);
//        new_r = rnd1;
//        for (j=slices; j>=0; j--) {
//            glNormal3d(cost1[j]*r1, sint1[j]*r1, z1);
//            if (j) {
//                new_r[j] = (1.0-mottle) + mottle*randtab[rtmp];
//                rtmp = (rtmp+1)%N_RAND;
//            } else new_r[j] = new_r[slices];
//            glColor3f(new_r[j]*0.63, new_r[j]*0.33, new_r[j]*0.22);
//            glVertex3d(cost1[j]*r1*radius, sint1[j]*r1*radius, z1*radius);
//        }
//        glEnd();
//
//        // Middle stacks
//        old_r = rnd1; new_r = rnd2;
//        for (i=1; i<stacks-1; i++) {
//            z0 = z1; z1 = cost2[i+1];
//            r0 = r1; r1 = sint2[i+1];
//            glBegin(GL_QUAD_STRIP);
//            for (j=0; j<=slices; j++) {
//                glNormal3d(cost1[j]*r1, sint1[j]*r1, z1);
//                if (j != slices) {
//                    new_r[j] = (1.0-mottle) + mottle*randtab[rtmp];
//                    rtmp = (rtmp+1)%N_RAND;
//                } else new_r[j] = new_r[0];
//                glColor3f(new_r[j]*0.63, new_r[j]*0.33, new_r[j]*0.22);
//                glVertex3d(cost1[j]*r1*radius, sint1[j]*r1*radius, z1*radius);
//                glNormal3d(cost1[j]*r0, sint1[j]*r0, z0);
//                glColor3f(old_r[j]*0.63, old_r[j]*0.33, old_r[j]*0.22);
//                glVertex3d(cost1[j]*r0*radius, sint1[j]*r0*radius, z0*radius);
//            }
//            tmp = old_r; old_r = new_r; new_r = tmp;
//            glEnd();
//        }
//
//        // Bottom cap
//        z0 = z1; r0 = r1;
//        glBegin(GL_TRIANGLE_FAN);
//        glNormal3d(0,0,-1);
//        glColor3f(0.63, 0.33, 0.22);
//        glVertex3d(0,0,-radius);
//        for (j=0; j<=slices; j++) {
//            glNormal3d(cost1[j]*r0, sint1[j]*r0, z0);
//            glColor3f(old_r[j]*0.63, old_r[j]*0.33, old_r[j]*0.22);
//            glVertex3d(cost1[j]*r0*radius, sint1[j]*r0*radius, z0*radius);
//        }
//        glEnd();
//
//        free(rnd1); free(rnd2);
//        free(sint1); free(cost1);
//        free(sint2); free(cost2);
//    }

    /**
     * Draws a mottled sphere
     * Modified from freeglut's glutSolidSphere, we use this to draw a mottled sphere by modulating
     * vertex colours
     * @param radius the radius
     * @param slices number of vertical slices
     * @param stacks nuber of horizontal stack
     * @param drawable the GLAutoDrawable object
     */
    public static void glutMottledSphere(double radius, int slices, int stacks, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();

        int i, j;
        double z0, z1, r0, r1;
        double[] new_r, old_r;
        double mottle = 0.2;

        double[][] slice = GLUTHelper.fghCircleTable(-slices);
        double[][] stack = GLUTHelper.fghCircleTable(stacks*2);

        Random rand=new Random();

        double[] rnd1 = new double[slices+1];
        for(i=0; i<5; i++ ){
            rnd1[i]= rand.nextDouble();
        }

        z1 = stack[1][(stacks>0) ? 1 : 0];
        r1 = stack[0][(stacks>0) ? 1 : 0];

        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3d(0,0,1);
        gl.glColor3f(0.63f, 0.33f, 0.22f);
        gl.glVertex3d(0,0,radius);
        new_r = rnd1;
        for (j=slices; j>=0; j--) {
            gl.glNormal3d(slice[1][j] * r1, slice[0][j] * r1, z1);
            if(j !=0) {
                new_r[j] = (1.0 - mottle) + mottle * rnd1[j];
            } else new_r[j] = new_r[slices];
            gl.glColor3f( (float) new_r[j]*0.63f, (float) new_r[j]*0.33f, (float) new_r[j]*0.22f);
            gl.glVertex3d(slice[1][j]*r1*radius, slice[0][j]*r1*radius, z1*radius);
        }
        gl.glEnd();

        for (i=1; i<stacks-1; i++) {
            old_r = new_r;
            for(i=0; i<5; i++ ){
                rnd1[i]= rand.nextDouble();
            }
            new_r = rnd1;

            z0 = z1;
            z1 = stack[1][i+1];
            r0 = r1;
            r1 = stack[0][i+1];
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (j=0; j<=slices; j++) {
                gl.glNormal3d(slice[1][j]*r1, slice[0][j]*r1, z1);
                if (j != slices) {
                    new_r[j] = (1.0 - mottle) + mottle * rnd1[j];
                } else new_r[j] = new_r[0];
                gl.glColor3f( (float) new_r[j]*0.63f, (float) new_r[j]*0.33f, (float) new_r[j]*0.22f);
                gl.glVertex3d(slice[1][j]*r1*radius, slice[0][j]*r1*radius, z1*radius);
                gl.glNormal3d(slice[1][j]*r0, slice[0][j]*r0, z0);
                gl.glColor3f( (float) old_r[j]*0.63f, (float) old_r[j]*0.33f, (float) old_r[j]*0.22f);
                gl.glVertex3d(slice[1][j]*r0*radius, slice[0][j]*r0*radius, z0*radius);
            }
            gl.glEnd();
        }

        old_r = new_r;

        // Bottom cap
        z0 = z1; r0 = r1;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glNormal3d(0,0,-1);
        gl.glColor3f(0.63f, 0.33f, 0.22f);
        gl.glVertex3d(0,0,-radius);
        for (j=0; j<=slices; j++) {
            gl.glNormal3d(slice[1][j]*r0, slice[0][j]*r0, z0);
            gl.glColor3f( (float) old_r[j]*0.63f, (float) old_r[j]*0.33f, (float) old_r[j]*0.22f);
            gl.glVertex3d(slice[1][j] * r0 * radius, slice[0][j]*r0*radius, z0*radius);
        }
        gl.glEnd();

    }

//    void glutCone (GLdouble base, GLdouble height, GLint slices, GLint stacks, bool closed)
//    // Modified from freeglut's glutSolidCone, we need this (a) to draw cones without bases and
//    // (b) to draw cones with bases, which glutSolidCone does not do correctly under Windows,
//    // for some reason.
//    {
//        int i, j;
//        double z0, z1, r0, r1, *sint, *cost;
//        const double zStep = height / ( ( stacks > 0 ) ? stacks : 1 );
//        const double rStep = base / ( ( stacks > 0 ) ? stacks : 1 );
//        const double cosn = ( height / sqrt ( height * height + base * base ));
//        const double sinn = ( base   / sqrt ( height * height + base * base ));
//
//        fghCircleTable(&sint, &cost, -slices);
//        z0 = 0.0; z1 = zStep;
//        r0 = base; r1 = r0 - rStep;
//
//        if (closed) {
//            glBegin(GL_TRIANGLE_FAN);
//            glNormal3d(0.0, 0.0, -1.0);
//            glVertex3d(0.0, 0.0, z0);
//            for (j=0; j<=slices; j++) glVertex3d(cost[j]*r0, sint[j]*r0, z0);
//            glEnd();
//        }
//
//        for (i=0; i<stacks-1; i++) {
//            glBegin(GL_QUAD_STRIP);
//            for (j=0; j<=slices; j++) {
//                glNormal3d(cost[j]*sinn, sint[j]*sinn, cosn);
//                glVertex3d(cost[j]*r0, sint[j]*r0, z0);
//                glVertex3d(cost[j]*r1, sint[j]*r1, z1);
//            }
//            z0 = z1; z1 += zStep;
//            r0 = r1; r1 -= rStep;
//            glEnd();
//        }
//
//        glBegin(GL_TRIANGLES);
//        glNormal3d(cost[0]*sinn, sint[0]*sinn, cosn);
//        for (j=0; j<slices; j++) {
//            glVertex3d(cost[j+0]*r0, sint[j+0]*r0, z0);
//            glVertex3d(0.0, 0.0, height);
//            glNormal3d(cost[j+1]*sinn, sint[j+1]*sinn, cosn);
//            glVertex3d(cost[j+1]*r0, sint[j+1]*r0, z0);
//        }
//        glEnd();
//
//        free(sint); free(cost);
//    }

    /**
     * Draws a cone with the specified parameters.
     * Modified from freeglut's glutSolidCone, we need this (a) to draw cones without bases and
     * (b) to draw cones with bases, which glutSolidCone does not do correctly under Windows,
     * for some reason.
     * @param base the base radius
     * @param height the cone height
     * @param slices number of vertical slices
     * @param stacks number of horizontal stacks
     * @param closed draw bottom or not
     * @param drawable the GLAutoDrawable object
     */
    public static void glutCone(double base, double height, int slices, int stacks, boolean closed, GLAutoDrawable drawable){
        GL4bc gl = drawable.getGL().getGL4bc();

        int i, j;
        double z0, z1, r0, r1;
        double zStep = height / ( ( stacks > 0 ) ? stacks : 1 );
        double rStep = base / ( ( stacks > 0 ) ? stacks : 1 );
        double cosn = ( height / sqrt ( height * height + base * base ));
        double sinn = ( base   / sqrt ( height * height + base * base ));
        double[][] slice = GLUTHelper.fghCircleTable(-slices);

        z0 = 0.0;
        z1 = zStep;
        r0 = base;
        r1 = r0 - rStep;

        if (closed) {
            gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glNormal3d(0.0, 0.0, -1.0);
            gl.glVertex3d(0.0, 0.0, z0);
            for (j=0; j<=slices; j++) gl.glVertex3d(slice[1][j] * r0, slice[0][j] * r0, z0);
            gl.glEnd();
        }

        for (i=0; i<stacks-1; i++) {
            gl.glBegin(GL2.GL_QUAD_STRIP);
            for (j=0; j<=slices; j++) {
                gl.glNormal3d(slice[1][j] * sinn, slice[0][j] * sinn, cosn);
                gl.glVertex3d(slice[1][j] * r0, slice[0][j] * r0, z0);
                gl.glVertex3d(slice[1][j] * r1, slice[0][j] * r1, z1);
            }
            z0 = z1; z1 += zStep;
            r0 = r1; r1 -= rStep;
            gl.glEnd();
        }

        gl.glBegin(GL.GL_TRIANGLES);
        gl.glNormal3d(slice[1][0]*sinn, slice[0][0]*sinn, cosn);
        for (j=0; j<slices; j++) {
            gl.glVertex3d(slice[1][j]*r0, slice[0][j]*r0, z0);
            gl.glVertex3d(0.0, 0.0, height);
            gl.glNormal3d(slice[1][j+1]*sinn, slice[0][j+1]*sinn, cosn);
            gl.glVertex3d(slice[1][j+1]*r0, slice[0][j+1]*r0, z0);
        }
        gl.glEnd();
    }

}
