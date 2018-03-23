package sev_tweaks_npc.entities;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sev_tweaks_npc.ModSounds;
import sev_tweaks_npc.SevTweaksNPC;

public class EntityGlen extends EntityCreature {
	public EntityGlen(World worldIn) {
		super(worldIn);
		setSize(0.6F, 1.95F);
	}

	@Override
	protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityMob.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(3, new EntityAIMoveIndoors(this));
        this.tasks.addTask(4, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(5, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (getEntityWorld().isRemote && getEntityWorld().getTotalWorldTime()%10 == 0)
			smokeParticles(getEntityWorld(), posX, posY + 2.5D, posZ, rand);

		if (!getEntityWorld().isRemote) {
			double direction = Math.toRadians(this.getRotationYawHead());
			getLookHelper().setLookPosition(posX + -Math.sin(direction) * 0.5F, posY - 2F, posZ + Math.cos(direction) * 0.5F, (float)getHorizontalFaceSpeed(), (float)getVerticalFaceSpeed());
			if(getEntityWorld().getTotalWorldTime()%300 == 0)
				puke();
		}
	}

	public void puke() {
		double direction = Math.toRadians(this.getRotationYawHead());
		getLookHelper().setLookPosition(posX + -Math.sin(direction) * 0.5F, posY - 2F, posZ + Math.cos(direction) * 0.5F, (float)getHorizontalFaceSpeed(), (float)getVerticalFaceSpeed());

		double targetX = posX + -Math.sin(direction) * 1.5F;
		double targetY = posY;
		double targetZ = posZ + Math.cos(direction) * 1.5F;
		getEntityWorld().playSound((EntityPlayer) null, getPosition(), ModSounds.GLEN_BARF_1, SoundCategory.HOSTILE, 1.0F, 1.0F);
		EntityPuke puke = new EntityPuke(getEntityWorld(), this);
		puke.setPosition(posX + -Math.sin(direction) * 0.5F, posY + getEyeHeight() - 0.3F, posZ + Math.cos(direction) * 0.5F);
		puke.shoot(targetX, targetY, targetZ, 0.05F, 0.0F);
		getEntityWorld().spawnEntity(puke);
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
	}

	@SideOnly(Side.CLIENT)
	public void smokeParticles(World world, double x, double y, double z, Random rand) {
		SevTweaksNPC.PROXY.spawnCustomParticle("puke", getEntityWorld(), x, y, z, 0F, 0F, 0F);
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere();
	}

	@Override
    protected boolean canDespawn() {
       return true;
    }

	@Override
	protected SoundEvent getAmbientSound() {
        return ModSounds.GLEN_WHINGE;
    }

	@Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.GLEN_HURT;
    }

	@Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GLEN_DEATH;
    }

	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		playSound(getStepSound(), 0.15F, 1.0F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
	}

	@Override
	public float getEyeHeight() {
		return 1.74F;
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		setCustomNameTag("Glen G.");
		setAlwaysRenderNameTag(true);
		return livingdata;
	}
}