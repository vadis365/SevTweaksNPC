package sev_tweaks_npc;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import sev_tweaks_npc.entities.EntityGlen;
import sev_tweaks_npc.entities.EntityPuke;
import sev_tweaks_npc.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class SevTweaksNPC {

	@Instance(Reference.MOD_ID)
	public static SevTweaksNPC INSTANCE;

	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_COMMON)
	public static CommonProxy PROXY;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(getEntityResource("glen"), EntityGlen.class, "glen", 1, this, 120, 3, true, -310, -65179583);
		EntityRegistry.registerModEntity(getEntityResource("puke"), EntityPuke.class, "puke", 2, this, 120, 3, true);
		PROXY.registerRenderers();
	}

	private static ResourceLocation getEntityResource(String entityName) {
		return new ResourceLocation(Reference.MOD_NAME, entityName);
	}

	@EventHandler
	public void posInit(FMLPostInitializationEvent event) {
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
}