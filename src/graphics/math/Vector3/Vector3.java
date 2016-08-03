package graphics.math.Vector3;

import com.sun.istack.internal.NotNull;

import static java.lang.Math.*;

/**
 * Created by Michael Hutchinson on 31/07/2016 at 21:08 for LanderJavaPort in simulation.body.
 *
 * Class for dealing with 3 dimensional vectors
 */
public class Vector3 {

    public double x, y, z;

    /**
     *
     * @param x x
     * @param y y
     * @param z z
     */
    public Vector3(@NotNull double x, @NotNull double y, @NotNull double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * creates a new Vector3 with {0, 0, 0} as the values
     */
    public Vector3(){
        new Vector3(0, 0 ,0);
    }

    /**
     * Checks if two Vector3 are equal
     * @param v the Vector3 to check against
     * @return true if equal, false otherwise
     */
    public boolean equals(@NotNull Vector3 v){
        return (x == v.x && y == v.y && z == v.z);
    }

    /**
     * Adds two Vector3 together
     * @param v the Vector3 to add to this
     * @return the sum of the two Vector3
     */
    public Vector3 add(@NotNull Vector3 v){
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Subtracts a Vector3 from this
     * @param v the Vector3 to subtract
     * @return this minus v
     */
    public Vector3 minus(@NotNull Vector3 v){
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Normalises the Vector3 to have unit magnitude
     * @return this normalised
     */
    public Vector3 normalise(){
        double s = abs();
        return new Vector3(x / s, y / s, z / s);
    }

    /**
     * Multiplies this by a scalar
     * @param a the scalar to multiply by
     * @return the multiplied Vector3
     */
    public Vector3 scalarMultiply(@NotNull double a){
        return new Vector3(x * a, y * a, z * a);
    }

    /**
     * Divides this by a scalar
     * @param a the scalar to divide by
     * @return the divided Vector3
     */
    public Vector3 scalarDivide(@NotNull double a){
        return new Vector3(x / a, y / a, z / a);
    }

    /**
     * Raises the elements of this to a power
     * @param a the power to raise by
     * @return this with elements raised to power a
     */
    public Vector3 scalarPower(@NotNull double a){
        return new Vector3(pow(x, a), pow(y, a), pow(z, a));
    }

    /**
     * @return the absolute value of this
     */
    public double abs(){
        return sqrt(abs2());
    }

    /**
     * @return the absolute value of this squared
     */
    public double abs2(){
        return x*x + y*y + z*z;
    }
}
