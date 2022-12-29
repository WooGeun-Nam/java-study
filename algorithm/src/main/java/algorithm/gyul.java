package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class gyul {

	public static void main(String[] args) {
		int a = solution(2,new int[] {1, 1, 1, 1, 2, 2, 2, 3});
		System.out.println(a);
	}
    public static int solution(int k, int[] tangerine) {
        ArrayList<Integer> list = (ArrayList<Integer>) Arrays.stream(tangerine).boxed().collect(Collectors.toList());

        Set<Integer> set = new HashSet<Integer>(list);
        Integer [] store = new Integer[set.size()];
        int index = 0;
        for (Integer str : set) {
        	store[index] = Collections.frequency(list, str);
        	index++;
        }
        
        Arrays.sort(store, Collections.reverseOrder());
        int answer = 0;
        for(int i=0; i<store.length; i++) {
        	k = k-store[i];
        	answer++;
        	if(k<=0) {
        		break;
        	}
        }
        
        return answer;
    }
}
