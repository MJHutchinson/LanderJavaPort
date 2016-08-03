package simulation.body;

import graphics.math.Vector3.Vector3;

/**
 * Created by Michael Hutchinson on 31/07/2016 at 21:05 for LanderJavaPort in simulation.body at 21:52 for LanderJavaPort in ${PACKAGE_NAME}.
 */
public abstract class AbstractBody {

    public Vector3 position, lastPosition, rotation;
    public double mass;
    public boolean isGravityAffected;

    public AbstractBody(Vector3 position, Vector3 lastPosition, Vector3 rotation, double mass, boolean isGravityAffected) {
        this.position = position;
        this.lastPosition = lastPosition;
        this.rotation = rotation;
        this.mass = mass;
        this.isGravityAffected = isGravityAffected;
    }
}
