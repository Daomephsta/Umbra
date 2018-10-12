package daomephsta.umbra.test.bitshifting;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

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
			assertEquals(6, bitEncoderDecoder.decode(), "decode() failed to correctly decode a whole binary sequence");
		}
		//Partial encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
			bitEncoderDecoder.set(0);
			bitEncoderDecoder.set(1);
			assertEquals(3, bitEncoderDecoder.decode(0, 2), "decode() failed to correctly decode part of binary sequence");
		}
	}

	@Test
	public void testEncode()
	{
		//Full encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
			bitEncoderDecoder.encode(6);
			assertTrue(!bitEncoderDecoder.get(0) && bitEncoderDecoder.get(1) && bitEncoderDecoder.get(2) && !bitEncoderDecoder.get(3), 
				"encode() failed to correctly encode an integer as a whole binary sequence");
		}
		//Partial encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
			bitEncoderDecoder.encode(0, 2, 3);
			assertTrue(bitEncoderDecoder.get(0) && bitEncoderDecoder.get(1) && !bitEncoderDecoder.get(2) && !bitEncoderDecoder.get(3),
				"encode() failed to correctly encode an integer into part of a binary sequence");
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
				assertEquals(testInt, bitEncoderDecoder.decode(), 
					String.format("%1$s#encode() & %1$s#encode() do not map 1:1 for value %2$s when encoding as a whole binary sequence.", 
					bitEncoderDecoder.getClass().getSimpleName(), testInt));
			}
			//Partial encode
			{
				int FROM = 2, TO = 6;
				IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(8);
				bitEncoderDecoder.encode(FROM, TO, testInt);
				assertEquals(testInt, bitEncoderDecoder.decode(FROM, TO),
					String.format("%1$s#encode() & %1$s#encode() do not map 1:1 for value %2$s when encoding as part of a binary sequence.", 
					bitEncoderDecoder.getClass().getSimpleName(), testInt));
			}
		}
	}
	
	@Test
	public void testIterator()
	{
		IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
		bitEncoderDecoder.set(0);
		bitEncoderDecoder.set(3);
		
		int[] actual = new int[bitEncoderDecoder.size()];
		PrimitiveIterator.OfInt iter = bitEncoderDecoder.iterator();
		for (int i = 0; i < bitEncoderDecoder.size(); i++)
		{
			actual[i] = iter.nextInt();
		}
		int[] expected = {1, 0, 0, 1};
		assertArrayEquals(expected, actual, 
			String.format("The iterator %s did not produce the expected integers %s", 
			iter.getClass().getSimpleName(), Arrays.toString(expected)));
		
	}
}
