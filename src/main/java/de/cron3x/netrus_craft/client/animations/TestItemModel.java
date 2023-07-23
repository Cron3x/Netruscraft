package de.cron3x.netrus_craft.client.animations;

import de.cron3x.netrus_craft.NetrusCraft;
import de.cron3x.netrus_craft.common.items.TestItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TestItemModel extends GeoModel<TestItem> {
    @Override
    public ResourceLocation getModelResource(TestItem animatable) {
        return new ResourceLocation(NetrusCraft.MODID, "geo/test_item.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TestItem animatable) {
        return new ResourceLocation(NetrusCraft.MODID, "textures/item/test_item.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TestItem animatable) {
        return new ResourceLocation(NetrusCraft.MODID, "animations/item/test_item.animation.json");
    }
}
