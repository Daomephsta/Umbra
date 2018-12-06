package daomephsta.umbra.scheduler;

import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import daomephsta.umbra.capabilities.CapabilityHelper;
import daomephsta.umbra.internal.Umbra;
import daomephsta.umbra.streams.NBTCollectors;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@Mod.EventBusSubscriber(modid = Umbra.MODID)
public class ActionScheduler
{
	private static final Logger LOGGER = LogManager.getLogger();
	@CapabilityInject(ScheduledActions.class)
	private static final Capability<ScheduledActions> SCHEDULED_ACTIONS = null;
	private static final ResourceLocation CAP_KEY = new ResourceLocation(Umbra.MODID, "scheduler");
	
	public static void schedule(World world, IAction action, long delay)
	{
		if (!ActionRegistry.isRegistered(action))
		{
			LOGGER.warn("Could not schedule action {}  to occur in {} ticks in world {} as it is unregistered", action, delay, world);
			return;
		}
		world.getCapability(SCHEDULED_ACTIONS, null).scheduleAction(action, delay);
	}
	
	public static void preinit()
	{
		CapabilityManager.INSTANCE.register(ScheduledActions.class, CapabilityHelper.noOpStorage(), () -> null);
	}
	
	@SubscribeEvent
	public static void onWorldTick(WorldTickEvent event)
	{
		event.world.getCapability(SCHEDULED_ACTIONS, null).tick();
	}
	
	@SubscribeEvent
	public static void attachWorldCapabilities(AttachCapabilitiesEvent<World> event)
	{
		event.addCapability(CAP_KEY, new ScheduledActionsProvider(event.getObject()));
	}
	
	private static class ScheduledActionsProvider implements ICapabilitySerializable<NBTTagCompound>
	{
		private final ScheduledActions scheduledActions;

		private ScheduledActionsProvider(World world)
		{
			this.scheduledActions = new ScheduledActions(world);
		}

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing)
		{
			return capability == SCHEDULED_ACTIONS;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing)
		{
			if (capability == SCHEDULED_ACTIONS)
				return SCHEDULED_ACTIONS.cast(scheduledActions);
			return null;
		}

		@Override
		public NBTTagCompound serializeNBT()
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setTag("actions", scheduledActions.actionQueue.stream()
				.map(a -> a.serializeNBT())
				.collect(NBTCollectors.toNBTList(NBTTagCompound.class)));
			return nbt;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt)
		{
			for (NBTBase actionTag : nbt.getTagList("actions", NBT.TAG_COMPOUND))
			{
				NBTTagCompound actionTagCompound = (NBTTagCompound) actionTag;
				TimestampedAction action = TimestampedAction.deserializeNBT(actionTagCompound);
				if (action != null)
					scheduledActions.actionQueue.add(action);
			}
		}
	}
	
	private static class ScheduledActions
	{
		private final Queue<TimestampedAction> actionQueue = new PriorityQueue<>();
		private final World world;
		
		private ScheduledActions(World world)
		{
			this.world = world;
		}

		public void tick()
		{
			long timestamp = world.getTotalWorldTime();
			while(!actionQueue.isEmpty())
			{
				TimestampedAction action = actionQueue.peek();
				if (action.timestamp <= timestamp)
				{
					actionQueue.remove();
					action.apply(world);
				}
				else break;
			}
		}

		private void scheduleAction(IAction action, long delay)
		{
			actionQueue.add(new TimestampedAction(action, world.getTotalWorldTime() + delay));
		}
	}
	
	private static class TimestampedAction implements Comparable<TimestampedAction>
	{
		private final IAction action;
		private final long timestamp;
		
		private TimestampedAction(IAction action, long timestamp)
		{
			this.action = action;
			this.timestamp = timestamp;
		}

		public void apply(World world)
		{
			action.apply(world);
		}

		@Override
		public int compareTo(TimestampedAction other)
		{
			return Long.compare(this.timestamp, other.timestamp);
		}

		public NBTTagCompound serializeNBT()
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setTag("action", ActionRegistry.serialiseAction(action));
			nbt.setLong("timestamp", timestamp);
			return nbt;
		}

		public static TimestampedAction deserializeNBT(NBTTagCompound nbt)
		{
			IAction action = ActionRegistry.deserialiseAction(nbt.getCompoundTag("action"));
			long timestamp = nbt.getLong("timestamp");
			if (action != null)
				return new TimestampedAction(action, timestamp);
			else
				return null;
		}
	}
}
