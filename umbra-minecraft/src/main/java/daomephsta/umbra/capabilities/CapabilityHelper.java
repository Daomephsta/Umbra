package daomephsta.umbra.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityHelper
{
	private static final Capability.IStorage<Object> NO_OP_STORAGE = new Capability.IStorage<Object>()
	{
		@Override
		public NBTBase writeNBT(Capability<Object> capability, Object instance, EnumFacing side)
		{
			return null;
		}

		@Override
		public void readNBT(Capability<Object> capability, Object instance, EnumFacing side, NBTBase nbt) {}
	};
	
	@SuppressWarnings("unchecked")
	public static <T> Capability.IStorage<T> noOpStorage()
	{
		return (IStorage<T>) NO_OP_STORAGE;
	}
	
	public static <T> Capability.IStorage<T> fromLambdas(ICapNBTReader<T> reader, ICapNBTWriter<T> writer)
	{
		return new LambdaStorage<T>(reader, writer);
	}
	
	public static interface ICapNBTReader<T>
	{
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt);
	}
	
	public static interface ICapNBTWriter<T>
	{
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side);
	}
	
	private static class LambdaStorage<T> implements Capability.IStorage<T>
	{
		private final ICapNBTReader<T> reader;
		private final ICapNBTWriter<T> writer;
		
		public LambdaStorage(ICapNBTReader<T> reader, ICapNBTWriter<T> writer)
		{
			this.reader = reader;
			this.writer = writer;
		}

		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side)
		{
			return writer.writeNBT(capability, instance, side);
		}

		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt)
		{
			reader.readNBT(capability, instance, side, nbt);
		}
	}
}
