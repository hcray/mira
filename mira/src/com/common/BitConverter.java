package com.common;

public class BitConverter {
	/*
	 * 整型数（short,int,long,char等）用byte[]存储时有两种情况
	 *  1.高位在前(java默认读取方式)
	 *  2.低位在前(其它情况)
	 *  
	 *  以int 257;为例在：
	 *       高位在前存储�?个byte对应的是 [0,0,1,1]
	 *       低位在前存储�?个byte对应的是 [1,1,0,0]
	 */
	public static final  int FLAG_JAVA = 0;  //整形数用高位在前存储方式
	public static final int FLAG_REVERSE = -1;  //整形数用低位在前存储方式
	

	/** short -&gt; byte[] */
	public static  byte[] getBytes(short s){
		return getBytes(s, FLAG_JAVA);
	}
	/** short -&gt; byte[] */
	public static byte[] getBytes(short s, int flag) {
		byte[] b = new byte[2];
		switch (flag) {
		case BitConverter.FLAG_JAVA:
			b[0] = (byte) ((s >> 8) & 0xff);
			b[1] = (byte) (s & 0xff);
			break;

		case BitConverter.FLAG_REVERSE:
			b[1] = (byte) ((s >> 8) & 0xff);
			b[0] = (byte) (s & 0xff);
			break;
		default:
			break;	
		}
		return b;
	}
	
	/** byte[] -&gt; short */
	public static short toShort(byte[] b) {
		return toShort(b, FLAG_JAVA);
	}
	/** byte[] -&gt; short */
	public static short toShort(byte[] b, int flag) {
		switch (flag) {
		case BitConverter.FLAG_JAVA:
			return (short) (((b[0] & 0xff) << 8) | (b[1] & 0xff));
		case BitConverter.FLAG_REVERSE:
			return (short) (((b[1] & 0xff) << 8) | (b[0] & 0xff));
		default:
			throw new IllegalArgumentException("BitConverter:toShort");
		}
	}
	
	/** float -&gt; byte[] */
	public static byte[] getBytes(float f){
		return getBytes(f, FLAG_JAVA);
	}
	/** float -&gt; byte[] */
	public static byte[] getBytes(float f,int flag){
		int s = Float.floatToIntBits(f);
		byte[] b = new byte[4];
		switch (flag) {
		case BitConverter.FLAG_REVERSE:
			for(int i = 0;i< 4;i++){
				b[i] = new Integer(s).byteValue();
				s = s >> 8;
			}
			break;
		case BitConverter.FLAG_JAVA:
			for(int i = 3;i>=0;i--){
				b[i] = new Integer(s).byteValue();
				s = s << 8;
			}
			break;
		default:
			break;	
		}
		return b;
	}
	
	/** byte[] -&gt; float */
	public static float tofloat(byte[] b){
		return tofloat(b,FLAG_JAVA);
	}
	/** byte[] -&gt; float */
	public static float tofloat(byte[] b, int flag){
		int l;
		switch(flag){
		case BitConverter.FLAG_REVERSE:
			l = b[0];
			l &= 0xff;
			l |= ((long) b[1] << 8);  
			l &= 0xffff;
			l |= ((long) b[2] << 16); 
			l &= 0xffffff;
			l |= ((long) b[3] << 24); 
			return Float.intBitsToFloat(l);
			//return (float)(((b[0])<<24) | ((b[1] & 0xff)<<16) | ((b[2] & 0xffff)<<8) | (b[3] & 0xffffff));
		case BitConverter.FLAG_JAVA:
			l = b[3];
			l &= 0xff;
			l |= ((long) b[2] << 8);  
			l &= 0xffff;
			l |= ((long) b[1] << 16); 
			l &= 0xffffff;
			l |= ((long) b[0] << 24); 
			return Float.intBitsToFloat(l);
			//return (float)(((b[3])<<24) | ((b[2] & 0xff)<<16) | ((b[1] & 0xffff)<<8) | (b[0] & 0xffffff));
		default:
			throw new IllegalArgumentException("BitConverter:tofloat");
	}
	}
	
