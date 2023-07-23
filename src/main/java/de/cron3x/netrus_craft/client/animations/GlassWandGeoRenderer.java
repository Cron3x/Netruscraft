package de.cron3x.netrus_craft.client.animations;

import de.cron3x.netrus_craft.common.items.GlassWantItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GlassWandGeoRenderer extends GeoItemRenderer<GlassWantItem> {
    public GlassWandGeoRenderer(){
        super(new GlassWandGeoModel());
    }
}
