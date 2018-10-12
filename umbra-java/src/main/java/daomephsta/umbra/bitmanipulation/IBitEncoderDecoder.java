package daomephsta.umbra.bitmanipulation;

import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

public interface IBitEncoderDecoder extends Iterable<Integer>
{
	public static IBitEncoderDecoder fixedBitCount(int bitCount)
	{
		return new BitEncoderDecoderFixedBitCount(bitCount);
	}
	
	public static IBitEncoderDecoder dynamicBitCount()
	{
		return new BitEncoderDecoderDynamicBitCount();
	}
	
	public static IBitEncoderDecoder dynamicBitCount(int initialBitCount)
	{
		return new BitEncoderDecoderDynamicBitCount(initialBitCount);
	}
	
	public void encode(int value);
	
	public default void encode(int bitIndex, int value) 
	{
		switch (value)
		{
		case 0:
			set(bitIndex, false);
			break;
		case 1:
			set(bitIndex, true);
			break;
		default:
			throw new IllegalArgumentException("value must be 0 or 1");
		}
	}
	
	public void encode(int fromIndex, int toIndex, int value);
	
	public int decode();
	
	public default int decode(int bitIndex) {return get(bitIndex) ? 1 : 0;}
	
	public int decode(int fromIndex, int toIndex);

	public byte[] toByteArray();

	public long[] toLongArray();

	public void flip(int bitIndex);

	public void flip(int fromIndex, int toIndex);

	public void set(int bitIndex);

	public void set(int bitIndex, boolean value);

	public void set(int fromIndex, int toIndex);

	public void set(int fromIndex, int toIndex, boolean value);
	
	public default void setInt(int fromIndex, int toIndex, int value) 
	{
		switch (value)
		{
		case 0:
			set(fromIndex, toIndex, false);
			break;
		case 1:
			set(fromIndex, toIndex, true);
			break;
		default:
			throw new IllegalArgumentException("value must be 0 or 1");
		}
	}

	public void clear(int bitIndex);

	public void clear(int fromIndex, int toIndex);

	public void clear();

	public boolean get(int bitIndex);

	public boolean isEmpty();

	public int cardinality();

	public int size();
	
	@Override
	public PrimitiveIterator.OfInt iterator();
	
	public IntStream stream();
}
