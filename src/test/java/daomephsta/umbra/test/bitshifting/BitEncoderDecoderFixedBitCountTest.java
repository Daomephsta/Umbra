package daomephsta.umbra.test.bitshifting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.Test;

import daomephsta.umbra.bitmanipulation.IBitEncoderDecoder;

public class BitEncoderDecoderFixedBitCountTest
{
	@Test
	public void testDecode()
	{
		//Full encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
			bitEncoderDecoder.set(1);
			bitEncoderDecoder.set(2);
			assertEquals("decode() failed to correctly decode a whole binary sequence", 6, bitEncoderDecoder.decode());
		}
		//Partial encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
			bitEncoderDecoder.set(0);
			bitEncoderDecoder.set(1);
			assertEquals("decode() failed to correctly decode part of binary sequence", 3, bitEncoderDecoder.decode(0, 2));
		}
	}

	@Test
	public void testEncode()
	{
		//Full encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
			bitEncoderDecoder.encode(6);
			assertTrue("encode() failed to correctly encode an integer as a whole binary sequence", 
				!bitEncoderDecoder.get(0) && bitEncoderDecoder.get(1) && bitEncoderDecoder.get(2) 
				&& !bitEncoderDecoder.get(3));
		}
		//Partial encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
			bitEncoderDecoder.encode(0, 2, 3);
			assertTrue("encode() failed to correctly encode an integer into part of a binary sequence",
				bitEncoderDecoder.get(0) && bitEncoderDecoder.get(1) && !bitEncoderDecoder.get(2) 
				&& !bitEncoderDecoder.get(3));
		}
	}

	@Test
	public void testEncodeDecode()
	{
		int[] testInts = IntStream.range(0, 16).toArray();
		for (int testInt : testInts)
		{
			//Full encode
			{
				IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
				bitEncoderDecoder.encode(testInt);
				assertEquals(String.format("%1$s#encode() & %1$s#encode() do not map 1:1 for value %2$s when encoding as a whole binary sequence.", 
					bitEncoderDecoder.getClass().getSimpleName(), testInt), testInt, bitEncoderDecoder.decode());
			}
			//Partial encode
			{
				int FROM = 2, TO = 6;
				IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(8);
				bitEncoderDecoder.encode(FROM, TO, testInt);
				assertEquals(String.format("%1$s#encode() & %1$s#encode() do not map 1:1 for value %2$s when encoding as part of a binary sequence.", 
					bitEncoderDecoder.getClass().getSimpleName(), testInt), testInt, bitEncoderDecoder.decode(FROM, TO));
			}
		}
	}
}
