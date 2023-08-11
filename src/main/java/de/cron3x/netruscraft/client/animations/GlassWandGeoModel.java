package de.cron3x.netruscraft.client.animations;

import de.cron3x.netruscraft.Netruscraft;
import de.cron3x.netruscraft.common.items.GlassWantItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GlassWandGeoModel extends GeoModel<GlassWantItem> {
    private final ResourceLocation modelResource = new ResourceLocation(Netruscraft.MODID, "geo/glass_wand.geo.json");
    private final ResourceLocation textureResource = new ResourceLocation(Netruscraft.MODID, "textures/item/glass_wand");
    private final ResourceLocation animationResource = new ResourceLocation(Netruscraft.MODID, "animations/item/glass_wand.animation.json");
    @Override
    public ResourceLocation getModelResource(GlassWantItem animatable) {
        return this.modelResource;
    }

    @Override
    public ResourceLocation getTextureResource(GlassWantItem animatable) {
        return this.textureResource;
    }

    @Override
    public ResourceLocation getAnimationResource(GlassWantItem animatable) {
        return this.animationResource;
    }
}
