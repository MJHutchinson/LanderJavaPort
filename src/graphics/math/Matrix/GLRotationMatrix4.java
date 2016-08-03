package graphics.math.Matrix;

/**
 * Created by Michael Hutchinson on 01/08/2016 at 22:19 for LanderJavaPort in graphics.math.
 *
 * Class for dealing with OpenGL 4x4 rotation matrices
 */
public class GLRotationMatrix4 {

    private double[][] matrix = new double[4][4];

    /**
     * A new Identity 4x4 matrix
     */
    public GLRotationMatrix4(){
        matrix = new double[][] {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
    }

    public GLRotationMatrix4(double[][] m){
        if(m.length == 4 && m[0].length == 4){
            matrix = m;
        }else{
            throw new IllegalArgumentException("Array of wrong dimensions. Must be of format 4x4");
        }
    }

    public GLRotationMatrix4(double m1, double m2, double m3, double m4, double m5, double m6, double m7, double m8, double m9, double m10, double m11, double m12, double m13, double m14, double m15, double m16){
        matrix = new double[][] {{m1, m2, m3, m4}, {m5, m6, m7, m8}, {m9, m10, m11, m12}, {m13, m14, m15, m16}};
    }

    /**
     * A simpler inversion for 4x4 OpenGL rotation matrices
     * @return this matrix inverted
     */
    public GLRotationMatrix4 invert() throws IllegalArgumentException{
        double zeroThree, oneThree, twoThree;

        // pre calculate values
        zeroThree = -matrix[3][0]*matrix[0][0] - matrix[3][1]*matrix[0][1] - matrix[3][2]*matrix[0][2];
        oneThree =  -matrix[3][0]*matrix[1][0] - matrix[3][1]*matrix[1][1] - matrix[3][2]*matrix[1][2];
        twoThree =  -matrix[3][0]*matrix[2][0] - matrix[3][1]*matrix[2][1] - matrix[3][2]*matrix[2][2];

        //build matrix
        return new GLRotationMatrix4(matrix[0][0], matrix[1][0], matrix[2][0], 0,
                           matrix[0][1], matrix[1][1], matrix[2][1], 0,
                           matrix[0][2], matrix[1][2], matrix[2][2], 0,
                           zeroThree,    oneThree,     twoThree,     1);
    }

    /**
     *
     * @return returns a double[4][4] of the matrix values
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     *
     * @param matrix set this.matrix values to matrix
     */
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public String toString() {
        return  matrix[0][0] + "," + matrix[0][1] + "," + matrix[0][2] + "," + matrix[0][3] + "," + "\n" +
                matrix[1][0] + "," + matrix[1][1] + "," + matrix[1][2] + "," + matrix[1][3] + "," + "\n" +
                matrix[2][0] + "," + matrix[2][1] + "," + matrix[2][2] + "," + matrix[2][3] + "," + "\n" +
                matrix[3][0] + "," + matrix[3][1] + "," + matrix[3][2] + "," + matrix[3][3] + "," + "\n";
    }
}
