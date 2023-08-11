package de.cron3x.netruscraft.client.animations;

import de.cron3x.netruscraft.Netruscraft;
import de.cron3x.netruscraft.common.items.WoodenCatalystItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WoodenCatalystItemModel extends GeoModel<WoodenCatalystItem> {
    @Override
    public ResourceLocation getModelResource(WoodenCatalystItem animatable) {
        return new ResourceLocation(Netruscraft.MODID, "geo/wooden_catalyst.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WoodenCatalystItem animatable) {
        return new ResourceLocation(Netruscraft.MODID, "textures/item/wooden_catalyst.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WoodenCatalystItem animatable) {
        return new ResourceLocation(Netruscraft.MODID, "animations/item/wooden_catalyst.animation.json");
    }
}
