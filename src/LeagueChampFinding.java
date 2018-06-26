import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
public class LeagueChampFinding {
	
	public static void main(String[] args) throws FileNotFoundException {
		int[] counters = new int[5];
		Scanner in = new Scanner(new File("filename.txt"));
		List<String> champList = new ArrayList<>();
		while(in.hasNext())
			champList.add(in.next().toLowerCase());
		List<List<String>> minList = findMin(champList);
		for(int i = 0; i < champList.size(); i++) {
			String print = "* " + champList.get(i);
			print+= ": ";
			for(int j = 0; j < minList.get(i).size() - 1; j++) {
				print+=minList.get(i).get(j);
				print+=", ";
			}
			print+= minList.get(i).get(minList.get(i).size() - 1);
			System.out.println(print);
		}
		
	}
	private static List<List<String>> findMin(List<String> list){
		return list.stream().map(a-> determine(a,list)).collect(Collectors.toList());
	}
	private static List<String> determine(String element, List<String> list) {
		int length = 1;
		while(true) {
			List<String> allOfLength = generate(length);
			List<String> result = allOfLength.stream().filter(a -> listContains(list, element, a)).collect(Collectors.toList());
			if(result.size() > 0)
				return result;
			length++;
		}
	}
	private static List<String> generate(int length){
		if(length == 0)
			return new ArrayList<String>() {{add("");}};
		List<String> prev = generate(length - 1);
		return prev.stream().flatMap(a->addOne(a).stream()).collect(Collectors.toList());
	}
	private static List<String> addOne(String a){
		List<String> allCombinations = new ArrayList<>();
		for(int j = 0; j < 26; j++) {
			char add = (char) (j+97);
			allCombinations.add(a + add);
		}
		allCombinations.add(a + "'");
		allCombinations.add(a+ "_");
		return allCombinations;
	}
	private static boolean listContains(List<String> list, String target, String substring) {
		return contains(target, substring) && list.stream().filter(a -> contains(a, substring)).count() == 1;
	}
	private static boolean contains(String word, String substring) {
		return word.matches(".*" + Arrays.stream(substring.split("")).map(a-> a+".*").collect(Collectors.joining("")));
	}
}
