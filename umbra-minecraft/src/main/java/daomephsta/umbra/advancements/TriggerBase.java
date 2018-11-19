package daomephsta.umbra.advancements;

import static java.util.stream.Collectors.toList;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import net.minecraft.advancements.*;
import net.minecraft.entity.player.EntityPlayerMP;

public abstract class TriggerBase<T extends ICriterionInstance> implements ICriterionTrigger<T>
{
	protected final Multimap<PlayerAdvancements, Listener<T>> listeners = MultimapBuilder.hashKeys().hashSetValues().build();
	
	protected void grantPassedCriteria(EntityPlayerMP player, Predicate<Listener<T>> conditions)
	{
		Collection<Listener<T>> playerListeners = listeners.get(player.getAdvancements());
		/* Avoid CME by adding criterions that have passed to a list, then iterating over that list
		 * and granting them. In this case all criterions pass, so the passed list is initialised with
		 * the criterion list*/
		Collection<Listener<T>> passedCriteria = playerListeners.stream()
			.filter(conditions)
			.collect(toList());
		for (Listener<T> passedCriterion : passedCriteria)
		{
			passedCriterion.grantCriterion(player.getAdvancements());
		}
	}
	
	@Override
	public void addListener(PlayerAdvancements playerAdvancements, Listener<T> listener)
	{
		listeners.put(playerAdvancements, listener);
	}

	@Override
	public void removeListener(PlayerAdvancements playerAdvancements, Listener<T> listener)
	{
		listeners.remove(playerAdvancements, listener);
	}

	@Override
	public void removeAllListeners(PlayerAdvancements playerAdvancements)
	{
		listeners.removeAll(playerAdvancements);
	}
	
	public boolean hasListeners(PlayerAdvancements playerAdvancements)
	{
		return listeners.containsKey(playerAdvancements);
	}
}
