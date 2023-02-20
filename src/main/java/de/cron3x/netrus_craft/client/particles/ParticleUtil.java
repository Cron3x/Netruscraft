package de.cron3x.netrus_craft.client.particles;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class ParticleUtil {
    public static Random r = new Random();

    public static double inRange(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double getCenterOfBlock(double a) {
        return (a + .5);
    }

    // https://karthikkaranth.me/blog/generating-random-points-in-a-sphere/
    public static Vec3 pointInSphere() {
        double u = Math.random();
        double v = Math.random();
        double theta = u * 2.0 * Math.PI;
        double phi = Math.acos(2.0 * v - 1.0);
        double r = Math.cbrt(Math.random());
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        double sinPhi = Math.sin(phi);
        double cosPhi = Math.cos(phi);
        double x = r * sinPhi * cosTheta;
        double y = r * sinPhi * sinTheta;
        double z = r * cosPhi;
        return new Vec3(x, y, z);
    }

    public static void beam(BlockPos toThisBlock, BlockPos fromThisBlock, Level world) {

        double x2 = getCenterOfBlock(toThisBlock.getX());
        double z2 = getCenterOfBlock(toThisBlock.getZ());
        double y2 = getCenterOfBlock(toThisBlock.getY());
        double x1 = getCenterOfBlock(fromThisBlock.getX());
        double z1 = getCenterOfBlock(fromThisBlock.getZ());
        double y1 = getCenterOfBlock(fromThisBlock.getY());
        double d5 = 1.2;
        double d0 = x2 - x1;
        double d1 = y2 - y1;
        double d2 = z2 - z1;
        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d0 = d0 / d3;
        d1 = d1 / d3;
        d2 = d2 / d3;

        double d4 = r.nextDouble();

        while ((d4 + .65) < d3) {
            d4 += 1.8D - d5 + r.nextDouble() * (1.5D - d5);
            if (world.isClientSide)
                world.addParticle(ParticleTypes.ENCHANT, x1 + d0 * d4, y1 + d1 * d4, z1 + d2 * d4, 0.0D, 0.0D, 0.0D);
            if (world instanceof ServerLevel) {
                ((ServerLevel) world).sendParticles(ParticleTypes.WITCH, x1 + d0 * d4, y1 + d1 * d4, z1 + d2 * d4, r.nextInt(4), 0, 0.0, 0, 0.0);
            }
        }
    }

    public static ParticleColor.IntWrapper defaultParticleColorWrapper() {
        return new ParticleColor.IntWrapper(255, 25, 180);
    }


    /*public static void spawnParticleSphere(Level world, BlockPos pos) {
        if (!world.isClientSide)
            return;
        spawnParticleSphere(world, pos, ParticleColor.makeRandomColor(255, 255, 255, world.random));
    }

    public static void spawnParticleSphere(Level world, BlockPos pos, ParticleColor color) {
        if (!world.isClientSide)
            return;
        for (int i = 0; i < 5; i++) {
            Vec3 particlePos = new Vec3(pos.getX(), pos.getY(), pos.getZ()).add(0.5, 0, 0.5);
            particlePos = particlePos.add(ParticleUtil.pointInSphere());
            world.addParticle(ParticleLineData.createData(color),
                    particlePos.x(), particlePos.y(), particlePos.z(),
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        }
    }
    public static void spawnLight(Level world, ParticleColor color, Vec3 vec, int intensity) {
        for (int i = 0; i < intensity; i++) {
            world.addParticle(
                    GlowParticleData.createData(color),
                    vec.x() + ParticleUtil.inRange(-0.1, 0.1), vec.y() + ParticleUtil.inRange(-0.1, 0.1), vec.z() + ParticleUtil.inRange(-0.1, 0.1),
                    0, 0, 0);
        }
    }

    public static void spawnOrb(Level level, ParticleColor color, BlockPos pos, int lifetime) {
        if (level instanceof ServerLevel server) {
            for (int i = 0; i <= 10; i++)
                server.sendParticles(
                        GlowParticleData.createData(color, 0.4f, 0.5f, lifetime),
                        pos.getX() + ParticleUtil.inRange(0.3, 0.7), pos.getY() + ParticleUtil.inRange(-0.2, 0.2), pos.getZ() + ParticleUtil.inRange(0.3, 0.7), 1,
                        0d, 0d, 0, 0);
        }
    }*/
}
