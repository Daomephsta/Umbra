package daomephsta.umbra.test.bitshifting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import daomephsta.umbra.bitmanipulation.IBitEncoderDecoder;

public class BitEncoderDecoderTest
{
	@Test
	public void testDecodeManualEncode()
	{
		IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
		bitEncoderDecoder.set(2);
		bitEncoderDecoder.set(3);
		assertEquals("decode() failed to correctly %d1", 121, bitEncoderDecoder.decode());
		
		bitEncoderDecoder.clear();
		bitEncoderDecoder.set(3);
		assertEquals("decode() failed to correctly decode a manually encoded integer", 8, bitEncoderDecoder.decode());
	}
	
	@Test
	public void testEncodeManualDecode()
	{
		IBitEncoderDecoder bitEncoderDecoder = IBitEncoderDecoder.fixedBitCount(4);
		bitEncoderDecoder.encode(12);
		//"encode() failed to correctly encode a manually encoded integer"
		
		bitEncoderDecoder.clear();
		//8
	}
}
