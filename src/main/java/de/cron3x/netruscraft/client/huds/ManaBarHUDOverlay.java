package de.cron3x.netruscraft.client.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import de.cron3x.netruscraft.Netruscraft;
import de.cron3x.netruscraft.common.items.CatalystItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaBarHUDOverlay {
    private static final ResourceLocation HUD_TEXTURE_ATLAS = new ResourceLocation(Netruscraft.MODID, "textures/gui/hud.png");
    public static final IGuiOverlay HUD_SPELL_SELECTION = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        if (player.getMainHandItem().getItem() instanceof CatalystItem || player.getOffhandItem().getItem() instanceof CatalystItem) {
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            RenderSystem.setShaderTexture(0, HUD_TEXTURE_ATLAS);
            guiGraphics.blit(HUD_TEXTURE_ATLAS, screenWidth-23, screenHeight-22, 0, 0,22,22);

            int itemX = screenWidth  - 20;
            int itemY = screenHeight - 19;

            if (player.getMainHandItem().getItem() instanceof CatalystItem item) {
                ItemStack[] spells = CatalystItem.getSpells(player.getMainHandItem()).toArray(ItemStack[]::new);
                if (spells.length > 0) {
                    try {
                        guiGraphics.renderItem(spells[SelectedSpellData.get()],
                                itemX,
                                itemY
                        );
                    } catch (Exception e) { System.out.printf("%s", e); }
                }
            } else {
                ItemStack[] spells = CatalystItem.getSpells(player.getOffhandItem()).toArray(ItemStack[]::new);
                if (spells.length > 0) {
                    guiGraphics.renderItem(spells[SelectedSpellData.get()],
                            itemX,
                            itemY
                    );
                }
            }
        }
    });
}
