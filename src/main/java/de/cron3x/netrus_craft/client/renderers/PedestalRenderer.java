package de.cron3x.netrus_craft.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cron3x.netrus_craft.common.blocks.entity.PedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;

import static de.cron3x.netrus_craft.settings.Settings.PEDESTAL_ANIMATION;
import static de.cron3x.netrus_craft.settings.Settings.PEDESTAL_SPEED;

public class PedestalRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    private float angle = 0f;
    private final BlockEntityRendererProvider.Context context;
    private static final ItemStack ITEM = new ItemStack(Items.ECHO_SHARD); //Get from inv
    public PedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(PedestalBlockEntity pedestal, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        if (Minecraft.getInstance().isPaused()) return;
        if (angle == 3.125f) angle = 0;
        if (pedestal.isRemoved()) return;
        final Minecraft mc = Minecraft.getInstance();
        final ItemStack item = pedestal.getDisplayItem(true);

        poseStack.translate(.5f, 1.15f,.5f);
        poseStack.scale(1,1,1);

        if (PEDESTAL_ANIMATION) {
            poseStack.mulPose(new Quaternionf(0, 0, 0, 1).rotateAxis(angle, 0, 1, 0));
            angle += PEDESTAL_SPEED;
        }
        poseStack.pushPose();

        mc.getItemRenderer().renderStatic(item, ItemTransforms.TransformType.GROUND, light, overlay, poseStack, buffers, 1);

        poseStack.popPose();
    }

}
