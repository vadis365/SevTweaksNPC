package sev_tweaks_npc;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import sev_tweaks_npc.items.ItemLadderOfAscension;
	// My Generic Item Registry ;)
	public class ModItems {
		private static final List<Item> ITEMS = new LinkedList<Item>();
		// Items
		public static final Item GOLDEN_LADDER_OF_ASCENSION = new ItemLadderOfAscension();

		public static void init() {
			try {
				for (Field field : ModItems.class.getDeclaredFields()) {
					Object obj = field.get(null);
					if (obj instanceof Item) {
						Item item = (Item) obj;
						ITEMS.add(item);
						String name = field.getName().toLowerCase(Locale.ENGLISH);
						item.setRegistryName(Reference.MOD_ID, name).setUnlocalizedName(Reference.MOD_ID + "." + name);
					}
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
		public static class RegistrationHandlerBlocks {

			@SubscribeEvent
			public static void registerItems(final RegistryEvent.Register<Item> event) {
				init();
				final IForgeRegistry<Item> registry = event.getRegistry();
					for (Item item : ITEMS) {
					registry.register(item);
				}
			}

			@SideOnly(Side.CLIENT)
			@SubscribeEvent
			public static void registerModels(ModelRegistryEvent event) {
				for (Item item : ITEMS)
					if (item instanceof ISubItemsItem) {
						List<String> models = ((ISubItemsItem) item).getModels();
						for (int i = 0; i < models.size(); i++)
							ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(Reference.MOD_ID + ":" + models.get(i), "inventory"));
					}
					else {
						ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
					}
			}
		}

		public static interface ISubItemsItem {
			List<String> getModels();
		}
}
