package daomephsta.umbra.network;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.*;

/**
 * @author Daomephsta
 * Automatically schedules packet actions to be performed on the main thread.
 * @param <M> The implementation of {@code IMessage} the subclassing handler handles.
 */
public abstract class SchedulingMessageHandler<M extends IMessage> implements IMessageHandler<M, IMessage>
{
	@Override
	public final IMessage onMessage(M message, MessageContext ctx)
	{
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
		return null;
	}
	
	protected abstract void processMessage(M message, MessageContext ctx);
}

