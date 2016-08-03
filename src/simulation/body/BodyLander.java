package simulation.body;

import graphics.math.Vector3.Vector3;

/**
 * Created by Michael Hutchinson on 31/07/2016 at 21:55 for LanderJavaPort in simulation.body.
 */
public class BodyLander extends AbstractBody {
    public BodyLander(Vector3 position, Vector3 lastPosition, Vector3 rotation, double mass, boolean isGravityAffected) {
        super(position, lastPosition, rotation, mass, isGravityAffected);
    }
}
