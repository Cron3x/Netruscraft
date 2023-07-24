package de.cron3x.netrus_craft.client.animations;

import de.cron3x.netrus_craft.common.items.WoodenCatalystItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WoodenCatalystItemRenderer extends GeoItemRenderer<WoodenCatalystItem> {
    public WoodenCatalystItemRenderer() {
        super(new WoodenCatalystItemModel());
    }
}
