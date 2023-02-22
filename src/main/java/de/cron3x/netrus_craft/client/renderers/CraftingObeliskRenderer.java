package de.cron3x.netrus_craft.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.*;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingAltarBlockEntity;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingObeliskBlockEntity;
import de.cron3x.netrus_craft.common.blocks.states.Blockstates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import static de.cron3x.netrus_craft.settings.Settings.CRAFTINGOBELISK_SPEED;
import static de.cron3x.netrus_craft.settings.Settings.CRAFTINGOBELISK_ANIMATION;

public class CraftingObeliskRenderer implements BlockEntityRenderer<CraftingObeliskBlockEntity> {
    private float angle = 0f;
    public CraftingObeliskRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CraftingObeliskBlockEntity obelisk, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        if (Minecraft.getInstance().isPaused()) return;
        if (obelisk.isRemoved()) return;

        if (obelisk.getAltarPos() == null) return;

        if (Minecraft.getInstance().level.getBlockState(obelisk.getAltarPos()).hasBlockEntity())
        {
            if (Minecraft.getInstance().level.getBlockEntity(obelisk.getAltarPos()) instanceof CraftingAltarBlockEntity altar){
                if (!altar.getBlockState().getValue(Blockstates.ACTIVE)) {
                    obelisk.setAltarPos(null);
                    return;
                }
                if (angle == 3.125f) angle = 0;
                final Minecraft mc = Minecraft.getInstance();

                BlockState state;
                //TODO: ADD custom ORB!

                if (CraftingAltarBlockEntity.getIsDay(altar)) {
                    state = Blocks.OCHRE_FROGLIGHT.defaultBlockState();
                } else {
                    state = Blocks.PEARLESCENT_FROGLIGHT.defaultBlockState();
                    light = 1500;
                }

                poseStack.translate(0.5, 2.4,0.5);
                poseStack.scale(.6f,.6f,.6f);

                poseStack.mulPose(Axis.XN.rotationDegrees(40));
                poseStack.mulPose(Axis.ZP.rotationDegrees(40));

                if (CRAFTINGOBELISK_ANIMATION) {
                    //poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                    angle += CRAFTINGOBELISK_SPEED;
                }
                poseStack.pushPose();
                mc.getBlockRenderer().renderSingleBlock(state, poseStack, buffers, light, overlay, ModelData.EMPTY, RenderType.solid()); //COOL Effect RenderType.endGateway() (dimension rift?)

                poseStack.popPose();
            }
        }
    }
}
