package daomephsta.umbra.nbt;

import net.minecraft.nbt.NBTTagCompound;

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
}
