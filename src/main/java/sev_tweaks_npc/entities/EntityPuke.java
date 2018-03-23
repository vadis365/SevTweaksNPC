package sev_tweaks_npc.entities;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sev_tweaks_npc.SevTweaksNPC;

public class EntityPuke extends EntityThrowable {

	private boolean playedSound = false;

	public EntityPuke(World world) {
		super(world);
		setSize(0.2F, 0.2F);
	}

	public EntityPuke(World world, EntityLiving entity) {
		super(world, entity);
	}

	public EntityPuke(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityPuke(World world, EntityPlayer player) {
		super(world, player);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (getEntityWorld().isRemote)
			trailParticles(getEntityWorld(), posX - 0.5D, posY, posZ - 0.5D, rand);
	}

	protected SoundEvent getSplashSound() {
		return SoundEvents.BLOCK_WATER_AMBIENT;
	}

	@Override
	protected void onImpact(RayTraceResult mop) {
		if (!getEntityWorld().isRemote) {
			if (mop.entityHit != null) {
				if (mop.typeOfHit != null && mop.typeOfHit == RayTraceResult.Type.BLOCK) {
					if (!playedSound) {
						getEntityWorld().playSound((EntityPlayer) null, getPosition(), getSplashSound(), SoundCategory.HOSTILE, 2.0F, 1.0F);
						playedSound = true;
					}
					setDead();
				}
			}
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, int amount) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void trailParticles(World world, double x, double y, double z, Random rand) {
		for (int count = 0; count < 20; ++count) {
			double velX = 0.0D;
			double velY = 0.0D;
			double velZ = 0.0D;
			int motionX = rand.nextInt(2) * 2 - 1;
			int motionZ = rand.nextInt(2) * 2 - 1;
			velY = (rand.nextFloat() - 0.5D) * 0.125D;
			velZ = rand.nextFloat() * 1.0F * motionZ;
			velX = rand.nextFloat() * 1.0F * motionX;
			SevTweaksNPC.PROXY.spawnCustomParticle("slime", getEntityWorld(), x + 0.5D, y, z + 0.5D, velX, velY, velZ);
		}
	}
}