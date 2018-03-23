package sev_tweaks_npc.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sev_tweaks_npc.entities.EntityGlen;

@SideOnly(Side.CLIENT)
public class RenderGlen extends RenderLiving<EntityGlen> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("sev_tweaks_npc:textures/entity/glen.png");

	public RenderGlen(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelBiped(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGlen entity) {
		return TEXTURE;
	}

}
