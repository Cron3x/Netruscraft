package de.cron3x.netruscraft.client.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import de.cron3x.netruscraft.NetrusCraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SelectedSpellHUDOverlay {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(NetrusCraft.MODID, "textures/gui/spell_indicator_background");

    public static final IGuiOverlay HUD_SPELL_SELECTION = (((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth/2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
    }));
}
