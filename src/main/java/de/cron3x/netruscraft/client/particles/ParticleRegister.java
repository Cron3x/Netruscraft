package de.cron3x.netruscraft.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static de.cron3x.netruscraft.Netruscraft.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleRegister {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<ParticleType<ColorParticleTypeData>> GLOW_TYPE = PARTICLES.register(GlowParticleData.NAME, GlowParticleType::new);

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent evt) {
        evt.registerSpriteSet(GLOW_TYPE.get(), GlowParticleData::new);
        Minecraft.getInstance().particleEngine.register(GLOW_TYPE.get(), GlowParticleData::new);
    }


}
