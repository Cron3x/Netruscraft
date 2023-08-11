package de.cron3x.netruscraft.common.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY = "key.category.netruscraft.keybinds";

    public static final KeyMapping KEY_SWITCH_SPELL = new KeyMapping("key.etruscraft.switch_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY);

}
