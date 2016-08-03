package graphics.math.Quaternion;

import graphics.math.Vector3.Vector3;

/**
 * Created by Michael Hutchinson on 01/08/2016 at 23:43 for LanderJavaPort in graphics.math.
 */
public class Quaternion {

    public Vector3 v;
    public double s;

    /**
     * returns a new empty Quaternion
     */
    public Quaternion(){
        this.v = new Vector3();
        this.s = 0;
    }

    public Quaternion(Vector3 v, double s){
        this.v = v;
        this.s = s;
    }

    /**
     * @return normalised quaternion
     * @throws IllegalArgumentException
     */
    public Quaternion normalise() throws IllegalArgumentException{
        double mag;
        mag = (this.v.x*this.v.x + this.v.y*this.v.y + this.v.z*this.v.z + this.s*this.s);
        if (mag > 0.0) {
            return new Quaternion(new Vector3(this.v.x / mag, this.v.y / mag, this.v.x / mag), this.s / mag);
        }else{
            throw new IllegalArgumentException("Quaternion magnitude is >= 0");
        }
    }

    /**
     * Finds the Hamilton product of the is and another quaternion
     * @param q the quaternion to find the Hamilton product with
     * @return the Hamilton product of the two quaternions
     */
    public Quaternion hamiltonProduct(Quaternion q){

        return new Quaternion(new Vector3(this.s * q.v.x + this.v.x * q.s + this.v.y * q.v.z - this.v.z * q.v.y,
                                          this.s * q.v.y - this.v.x * q.v.z + this.v.y * q.s + this.v.z * q.v.x,
                                          this.s * q.v.z + this.v.x * q.v.y - this.v.y * q.v.x + this.v.z * q.s),
                                          this.s * q.s - this.v.x * q.v.x - this.v.y * q.v.y - this.v.z * q.v.z);
    }

}
