package de.cron3x.netrus_craft.common.items;

import de.cron3x.netrus_craft.client.animations.WoodenCatalystItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WoodenCatalystItem extends Item implements GeoItem {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WoodenCatalystItem(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WoodenCatalystItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new WoodenCatalystItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Controller", 2, animationState -> PlayState.STOP)
                .triggerableAnim("use", DefaultAnimations.ITEM_ON_USE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel serverLevel) {
            triggerAnim(pPlayer, GeoItem.getOrAssignId(pPlayer.getItemInHand(pUsedHand), serverLevel), "Controller", "use");
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
