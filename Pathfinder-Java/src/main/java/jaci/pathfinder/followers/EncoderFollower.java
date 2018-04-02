package jaci.pathfinder.followers;

import jaci.pathfinder.Trajectory;

/**
 * The EncoderFollower is an object designed to follow a trajectory based on encoder input. This class can be used
 * for Tank or Swerve drive implementations.
 *
 * @author Jaci
 */
public class EncoderFollower extends DistanceFollower{

    private int encoder_offset, encoder_tick_count;
    private double wheel_circumference;

    public EncoderFollower(Trajectory traj) {
        super(traj);
    }

    public EncoderFollower() {
        super();
    }

    /**
     * Configure the Encoders being used in the follower.
     *
     * @param initial_position     The initial 'offset' of your encoder. This should be set to the encoder value just
     *                             before you start to track
     * @param ticks_per_revolution How many ticks per revolution the encoder has
     * @param wheel_diameter       The diameter of your wheels (or pulleys for track systems) in meters
     */
    public void configureEncoder(int initial_position, int ticks_per_revolution, double wheel_diameter) {
        encoder_offset = initial_position;
        encoder_tick_count = ticks_per_revolution;
        wheel_circumference = Math.PI * wheel_diameter;
    }

    /**
     * Calculate the desired output for the motors, based on the amount of ticks the encoder has gone through.
     * This does not account for heading of the robot. To account for heading, add some extra terms in your control
     * loop for realignment based on gyroscope input and the desired heading given by this object.
     *
     * @param encoder_tick The amount of ticks the encoder has currently measured.
     * @return The desired output for your motor controller
     */
    public double calculate(int encoder_tick) {
        // Number of Revolutions * Wheel Circumference
        double distance_covered = ((double) (encoder_tick - encoder_offset) / encoder_tick_count)
                * wheel_circumference;
        return super.calculate(distance_covered);
    }

}
