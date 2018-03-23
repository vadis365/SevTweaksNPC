package sev_tweaks_npc.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sev_tweaks_npc.entities.EntityPuke;

@SideOnly(Side.CLIENT)
public class RenderPuke extends Render<EntityPuke> {

	public RenderPuke(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public void doRender(EntityPuke entity, double x, double y, double z, float yaw, float tick) {
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPuke entity) {
		return null;
	}
}

