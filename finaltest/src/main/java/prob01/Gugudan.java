package prob01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Gugudan {
	private int lValue;
	private int rValue;

	public Gugudan(int lValue, int rValue) {
		this.lValue = lValue;
		this.rValue = rValue;
	}
	
	// 해시셋
	public int[] getDistinctHashSet(int[] originArr) {
	    List<Integer> originList = Arrays.stream(originArr).boxed().collect(Collectors.toList());
		List<Integer> result = new ArrayList<>();
		HashSet<Integer> distinctData = new HashSet<>(originList);
		result = new ArrayList<Integer>(distinctData);
		int[] arr = result.stream().mapToInt(Integer::intValue).toArray();
		return arr;
	}
}