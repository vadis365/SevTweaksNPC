package sev_tweaks_npc.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sev_tweaks_npc.ModSounds;
import sev_tweaks_npc.worldgen.WorldGenLadderPillar;

public class ItemLadderOfAscension extends Item {
	public ItemLadderOfAscension() {
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.MISC);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		list.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.golden_ladder_of_ascension_1").getFormattedText());
		list.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.golden_ladder_of_ascension_2").getFormattedText());
		list.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.golden_ladder_of_ascension_3").getFormattedText());
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) {
			if (!player.canPlayerEdit(pos, facing, stack)) {
				return EnumActionResult.FAIL;
			} else {
				IBlockState block = world.getBlockState(pos);
					if (block.isNormalCube() && new WorldGenLadderPillar().generate(world, itemRand, pos.up()) && pos.getY() <= 100) {
						world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ModSounds.LADDER_PLACEMENT, SoundCategory.PLAYERS, 0.5F, 1F);
						stack.shrink(1);
						for(int x = 0; x < 2; x++) {
							ItemStack stackLadder = new ItemStack(Blocks.LADDER, 50);
							InventoryHelper.spawnItemStack(world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), stackLadder);
						}
						return EnumActionResult.SUCCESS;
				} else
					player.sendStatusMessage(new TextComponentTranslation("chat.ladder.noplace"), true);
			}
		}
		return EnumActionResult.FAIL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
