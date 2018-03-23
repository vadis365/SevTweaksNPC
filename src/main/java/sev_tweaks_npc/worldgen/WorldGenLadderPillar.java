package sev_tweaks_npc.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import sev_tweaks_npc.entities.EntityGlen;

public class WorldGenLadderPillar extends WorldGenerator {
	private IBlockState DIRT = Blocks.DIRT.getDefaultState();
	private IBlockState GRASS = Blocks.GRASS.getDefaultState();;

	public WorldGenLadderPillar() {
		super(true);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int width = 2;
		int height = 100;

		for (int xx = -width; xx <= width; xx++) {
			for (int zz = -width; zz <= width; zz++) {
				for (int yy = 0; yy <= height; yy++) {
					IBlockState blockState = world.getBlockState(pos.add(xx, yy, zz));
					if(!world.canBlockSeeSky(pos.add(xx, yy, zz)))
						return false;
					if (blockState.getBlock() != Blocks.AIR) {
						return false;
					}
				}
			}
		}

		for (int x = -width; x <= width; x++)
			for (int z = -width; z <= width; z++)
				for (int y = 0; y <= height; y++)
					if (y <= height - 1)
						world.setBlockState(pos.add(x, y, z), DIRT, 2);
					else
						world.setBlockState(pos.add(x, y, z), GRASS, 2);

		EntityGlen glen = new EntityGlen(world);
		glen.setPosition(pos.getX(), pos.getY() + height + 1, pos.getZ());
		world.spawnEntity(glen);
		glen.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData) null);
		return true;
	}

}
