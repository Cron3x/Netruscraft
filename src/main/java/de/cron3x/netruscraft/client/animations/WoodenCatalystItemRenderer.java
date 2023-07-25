package de.cron3x.netruscraft.client.animations;

import de.cron3x.netruscraft.common.items.WoodenCatalystItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WoodenCatalystItemRenderer extends GeoItemRenderer<WoodenCatalystItem> {
    public WoodenCatalystItemRenderer() {
        super(new WoodenCatalystItemModel());
    }
}
