package daomephsta.umbra.test.bitshifting;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import daomephsta.umbra.bitmanipulation.IBitEncoderDecoder;

public class BitEncoderDecoderDynamicBitCountTest
{
	@Test
	public void testDecode()
	{
		//Full encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.dynamicBitCount();
			bitEncoderDecoder.set(bitEncoderDecoder.size() - 2);
			bitEncoderDecoder.set(bitEncoderDecoder.size() - 3);
			assertEquals(6, bitEncoderDecoder.decode(), "decode() failed to correctly decode a whole binary sequence");
		}
		//Partial encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.dynamicBitCount();
			bitEncoderDecoder.set(0);
			bitEncoderDecoder.set(1);
			assertEquals(3, bitEncoderDecoder.decode(0, 2), "decode() failed to correctly decode part of a binary sequence");
		}
	}

	@Test
	public void testEncode()
	{
		//Full encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.dynamicBitCount();
			bitEncoderDecoder.encode(6);
			
			//Check that the last 3 bits are 110
			boolean correctEncoding = true;
			for (int i = 0; i < bitEncoderDecoder.size() - 3; i++)
			{
				correctEncoding &= !bitEncoderDecoder.get(i);
			}
			correctEncoding &= 
				bitEncoderDecoder.get(bitEncoderDecoder.size() - 3) 
				&& bitEncoderDecoder.get(bitEncoderDecoder.size() - 2) 
				&& !bitEncoderDecoder.get(bitEncoderDecoder.size() - 1);
			
			assertTrue(correctEncoding, "encode() failed to correctly encode an integer as a whole binary sequence");
		}
		//Partial encode
		{
			IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.dynamicBitCount();
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
				IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.dynamicBitCount();
				bitEncoderDecoder.encode(testInt);
				assertEquals(testInt, bitEncoderDecoder.decode(), 
					String.format("%1$s#encode() & %1$s#encode() do not map 1:1 for value %2$s when encoding as a whole binary sequence.", 
					bitEncoderDecoder.getClass().getSimpleName(), testInt));
			}
			//Partial encode
			{
				int FROM = 2, TO = 6;
				IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.dynamicBitCount();
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
