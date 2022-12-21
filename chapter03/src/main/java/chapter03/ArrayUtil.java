package chapter03;

public class ArrayUtil {

	public static double[] intToDouble(int[] is) {
		double[] result = new double[is.length];
		
		for(int i=0; i<is.length; i++) {
			result[i] = is[i];
		}
		
		return result;
	}

	public static int[] doubleToInt(double[] ds) {
		int[] result = new int[ds.length];
		for(int i=0; i<ds.length; i++) {
			result[i] = (int)ds[i];
		}
		return result;
	}

	public static int[] concat(int[] is, int[] is2) {
		int[] result = new int[is.length+is2.length];
		int position=0;
		for(int i=0; i<is.length; i++) {
			result[position] = is[i];
			position++;
		}
		for(int i=0; i<is2.length; i++) {
			result[position] = is2[i];
			position++;
		}
		return result;
	}

}
