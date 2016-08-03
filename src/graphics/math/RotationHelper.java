package graphics.math;

import graphics.math.Matrix.GLRotationMatrix4;
import graphics.math.Quaternion.Quaternion;
import graphics.math.Vector3.Vector3;
import static java.lang.Math.*;

/**
 * Created by Michael Hutchinson on 01/08/2016 at 22:33 for LanderJavaPort in graphics.math.
 */
public class RotationHelper {

    /**
     * Converts a 3 dimensional Euler rotation vector (in degrees) into an OpenGL 4x4 rotation vector
     * @param angle the Euler vector to convert (in degrees)
     * @return the corresponding 4x4 OpenGL rotation matrix
     */
    public static GLRotationMatrix4 xyzEulerToMatrix(Vector3 angle){
        double sin_a, sin_b, sin_g, cos_a, cos_b, cos_g;
        double ra, rb, rg;

        // convert degrees to radians
        ra = angle.x * PI / 180;
        rb = angle.y * PI / 180;
        rg = angle.z * PI / 180;

        // pre calculate values
        cos_a = cos(ra);
        cos_b = cos(rb);
        cos_g = cos(rg);
        sin_a = sin(ra);
        sin_b = sin(rb);
        sin_g = sin(rg);

        //build matrix
        return new GLRotationMatrix4(cos_a * cos_b,                         sin_a * cos_b,                         -sin_b,        0,
                           cos_a * sin_b * sin_g - sin_a * cos_g, sin_a * sin_b * sin_g + cos_a * cos_g, cos_b * sin_g, 0,
                           cos_a * sin_b * cos_g + sin_a * sin_g, sin_a * sin_b * cos_g - cos_a * sin_g, cos_b * cos_g, 0,
                           0                                     ,0                                     ,0            , 1.0);
    }

    /**
     * Converts an OpenGL 4x4 rotation matrix into a Euler rotation vector (in degrees)
     * @param matrix the OpenGL 4x4 rotation matrix to convert
     * @return the corresponding Euler rotation vector (in degrees)
     */
    public static Vector3 matrixToXyzEuler(GLRotationMatrix4 matrix){
        double[][] m = matrix.getMatrix();
        double tmp;
        Vector3 ang = new Vector3();

        // Catch degenerate elevation cases
        if (m[0][2] < -0.99999999) {
            ang.y = 90.0;
            ang.x = 0.0;
            ang.z = acos(m[2][0]);
            if ( (sin(ang.z)>0.0) ^ (m[1][0]>0.0) ) ang.z = -ang.z;
            ang.z *= 180.0/PI;
            return ang;
        }
        if (m[0][2] > 0.99999999) {
            ang.y = -90.0;
            ang.x = 0.0;
            ang.z = acos(m[1][1]);
            if ( (sin(ang.z)<0.0) ^ (m[1][0]>0.0) ) ang.z = -ang.z;
            ang.z *= 180.0/PI;
            return ang;
        }

        // Non-degenerate elevation - between -90 and +90
        ang.y = asin(-m[0][2]);

        // Now work out azimuth - between -180 and +180
        tmp = m[0][0]/cos(ang.y); // the denominator will not be zero
        if ( tmp <= -1.0 ) ang.x = PI;
        else if ( tmp >= 1.0 ) ang.x = 0.0;
        else ang.x = acos( tmp );
        if ( ((sin(ang.x) * cos(ang.y))>0.0) ^ ((m[0][1])>0.0) ) ang.x = -ang.x;

        // Now work out roll - between -180 and +180
        tmp = m[2][2]/cos(ang.y); // the denominator will not be zero
        if ( tmp <= -1.0 ) ang.z = PI;
        else if ( tmp >= 1.0 ) ang.z = 0.0;
        else ang.z = acos( tmp );
        if ( ((sin(ang.z) * cos(ang.y))>0.0) ^ ((m[1][2])>0.0) ) ang.z = -ang.z;

        // Convert to degrees
        ang.y *= 180.0/PI;
        ang.x *= 180.0/PI;
        ang.z *= 180.0/PI;

        return ang;
    }

    /**
     * Given an axis and an angle, returns the corresponding quaternion
     * @param v the axis
     * @param phi the angle
     * @return the corresponding quaternion
     */
    public static Quaternion axisToQuaternion(Vector3 v, double phi){
        return new Quaternion(v.normalise().scalarMultiply(sin(phi/2)), cos(phi/2));
    }

    /**
     * Converts a quaternion to a OpenGL rotation matrix
     * @param q the quaternion to convert
     * @return the corresponding OpenGL rotation matrix
     */
    public static GLRotationMatrix4 quaternionToMatrix(Quaternion q){
        return new GLRotationMatrix4(1.0 - 2.0 * (q.v.y * q.v.y + q.v.z * q.v.z), 2.0 * (q.v.x * q.v.y - q.v.z * q.s)        , 2.0 * (q.v.z * q.v.x + q.v.y * q.s)        , 0.0,
                                     2.0 * (q.v.x * q.v.y + q.v.z * q.s)        , 1.0 - 2.0 * (q.v.z * q.v.z + q.v.x * q.v.x), 2.0 * (q.v.y * q.v.z - q.v.x * q.s)        , 0.0,
                                     2.0 * (q.v.z * q.v.x - q.v.y * q.s)        , 2.0 * (q.v.y * q.v.z + q.v.x * q.s)        , 1.0 - 2.0 * (q.v.y * q.v.y + q.v.x * q.v.x), 0.0,
                                     0.0                                        , 0.0                                        , 0.0                                        , 0.0);

    }

}
