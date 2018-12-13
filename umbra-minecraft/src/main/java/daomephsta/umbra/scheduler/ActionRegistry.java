package daomephsta.umbra.scheduler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ActionRegistry
{
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Map<Class<?>, ActionSerDes<?>> CLASS_TO_SERDES = new HashMap<>();
	private static final Map<ResourceLocation, ActionSerDes<?>> TYPE_TO_SERDES = new HashMap<>();
	
	public static <T extends IAction> void register(ActionSerDes<T> actionSerDes)
	{
		CLASS_TO_SERDES.put(actionSerDes.getActionClass(), actionSerDes);
		TYPE_TO_SERDES.put(actionSerDes.getActionType(), actionSerDes);
	}
	
	public static boolean isRegistered(IAction action)
	{
		return CLASS_TO_SERDES.containsKey(action.getClass());
	}
	
	public static NBTTagCompound serialiseAction(IAction action)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		//It's guaranteed to be the proper type, assuming the implementation of the serdes is correct.
		@SuppressWarnings("unchecked") 
		ActionSerDes<IAction> serDes = (ActionSerDes<IAction>) CLASS_TO_SERDES.get(action.getClass());
		if (serDes == null)
		{
			LOGGER.warn("No serialiser found for the action {}: {}", action, action.getClass());
			return nbt;
		}
		nbt.setString("action_type", serDes.getActionType().toString());
		serDes.serialise(nbt, action);
		return nbt;
	}
	
	@Nullable
	public static IAction deserialiseAction(NBTTagCompound nbt)
	{
		ResourceLocation actionType = new ResourceLocation(nbt.getString("action_type"));
		//It's guaranteed to be the proper type, assuming the implementation of the serdes is correct.
		@SuppressWarnings("unchecked") 
		ActionSerDes<IAction> serDes = (ActionSerDes<IAction>) TYPE_TO_SERDES.get(actionType);
		if (serDes == null)
		{
			LOGGER.warn("No deserialiser found for the action type {}", actionType);
			return null;
		}
		return serDes.deserialise(nbt);
	}
}
