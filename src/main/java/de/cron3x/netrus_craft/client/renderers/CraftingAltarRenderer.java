package de.cron3x.netrus_craft.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.cron3x.netrus_craft.common.blocks.entity.CraftingAltarBlockEntity;
import de.cron3x.netrus_craft.common.blocks.states.Blockstates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class CraftingAltarRenderer implements BlockEntityRenderer<CraftingAltarBlockEntity> {
    private float angle = 0f;

    public CraftingAltarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CraftingAltarBlockEntity altar, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        if (Minecraft.getInstance().isPaused()) return;
        if (!altar.getBlockState().getValue(Blockstates.ACTIVE)) return;
        if (angle == 3.125f) angle = 0;
        if (altar.isRemoved()) return;
        if (!altar.getShowItem()) return;
        final Minecraft mc = Minecraft.getInstance();

        poseStack.translate(.5, 1.625f,.5);
        poseStack.scale(1f,1f,1f);
        poseStack.pushPose();

        ItemStack item = altar.getDisplayItem(true);


        mc.getItemRenderer().renderStatic(item, ItemTransforms.TransformType.GROUND, 255, overlay, poseStack, buffers, 1);

        poseStack.popPose();
    }

}
