package de.cron3x.netrus_craft.client.animations;

import de.cron3x.netrus_craft.common.items.TestItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class TestItemRenderer extends GeoItemRenderer<TestItem> {
    public TestItemRenderer() {
        super(new TestItemModel());
    }
}
