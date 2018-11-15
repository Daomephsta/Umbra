package daomephsta.umbra.network;

import io.netty.buffer.ByteBuf;

public class ByteBufExtensions
{
	public static void writeIntArray(ByteBuf to, int[] array)
	{
		to.writeInt(array.length);
		for (int i : array)
		{
			to.writeInt(i);
		}
	}
	
	public static int[] readIntArray(ByteBuf from)
	{
		int length = from.readInt();
		int[] array = new int[length];
		for (int i = 0; i < length; i++)
		{
			array[i] = from.readInt();
		}
		return array;
	}
}
