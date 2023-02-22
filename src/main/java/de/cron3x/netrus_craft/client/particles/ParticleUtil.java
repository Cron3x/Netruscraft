package de.cron3x.netrus_craft.client.particles;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class ParticleUtil {
    public static Random r = new Random();

    public static double inRange(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static ParticleColor.IntWrapper defaultParticleColorWrapper() {
        return new ParticleColor.IntWrapper(255, 25, 180);
    }
}
