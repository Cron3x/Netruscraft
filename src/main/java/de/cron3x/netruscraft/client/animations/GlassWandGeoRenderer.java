package de.cron3x.netruscraft.client.animations;

import de.cron3x.netruscraft.common.items.GlassWantItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GlassWandGeoRenderer extends GeoItemRenderer<GlassWantItem> {
    public GlassWandGeoRenderer(){
        super(new GlassWandGeoModel());
    }
}
