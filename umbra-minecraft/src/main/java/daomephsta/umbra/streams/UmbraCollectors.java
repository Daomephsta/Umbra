package daomephsta.umbra.streams;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.stream.Collector;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public class UmbraCollectors
{
	public static <T extends NBTBase> Collector<T, ?, NBTTagList> toNBTList(Class<T> expectedClass)
	{
		return Collector.of(NBTTagList::new,
			throwingAccumulator(expectedClass), (a, b) ->
			{
				b.forEach(a::appendTag);
				return b;
			});
	}
	
	private static <T extends NBTBase> BiConsumer<NBTTagList, T> throwingAccumulator(Class<T> expectedClass)
	{
		return (list, element) -> 
		{
			if (!expectedClass.isInstance(element))
				throw new IllegalStateException(String.format("%s was an instance of %s, not the expected type %s", element, element.getClass(), expectedClass));
			list.appendTag(element);
		};
	}
 
	public static <I> Collector<I, ?, NonNullList<I>> toNonNullList()
	{
		return Collector.of(NonNullList::create, Collection::add, (a, b) ->
		{
			a.addAll(b);
			return a;
		});
	}
}
