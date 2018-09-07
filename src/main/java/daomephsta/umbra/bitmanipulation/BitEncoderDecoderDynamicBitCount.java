package daomephsta.umbra.bitmanipulation;

import java.util.BitSet;
import java.util.stream.IntStream;

import com.google.common.math.IntMath;

class BitEncoderDecoderDynamicBitCount implements IBitEncoderDecoder
{
	private final BitSet bits;
	
	BitEncoderDecoderDynamicBitCount()
	{
		this.bits = new BitSet();
	}
	
	BitEncoderDecoderDynamicBitCount(int initialBitCount)
	{
		this.bits = new BitSet(initialBitCount);
	}

	@Override
	public void encode(int value)
	{
		encode(0, size(), value);
	}
	
	@Override
	public void encode(int fromIndex, int toIndex, int value)
	{
		int maxStorableValue = IntMath.pow(2, toIndex - fromIndex) - 1;
		if (value > maxStorableValue) 
		{
			throw new IllegalArgumentException(String.format(
			"%d is larger than %d, the largest integer that can be stored in %d bits", 
			value, maxStorableValue, toIndex - fromIndex));
		}
	}
	
	@Override
	public int decode()
	{
		return decode(0, size());
	}
	
	@Override
	public int decode(int fromIndex, int toIndex)
	{
		int result = 0;
		for(int i = fromIndex; i < toIndex; i++)
		{
			if (bits.get(i))
				result += IntMath.pow(2, i);
		}
		return result >> fromIndex;
	}

	@Override
	public byte[] toByteArray()
	{
		return bits.toByteArray();
	}

	@Override
	public long[] toLongArray()
	{
		return bits.toLongArray();
	}

	@Override
	public void flip(int bitIndex)
	{
		bits.flip(bitIndex);
	}

	@Override
	public void flip(int fromIndex, int toIndex)
	{
		bits.flip(fromIndex, toIndex);
	}

	@Override
	public void set(int bitIndex)
	{
		bits.set(bitIndex);
	}

	@Override
	public void set(int bitIndex, boolean value)
	{
		bits.set(bitIndex, value);
	}

	@Override
	public void set(int fromIndex, int toIndex)
	{
		bits.set(fromIndex, toIndex);
	}

	@Override
	public void set(int fromIndex, int toIndex, boolean value)
	{
		bits.set(fromIndex, toIndex, value);
	}

	@Override
	public void clear(int bitIndex)
	{
		bits.clear(bitIndex);
	}

	@Override
	public void clear(int fromIndex, int toIndex)
	{
		bits.clear(fromIndex, toIndex);
	}

	@Override
	public void clear()
	{
		bits.clear();
	}

	@Override
	public boolean get(int bitIndex)
	{
		return bits.get(bitIndex);
	}

	@Override
	public boolean isEmpty()
	{
		return bits.isEmpty();
	}

	@Override
	public int cardinality()
	{
		return bits.cardinality();
	}

	@Override
	public int hashCode()
	{
		return bits.hashCode();
	}

	@Override
	public int size()
	{
		return bits.size();
	}

	@Override
	public boolean equals(Object obj)
	{
		return bits.equals(obj);
	}

	@Override
	public String toString()
	{
		return bits.toString();
	}

	@Override
	public IntStream stream()
	{
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
