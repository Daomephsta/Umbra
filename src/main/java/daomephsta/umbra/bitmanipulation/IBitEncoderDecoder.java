package daomephsta.umbra.bitmanipulation;

import java.util.stream.IntStream;

public interface IBitEncoderDecoder
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
	
	public void encode(int fromIndex, int toIndex, int value);
	
	public int decode();
	
	public int decode(int fromIndex, int toIndex);

	public byte[] toByteArray();

	public long[] toLongArray();

	public void flip(int bitIndex);

	public void flip(int fromIndex, int toIndex);

	public void set(int bitIndex);

	public void set(int bitIndex, boolean value);

	public void set(int fromIndex, int toIndex);

	public void set(int fromIndex, int toIndex, boolean value);

	public void clear(int bitIndex);

	public void clear(int fromIndex, int toIndex);

	public void clear();

	public boolean get(int bitIndex);

	public boolean isEmpty();

	public int cardinality();

	public int size();

	public IntStream stream();
}