	/** double -&gt; byte[] */
	public static byte[] getBytes(double d){
		return getBytes(d,FLAG_JAVA);
	}
	/** double -&gt; byte[] */
	public static byte[] getBytes(double d, int flag){
		long i = Double.doubleToLongBits(d);
		byte[] b = new byte[8];
		switch (flag) {
		case BitConverter.FLAG_REVERSE:
			for(int j=0;j<8;j++){
				b[j] = new Long(i).byteValue();
				i = i >> 8;
			}
			break;
		case BitConverter.FLAG_JAVA:
			for(int j=7;j>=0;j--){
				b[j] = new Long(i).byteValue();
				i = i << 8;
			}
			break;
		default:
			break;	
		}
		return b;
	}
	
	/** byte[] -&gt; double */
	public static double todouble(byte[] b){
		return todouble(b,FLAG_JAVA);
	}
	/** byte[] -&gt; double */
	public static double todouble(byte[] b,int flag){
		long l;
		switch (flag) {
		case BitConverter.FLAG_REVERSE:
			/*return (double)(((b[0] & 0xff) << 56) | ((b[1] & 0xff) << 48) | ((b[2] & 0xff) << 40)
					| ((b[3] & 0xff) << 32) | ((b[4] & 0xff) << 24) | ((b[5] & 0xff) << 16)
					| ((b[6] & 0xff) << 8) | ((b[7] & 0xff)));*/
			l = b[0];
			l &= 0xff;
			l |= ((long)b[1] << 8);
			l &= 0xffff;
			l |= ((long)b[2] << 16);
			l &= 0xffffff;
			l |= ((long)b[3] << 24);
			l &= 0xffffffffl;
			l |= ((long)b[4] << 32);
			l &= 0xffffffffffl;
			l |= ((long)b[5] << 40);
			l &= 0xffffffffffffl;
			l |= ((long)b[6] << 48);
			l &= 0xffffffffffffffl;
			l |= ((long)b[7] << 56);
			return Double.longBitsToDouble(l);
		case BitConverter.FLAG_JAVA:
			/*return (double)(((b[7] & 0xff) << 56) | ((b[6] & 0xff) << 48) | ((b[5] & 0xff) << 40)
					| ((b[4] & 0xff) << 32) | ((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16)
					| ((b[1] & 0xff) << 8) | ((b[0] & 0xff)));*/
			l = b[7];
			l &= 0xff;
			l |= ((long)b[6] << 8);
			l &= 0xffff;
			l |= ((long)b[5] << 16);
			l &= 0xffffff;
			l |= ((long)b[4] << 24);
			l &= 0xffffffffl;
			l |= ((long)b[3] << 32);
			l &= 0xffffffffffl;
			l |= ((long)b[2] << 40);
			l &= 0xffffffffffffl;
			l |= ((long)b[1] << 48);
			l &= 0xffffffffffffffl;
			l |= ((long)b[0] << 56);
			return Double.longBitsToDouble(l);
		default:
			throw new IllegalArgumentException("BitConverter:todouble");
		}
	}
	
	/** int -&gt; byte[] */
	public static byte[] getBytes(int i) {
		return getBytes(i, FLAG_JAVA);
	}
	/** int -&gt; byte[] */
	public static byte[] getBytes(int i, int flag) {
		byte[] b = new byte[4];
		switch (flag) {
		case BitConverter.FLAG_JAVA:
			b[0] = (byte) ((i >> 24) & 0xff);
			b[1] = (byte) ((i >> 16) & 0xff);
			b[2] = (byte) ((i >> 8) & 0xff);
			b[3] = (byte) (i & 0xff);
			break;
		case BitConverter.FLAG_REVERSE:
			b[3] = (byte) ((i >> 24) & 0xff);
			b[2] = (byte) ((i >> 16) & 0xff);
			b[1] = (byte) ((i >> 8) & 0xff);
			b[0] = (byte) (i & 0xff);
			break;
		default:
			break;	
		}
		return b;
	}
	
	/** byte[] -&gt; int */
	public static int toInt(byte[] b){
		return (int)(((b[0] & 0xff)<<24) | ((b[1] & 0xff)<<16) | ((b[2] & 0xff)<<8) | (b[3] & 0xff));
	}
	/** byte[] -&gt; int */
	public static int toInt(byte[] b, int flag) {
		switch(flag){
			case BitConverter.FLAG_JAVA:
				return (int)(((b[0] & 0xff)<<24) | ((b[1] & 0xff)<<16) | ((b[2] & 0xff)<<8) | (b[3] & 0xff));
			case BitConverter.FLAG_REVERSE:
				return (int)(((b[3] & 0xff)<<24) | ((b[2] & 0xff)<<16) | ((b[1] & 0xff)<<8) | (b[0] & 0xff));
			default:
				throw new IllegalArgumentException("BitConverter:toInt");
		}
	}
	
