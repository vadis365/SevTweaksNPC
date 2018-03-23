package sev_tweaks_npc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModSounds {
	public static final List<SoundEvent> SOUNDS = new ArrayList<SoundEvent>();
	public static final SoundEvent LADDER_PLACEMENT = registerSoundResource("ladder_placement");
	public static final SoundEvent GLEN_BARF_1 = registerSoundResource("glen_barf_1");
	public static final SoundEvent GLEN_WHINGE = registerSoundResource("glen_whinge");
	public static final SoundEvent GLEN_HURT = registerSoundResource("glen_hurt");
	public static final SoundEvent GLEN_DEATH = registerSoundResource("glen_death");

	public static SoundEvent registerSoundResource(String name) {
		return new SoundEvent(new ResourceLocation(Reference.MOD_ID, name));
	}

	public static void init() {
		try {
			for (Field field : ModSounds.class.getDeclaredFields()) {
				Object obj = field.get(null);
				if (obj instanceof SoundEvent) {
					SoundEvent sound = (SoundEvent) obj;
					String name = field.getName().toLowerCase(Locale.ENGLISH);
					registerSoundName(name, sound);
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static void registerSoundName(String name, SoundEvent sound) {
		SOUNDS.add(sound);
		sound.setRegistryName(Reference.MOD_ID, name);
	}

	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class RegistrationHandlerSounds {
		@SubscribeEvent
		public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
			init();
			final IForgeRegistry<SoundEvent> registry = event.getRegistry();
			for (SoundEvent sounds : SOUNDS)
				registry.register(sounds);
		}
	}
}
