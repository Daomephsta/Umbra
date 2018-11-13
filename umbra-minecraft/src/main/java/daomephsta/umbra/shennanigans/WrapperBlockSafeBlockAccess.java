package daomephsta.umbra.shennanigans;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;

public class WrapperBlockSafeBlockAccess implements IBlockAccess
{
	private final IBlockAccess wrapped;
	
	public WrapperBlockSafeBlockAccess(IBlockAccess wrapped)
	{
		this.wrapped = wrapped;
	}

	@Override
	public TileEntity getTileEntity(BlockPos pos)
	{
		return wrapped.getTileEntity(pos);
	}

	@Override
	public int getCombinedLight(BlockPos pos, int lightValue)
	{
		return wrapped.getCombinedLight(pos, lightValue);
	}

	@Override
	public IBlockState getBlockState(BlockPos pos)
	{
		IBlockState state = wrapped.getBlockState(pos);
		if (state.getBlock() instanceof IWrapperBlock)
			return ((IWrapperBlock) state.getBlock()).getWrappedState(state, wrapped, pos);
		return state;
	}

	@Override
	public boolean isAirBlock(BlockPos pos)
	{
		return wrapped.isAirBlock(pos);
	}

	@Override
	public Biome getBiome(BlockPos pos)
	{
		return wrapped.getBiome(pos);
	}

	@Override
	public int getStrongPower(BlockPos pos, EnumFacing direction)
	{
		return wrapped.getStrongPower(pos, direction);
	}

	@Override
	public WorldType getWorldType()
	{
		return wrapped.getWorldType();
	}

	@Override
	public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default)
	{
		return wrapped.isSideSolid(pos, side, _default);
	}
}