	/** long -&gt; byte[] */
	public static byte[] getBytes(long i) {
		return getBytes(i, FLAG_JAVA);
	}
	/** long -&gt; byte[] */
	public static byte[] getBytes(long i, int flag) {
		byte[] b = new byte[8];
		switch (flag) {
		case BitConverter.FLAG_JAVA:
			b[0] = (byte) ((i >> 56) & 0xff);
			b[1] = (byte) ((i >> 48) & 0xff);
			b[2] = (byte) ((i >> 40) & 0xff);
			b[3] = (byte) ((i >> 32) & 0xff);
			b[4] = (byte) ((i >> 24) & 0xff);
			b[5] = (byte) ((i >> 16) & 0xff);
			b[6] = (byte) ((i >> 8) & 0xff);
			b[7] = (byte) ((i >> 0) & 0xff);
			break;
		case BitConverter.FLAG_REVERSE:
			b[7] = (byte) ((i >> 56) & 0xff);
			b[6] = (byte) ((i >> 48) & 0xff);
			b[5] = (byte) ((i >> 40) & 0xff);
			b[4] = (byte) ((i >> 32) & 0xff);
			b[3] = (byte) ((i >> 24) & 0xff);
			b[2] = (byte) ((i >> 16) & 0xff);
			b[1] = (byte) ((i >> 8) & 0xff);
			b[0] = (byte) ((i >> 0) & 0xff);
			break;
		default:
			break;	
		}
		return b;
	}

	/** byte[] -&gt; long */
	public static long toLong(byte[] b) {
		return toLong(b, FLAG_JAVA);
	}
	/** byte[] -&gt; long */
	public static long toLong(byte[] b, int flag) {
		switch (flag) {
		case BitConverter.FLAG_JAVA:
			return (((long) (b[0] & 0xff) << 56) | ((long) (b[1] & 0xff) << 48) | ((long) (b[2] & 0xff) << 40)
					| ((long) (b[3] & 0xff) << 32) | ((long) (b[4] & 0xff) << 24) | ((long) (b[5] & 0xff) << 16)
					| ((long) (b[6] & 0xff) << 8) | ((long) (b[7] & 0xff)));
		case BitConverter.FLAG_REVERSE:
			return (((long) (b[7] & 0xff) << 56) | ((long) (b[6] & 0xff) << 48) | ((long) (b[5] & 0xff) << 40)
					| ((long) (b[4] & 0xff) << 32) | ((long) (b[3] & 0xff) << 24) | ((long) (b[2] & 0xff) << 16)
					| ((long) (b[1] & 0xff) << 8) | ((long) (b[0] & 0xff)));
		default:
			throw new IllegalArgumentException("BitConverter:toLong");
		}
	}
	
	/***/
	public static void main(String[] args){
		short s = -10;
		int i = -10000;
		long lo = -10000;
		print(BitConverter.getBytes(s));
		print(BitConverter.getBytes(i));
		print(BitConverter.getBytes(lo));
		
		System.out.println(BitConverter.toShort(BitConverter.getBytes(s)));
		System.out.println(BitConverter.toInt((BitConverter.getBytes(i))));
		System.out.println(BitConverter.toLong(BitConverter.getBytes(lo)));
	}
	private static void print(byte[] b) {
		String str = "";
		for (byte bb : b) {
			str += bb + ",";
		}
		System.out.println(str);
	}

	/** byte[] -&gt; String */
	public static String getString(byte[] by) {
		if (by == null) {
			return "";
		}
		int len = by.length;
		if (len == 0 || by.length % 2 != 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer(len / 2);
		char ch;
		for (int i = 0; i < len; i += 2) {
			ch = (char) (by[i] & 0xff | ((by[i + 1] & 0xff) << 8));
			sb.append(ch);
		}
		return sb.toString();
	}
	/** String -&gt; byte[] */
    public static byte[] getBytes(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		int len = str.length();
		int size = len * 2;
		byte[] by = new byte[size];
		short ch;
		for (int i = 0; i < size; i += 2) {
			ch = (short) str.charAt(i / 2);
			by[i] = (byte) (ch & 0xff);
			by[i + 1] = (byte) ((ch >> 8) & 0xff);
			// sb.append((char)(by[i] & 0xff | (by[i + 1] & 0xff) << 8));
		}
		return by;
	}
}

