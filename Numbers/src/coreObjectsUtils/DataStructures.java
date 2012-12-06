package coreObjectsUtils;

import java.util.Set;
import java.util.TreeSet;

public class DataStructures {

	/*
	 * This will determine if two sets have an empty intersection.
	 * It is done by instiating a new set, adding both of the input sets to it, 
	 *      and seeing if the result has the size of the input sets put together. 
	 * This works using the Set interface in Java since it won't add an element to a set
	 *      if the element already exists in the set
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean isIntersectionEmpty(Set SetOne, Set SetTwo){
		Set masterSet = new TreeSet();
		
		masterSet.addAll(SetOne); 
		masterSet.addAll(SetTwo);
		
		if(SetOne.size() + SetTwo.size() == masterSet.size()){
			return true;
		}
		else{
			return false;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set[] SplitSetbySet(Set SetToSplit, Set SetToSplitBy){
		
		/*this method splits the SetToSplit into two sets:
		 *     0: elements that are in SetToSplitBy
		 *     1: elements that are not in SetToSplitBy
		 */
		Set[] theTwoSets = new Set[2];
		Set SetToSplitMinusSetToSplitBy = new TreeSet();
		Set SetToSplitByMinusSetToSplit = new TreeSet();
		Set SetIntersection = new TreeSet();
		
		SetToSplitMinusSetToSplitBy.addAll(SetToSplit);
		SetToSplitMinusSetToSplitBy.removeAll(SetToSplitBy);
		
		SetToSplitByMinusSetToSplit.addAll(SetToSplitBy);
		SetToSplitByMinusSetToSplit.removeAll(SetToSplit);
				
		SetIntersection.addAll(SetToSplit);
		SetIntersection.addAll(SetToSplitBy);
		SetIntersection.removeAll(SetToSplitMinusSetToSplitBy);
		SetIntersection.removeAll(SetToSplitByMinusSetToSplit);
		
		theTwoSets[0] = SetIntersection;
		theTwoSets[1] = SetToSplitMinusSetToSplitBy;
		
		
		return theTwoSets;
	}
	
	
}
