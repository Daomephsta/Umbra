package daomephsta.umbra.streams;

import java.util.Collection;
import java.util.stream.Collector;

import net.minecraft.util.NonNullList;

public class MiscUmbraCollectors
{
	public static <I> Collector<I, ?, NonNullList<I>> toNonNullList()
	{
		return Collector.of(NonNullList::create, Collection::add, (a, b) ->
		{
			a.addAll(b);
			return a;
		});
	}
}
