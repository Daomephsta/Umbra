package daomephsta.umbra.streams;

import java.util.Collection;
import java.util.stream.Collector;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public class UmbraCollectors
{
	public static final Collector<NBTBase, ?, NBTTagList> NBT_LIST = Collector.of(NBTTagList::new,
		NBTTagList::appendTag, (a, b) ->
		{
			b.forEach(a::appendTag);
			return b;
		});

	public static <I> Collector<I, ?, NonNullList<I>> nonnullList()
	{
		return Collector.of(() -> NonNullList.create(), Collection::add, (a, b) ->
		{
			a.addAll(b);
			return a;
		});
	}
}
