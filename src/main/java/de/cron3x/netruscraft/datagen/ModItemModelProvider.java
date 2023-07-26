package de.cron3x.netruscraft.datagen;

import de.cron3x.netruscraft.NetrusCraft;
import de.cron3x.netruscraft.common.items.ItemRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ItemRegister.CATALYST_ADVANCE_RUNE_SIZE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return  withExistingParent(
                item.getId().getPath(),
                new ResourceLocation("minecraft:item/generated")
        ).texture(
                "layer0",
                new ResourceLocation(
                        NetrusCraft.MODID,
                        "netruscraft:item/" + item.getId().getPath()
                )
        );
    }
}
