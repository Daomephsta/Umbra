package daomephsta.umbra.nbt;

import java.util.Map.Entry;

import com.google.common.collect.Iterables;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class NBTExtensions
{
	public static <E extends Enum<E>> E getEnumConstant(NBTTagCompound nbt, Class<E> enumClass, String key, E fallback)
	{
		return nbt.hasKey(key) 
			? Enum.valueOf(enumClass, nbt.getString(key))
			: fallback;
	}
	
	public static <E extends Enum<E>> E getEnumConstant(NBTTagCompound nbt, Class<E> enumClass, String key)
	{
		return Enum.valueOf(enumClass, nbt.getString(key));
	}
	
	public static <E extends Enum<E>> void setEnumConstant(NBTTagCompound nbt, String key, E enumConstant)
	{
		nbt.setString(key, enumConstant.name());
	}
	
	public static IBlockState getBlockState(NBTTagCompound nbt, String key)
	{
		NBTTagCompound stateTag = nbt.getCompoundTag(key);
		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(stateTag.getString("block")));
		NBTTagCompound propertiesTag = stateTag.getCompoundTag("properties");
		BlockStateContainer stateContainer = block.getBlockState();
		IBlockState state = block.getDefaultState();
		for (String propName : propertiesTag.getKeySet())
		{
			IProperty<?> property = stateContainer.getProperty(propName);
			state = deserialisePropertyValue(state, property, propertiesTag.getString(propName));
		}
		return state;
	}
	
	private static <T extends Comparable<T>> IBlockState deserialisePropertyValue(IBlockState state, IProperty<T> property, String serValue)
	{
		T value = property.parseValue(serValue).get();
		return state.withProperty(property, value);
	}
	
	public static void setBlockState(NBTTagCompound nbt, String key, IBlockState state)
	{
		NBTTagCompound stateTag = new NBTTagCompound();
		stateTag.setString("block", state.getBlock().getRegistryName().toString());
		NBTTagCompound propertiesTag = new NBTTagCompound();
		for (Entry<IProperty<?>, Comparable<?>> entry : state.getProperties().entrySet())
		{
			IProperty<?> property = entry.getKey();
			propertiesTag.setString(property.getName(), serialisePropertyValue(property, entry.getValue()));
		}
		stateTag.setTag("properties", propertiesTag);
		
		nbt.setTag(key, stateTag);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> String serialisePropertyValue(IProperty<T> property, Comparable<?> value)
	{
		return property.getName((T) value);
	}
	
	public static BlockPos getPosition(NBTTagCompound nbt, String key)
	{
		int[] posTag = nbt.getIntArray(key);
		int x = posTag[0],
			y = posTag[1],
			z = posTag[2];
		return new BlockPos(x, y, z);
	}
	
	public static void setPosition(NBTTagCompound nbt, String key, BlockPos pos)
	{
		nbt.setIntArray(key, new int[] {pos.getX(), pos.getY(), pos.getZ()});
	}
	
	public static boolean contains(NBTTagList list, String target)
	{
		return Iterables.any(list, nbt -> nbt instanceof NBTTagString && ((NBTTagString) nbt).getString().equals(target));
	}
}
