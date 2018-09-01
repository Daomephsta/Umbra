package daomephsta.umbra.streams;

import java.util.stream.Collector;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

public class UmbraCollectors
{
	public static final Collector<NBTBase, ?, NBTTagList> NBT_LIST = Collector.of(NBTTagList::new, NBTTagList::appendTag, 
		(a, b) ->
		{
			b.forEach(a::appendTag);
			return b;
		});
}

