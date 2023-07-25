package de.cron3x.netruscraft.common.blocks.crafting_altar_handler;

import de.cron3x.netruscraft.client.particles.GlowParticleData;
import de.cron3x.netruscraft.client.particles.ParticleColor;
import de.cron3x.netruscraft.client.particles.ParticleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class CraftingAltarParticleHandler {
    private static final Level level = Minecraft.getInstance().level;

    //WTF What Is This
    /*public static void craftingParticles(BlockPos altarPos) {
        BlockPos obeliskXPZP = new BlockPos(altarPos.getX()+4, altarPos.getY()+1, altarPos.getZ()+4);
        BlockPos obeliskXPZN = new BlockPos(altarPos.getX()+4, altarPos.getY()+1, altarPos.getZ()-4);
        BlockPos obeliskXNZP = new BlockPos(altarPos.getX()-4, altarPos.getY()+1, altarPos.getZ()+4);
        BlockPos obeliskXNZN = new BlockPos(altarPos.getX()-4, altarPos.getY()+1, altarPos.getZ()-4);

        BlockPos pedestalXP = new BlockPos(altarPos.getX()+3, altarPos.getY()+0.5, altarPos.getZ());
        BlockPos pedestalXN = new BlockPos(altarPos.getX()-3, altarPos.getY()+0.5, altarPos.getZ());
        BlockPos pedestalZP = new BlockPos(altarPos.getX(), altarPos.getY(), altarPos.getZ()+3);
        BlockPos pedestalZN = new BlockPos(altarPos.getX(), altarPos.getY(), altarPos.getZ()-3);
        BlockPos pedestalXPZN = new BlockPos(altarPos.getX()+2, altarPos.getY()+0.5, altarPos.getZ()-2);
        BlockPos pedestalXPZP = new BlockPos(altarPos.getX()+2, altarPos.getY()+0.5, altarPos.getZ()+2);
        BlockPos pedestalXNZP = new BlockPos(altarPos.getX()-2, altarPos.getY()+0.5, altarPos.getZ()+2);
        BlockPos pedestalXNZN = new BlockPos(altarPos.getX()-2, altarPos.getY()+0.5, altarPos.getZ()-2);
    }*/

    public static void particleCircle(ParticleOptions particleOptions, BlockPos origin, double radius){
        double curAngle = 0;
        double particleX;
        double particleZ;

        while (curAngle <= 360) {
            particleX = origin.getX() + radius * Math.cos(curAngle * Math.PI / 180);
            particleZ = origin.getZ() + radius * Math.sin(curAngle * Math.PI / 180);
            curAngle += 4;
            level.addParticle(particleOptions, particleX+0.5, origin.getY()+1.2, particleZ+0.5, 0, 0, 0);
        }
    }

    public static void validAltar(BlockPos pos) {
        level.addParticle(ParticleTypes.FLASH, pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, 0, 0, 0);
        level.addParticle(ParticleTypes.END_ROD, pos.getX(), pos.getY() + 3, pos.getZ(), 0, 0, 0);
        level.addParticle(ParticleTypes.END_ROD, pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1, 0, 0, 0);
        level.addParticle(ParticleTypes.END_ROD, pos.getX(), pos.getY() + 1, pos.getZ() - 1, 0, 0, 0);
        level.addParticle(ParticleTypes.END_ROD, pos.getX(), pos.getY() + 1, pos.getZ() + 1, 0, 0, 0);
    }

    public static void invalidAltar(BlockPos pos) {
        level.addParticle(new DustParticleOptions(new Vector3f(255, 0, 0), 255), pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, 1, 1, 1);
        level.addParticle(new DustParticleOptions(new Vector3f(0, 255, 0), 255), pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, 1, 1, 1);
    }

    public static void craftingFlameParticle(BlockPos pos, ParticleColor centerColor, ParticleColor outerColor, int intensity) {

        double xzOffset = 0.25;

        for (int i = 0; i < intensity; i++) {
            //TODO: add light to pillar tip
            //TODO: Implement altar like door

            level.addParticle(
                    GlowParticleData.createData(centerColor, 0.25f, 0.7f, 36),
                    pos.getX() + 0.5 + ParticleUtil.inRange(-xzOffset / 2, xzOffset / 2), pos.getY() + 3.5 + ParticleUtil.inRange(-0.05, 0.2), pos.getZ() + 0.5 + ParticleUtil.inRange(-xzOffset / 2, xzOffset / 2),
                    0, ParticleUtil.inRange(0.0, 0.05f), 0);
        }
        for (int i = 0; i < intensity; i++) {
            level.addParticle(
                    GlowParticleData.createData(outerColor, 0.25f, 0.7f, 36),
                    pos.getX() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset), pos.getY() + 3.5 + ParticleUtil.inRange(0, 0.7), pos.getZ() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
                    0, ParticleUtil.inRange(0.0, 0.05f), 0);
        }
    }
}
