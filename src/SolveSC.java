import java.util.*;

public class SolveSC {
	
	static int groundSetSize;
	static Set<Integer> groundSet = new HashSet<Integer>();
	static List<Subset> subSets = new ArrayList<Subset>();
	static List<Integer> setCover = new ArrayList<Integer>();
	
	public static void main(String[] args) {
				
		initialize(args);
		
		while(groundSetIsNotCovered()){
			setCover.add(indexOfSubsetWithMinmumWeightPerElement());
		}
		printResults();
	}

	
	private static void printResults() {		
		
		System.out.println("Ground set: " + groundSet);

		for(Subset subset: subSets) {
			System.out.println(subset);
		}
		
		Collections.sort(setCover);
		System.out.println("Indicies of set cover: " + setCover);	
	}

	public static void initialize(String[] args) {
	
		groundSetSize = Integer.parseInt(args[0]); 
		
		for(int i=1, subsetIndex = 0; i < args.length; i++, subsetIndex++){		
			int weight = Integer.parseInt(args[i]);
			Set<Integer> set = new HashSet<Integer>();
			i++;
			
			while(Integer.parseInt(args[i]) != 0){
				set.add(Integer.parseInt(args[i]));
				i++;
			}
			
			groundSet.addAll(set);
			subSets.add(new Subset(weight, subsetIndex, set));
		}
		
		if (groundSetSize != groundSet.size()) { 
			throw new IllegalArgumentException("Invaild argument. Size of ground set should be " + groundSet.size()); 
		}
	}

	private static int indexOfSubsetWithMinmumWeightPerElement() {
		Set<Double> tempSet = new HashSet<Double>();
		int l = -1;
		for (Subset subset: subSets){
			if (!setCover.contains(subset.index) && subset.set.size() > 0){
				tempSet.add(subset.weigtPerElement);
				if (subset.weigtPerElement == Collections.min(tempSet)){
					l = subset.index;
				}	
			}
		}
		return l;
	}

	private static boolean groundSetIsNotCovered() {
		Set<Integer> tempSet = new HashSet<Integer>();
		for(int i: setCover){
			tempSet.addAll(subSets.get(i).set);
		}
		return (tempSet.size() < groundSetSize);
	}
}

class Subset {
	int weight;
	int index;
	Set<Integer> set;
	double weigtPerElement;
	
	public Subset(int weight, int index, Set<Integer> set){
		this.weight = weight;
		this.index = index;
		this.set = set;
		this.weigtPerElement = (double)weight / (double)set.size();
	}
	
	public String toString() { 
		return "Subset " + this.index + ": Weight: " + this.weight+ ", " + this.set;
	} 
}