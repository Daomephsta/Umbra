package daomephsta.umbra.bitmanipulation;

import java.util.PrimitiveIterator.OfInt;

class BitEncoderDecoderIterator implements OfInt
{
	private final IBitEncoderDecoder bitEncoderDecoder;
	private int cursor = -1;
	
	BitEncoderDecoderIterator(IBitEncoderDecoder bitEncoderDecoder)
	{
		this.bitEncoderDecoder = bitEncoderDecoder;
	}

	@Override
	public boolean hasNext()
	{
		return cursor < bitEncoderDecoder.size();
	}

	@Override
	public int nextInt()
	{
		return bitEncoderDecoder.getInt(++cursor);
	}
}
