package daomephsta.umbra.bitmanipulation;

import java.util.BitSet;
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
	
	public void encode(long value);
	
	public void encode(int fromIndex, int toIndex, long value);
	
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

	public BitSet get(int fromIndex, int toIndex);

	public int nextSetBit(int fromIndex);

	public int nextClearBit(int fromIndex);

	public int previousSetBit(int fromIndex);

	public int previousClearBit(int fromIndex);

	public int length();

	public boolean isEmpty();

	public boolean intersects(BitSet set);

	public int cardinality();

	public void and(BitSet set);

	public void or(BitSet set);

	public void xor(BitSet set);

	public void andNot(BitSet set);

	public int size();

	public IntStream stream();
}
