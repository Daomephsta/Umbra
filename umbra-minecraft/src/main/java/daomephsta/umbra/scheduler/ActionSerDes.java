package daomephsta.umbra.scheduler;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public abstract class ActionSerDes<T extends IAction>
{
	private final ResourceLocation actionType;
	private final Class<T> actionClass;
	
	public ActionSerDes(ResourceLocation identifier, Class<T> actionClass)
	{
		this.actionType = identifier;
		this.actionClass = actionClass;
	}
	
	public ResourceLocation getActionType()
	{
		return actionType;
	}

	public Class<T> getActionClass()
	{
		return actionClass;
	}

	public abstract void serialise(NBTTagCompound nbt, T actionInstance);
	
	@Nullable
	public abstract T deserialise(NBTTagCompound nbt);
}
