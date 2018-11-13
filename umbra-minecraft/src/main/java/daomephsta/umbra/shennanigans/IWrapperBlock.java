package daomephsta.umbra.shennanigans;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IWrapperBlock
{
	public IBlockState getWrappedState(IBlockState wrapperState, IBlockAccess blockAccess, BlockPos pos);
}
