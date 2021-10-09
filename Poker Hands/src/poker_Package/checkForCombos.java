package poker_Package;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// Straight Flush
// Four of a Kind
// Full House
// Flush
// Straight
// Three of a Kind
// Two Pairs
// Pair
// High Card


public class checkForCombos {
	
	// Given an array of integers returns the most common integer and how many times it occurs
	public static int[] findMost(int[]values)
	{
		int common = 0;
		int commonNo = 0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(int i : values)
		{
			Integer count = map.get(i);
			map.put(i, count!= null ? count+1 : 1);
		}
		
		for(Map.Entry<Integer, Integer> entry : map.entrySet())
		{
			if(entry.getValue() > commonNo)
			{
				common = entry.getKey();
				commonNo = entry.getValue();
			}
		}
		int[] pair = new int[2];
		pair[0] = common;
		pair[1] = commonNo;
		return pair;
	}
	
	// Given an array of integers returns the least common integer and how many times it occurs
	public static int[] findLeast(int[]values)
	{
		int uncommon = 100;
		int uncommonNo = 100;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(int i : values)
		{
			Integer count = map.get(i);
			map.put(i, count!= null ? count+1 : 1);
		}
		
		for(Map.Entry<Integer, Integer> entry : map.entrySet())
		{
			if(entry.getValue() < uncommonNo)
			{
				uncommon = entry.getKey();
				uncommonNo = entry.getValue();
			}
		}
		
		int[] pair = new int[2];
		
		pair[0] = uncommon;
		pair[1] = uncommonNo;
		return pair;
	}
	
	// Finds the largest value
	public static int findMax(int[] values)
	{
		int max = values[0];
		
		for(int i : values)
		{
			if(i > max)
			{
				max = i;
			}
		}
		
		return max;
	}
	
	// Returns true if the values are consecutive, false otherwise
	public static boolean consecutive(int[] values)
	{
		int[] copy = values;
		for(int i = 0; i < values.length-1; i++)
		{
			if(copy[i] != copy[i+1]+1)
			{
				return false;
			}
		}
		return true;
	}
	
	// Removes an integer from an array
	public static int[] removeLeast(int[] values, int least)
	{
		int[] modified = new int[4];
		int index = 0;
		
		for(int i : values)
		{
			if(i != least)
			{
				modified[index] = i;
				index+=1;
			}
		}
		
		Arrays.sort(modified);
		
		return modified;
	}
	
	// Checks two arrays to find the highest integer that is in only one array
	public static int[] checkHighCard(int[] whiteValues, int[] blackValues)
	{
		int[] whiteCopy = whiteValues;
		
		int[] blackCopy = blackValues;
		
		for(int i = 4; i >= 0; i--)
		{
			if(whiteCopy[i] > blackCopy[i])
			{
				int[] white = new int[2];
				white[0] = 0;
				white[1] = whiteCopy[i];
				return white;
			}
			if(blackCopy[i] > whiteCopy[i])
			{
				int[] black = new int[2];
				black[0] = 1;
				black[1] = blackCopy[i];
				return black;
			}
		}
		return null;
	}
	
	
	// Checks a given hand and returns a point value based on how good the hand is.
	public static int checkCombos(String[] suits, int[] values)
	{
		int points = 0;
		
		int numOfCommonValues = Hands.noSameValues(values);
		int numOfCommonSuits = Hands.noSameSuits(suits);
		
		int max = findMax(values);
		String face = Hands.convertback(max);
		
		// Straight flush and Straight
		if(consecutive(values))
		{
			// Straight flush
			if(numOfCommonSuits == 10)
			{
				System.out.print("Straight flush " + face + " high");
				points = 130 + max;
			}
			// Straight
			else
			{
				System.out.print("Straight " + face + " high");
				points = 62 + max;
			}
		}
		// Four of a kind, Full House, Flush, Three of a Kind, Two Pair, Pair
		else
		{
			// Flush
			if(numOfCommonSuits == 5)
			{
				System.out.print("Flush " + face + " high");
				points = max + 71;
			}
			// Four of a kind, Full House, Three of a kind, Two Pair, Pair
			else
			{
				int[] pair = findMost(values);
				int common = pair[0];
				int commonNo = pair[1];
				
				// Full House or Three of a Kind
				if(commonNo == 3)
				{
					// Full House
					if(numOfCommonValues == 4)
					{
						System.out.print("Full House: with " + face);
						int[] leastPair = findLeast(values);
						int pairValue = leastPair[0] + 13;
						String leastFace = Hands.convertback(leastPair[0]);
						System.out.print(" over " + leastFace);
						points = ((common + 13) + 39) + pairValue + 16;
					}
					// Three of a Kind
					else
					{
						System.out.print("Three of a Kind - " + face);
						points = (common + 13) +39;
					}
				}
				// Four of a kind, Two Pair, Pair
				else
				{
					// Four of a Kind
					if(numOfCommonValues == 6)
					{
						System.out.print("Four of a Kind - " + face);
						points = ((common + 13) + 39) + common + 55;
					}
					// Two Pair
					else if(numOfCommonValues == 2)
					{
						int[] leas = findLeast(values);
						int[] copy = removeLeast(values, leas[0]);
						String faceOne = Hands.convertback(copy[0]);
						String faceTwo = Hands.convertback(copy[3]);
						System.out.print("Two Pair with - " + faceOne + " and " + faceTwo);
						points = (copy[0] + 13) + (copy[3] + 13);
					}
					// Pair
					else if(numOfCommonValues == 1)
					{
						System.out.print("Pair of - " + face);
						points = (common + 13);
					}
					// High Card
					else
					{
						System.out.print("High - " + face);
						points = max;
					}
				}
			}
		}
		return points;
	}
	
}
