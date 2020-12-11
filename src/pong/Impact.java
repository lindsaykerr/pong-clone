package pong;

/**
 * Implemented by class whose instances may come into collision with other objects.
 *
 * These objects must provide their position in the form of XY coordinates, so that
 * a collision can be tested with an implementation of the {@code}testImpact(){@code}
 * method to see if one has occurred.
 */
public interface Impact {
    /**
     * Uses the XY coordinates of another object to determine whether an impact
     * has taken place.
     *
     * @param x coordinate of another object
     * @param y coordinate of another object
     * @return should return an instance of the class that implement Impact Interface i.e. this
     */
    Impact testImpact(double x, double y);

    /**
     * Set the normal angle of the face being affected
     * @param normal 0 - 359 degrees
     */
    void setNormal(int normal);

    /**
     * Get the normal angle of the face being affected.
     * @return 0- 359 degrees
     */
    int getNormal();
}
