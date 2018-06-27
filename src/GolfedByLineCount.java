import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
public class GolfedByLineCount {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File("filename.txt"));
		List<String> champList = new ArrayList<>();
		while(in.hasNext())
			champList.add(in.next().toLowerCase());
		List<List<String>> minList = champList.stream().map(a-> determine(a,champList)).collect(Collectors.toList());
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
		return target.matches(".*" + Arrays.stream(substring.split("")).map(a-> a+".*").collect(Collectors.joining(""))) && list.stream().filter(a -> a.matches(".*" + Arrays.stream(substring.split("")).map(b-> b+".*").collect(Collectors.joining("")))).count() == 1;
	}
}
