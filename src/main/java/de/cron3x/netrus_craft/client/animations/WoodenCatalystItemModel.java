package de.cron3x.netrus_craft.client.animations;

import de.cron3x.netrus_craft.NetrusCraft;
import de.cron3x.netrus_craft.common.items.TestItem;
import de.cron3x.netrus_craft.common.items.WoodenCatalystItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WoodenCatalystItemModel extends GeoModel<WoodenCatalystItem> {
    @Override
    public ResourceLocation getModelResource(WoodenCatalystItem animatable) {
        return new ResourceLocation(NetrusCraft.MODID, "geo/wooden_catalyst.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WoodenCatalystItem animatable) {
        return new ResourceLocation(NetrusCraft.MODID, "textures/item/wooden_catalyst.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WoodenCatalystItem animatable) {
        return new ResourceLocation(NetrusCraft.MODID, "animations/item/wooden_catalyst.animation.json");
    }
}
