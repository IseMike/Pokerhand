package poker_Package;

import java.util.Scanner;

// Finds the winner of a game of Poker.
// Uses a row of input to symbolize the hands and determines the point value of each hand.

// Straight Flush = High Card Value + 130  -> 136 - 144
// Four of a Kind = Three of a Kind + Most Common Card Value + 55 -> 111 - 135
// Full House = Three of a Kind + Pair + 16 -> 86 - 100
// Flush = value of highest card + 71 -> 77 - 85
// Straight = value of highest card + 62 -> 68 - 76
// Three of a Kind = pair + 39 -> 54 - 66
// Two Pair = pair1 + pair2 -> 31 - 53 
// Pair = value + 13 -> 15 - 27
// High Card = value -> 2 - 14

// Jack = 11
// Queen = 12
// King = 13
// Ace = 14

// Test Cases:
// playerTwo: 2H 3D 5S 9C KD  playerOne: 2C 3H 4S 8C AH
// playerTwo: 2H 4S 4C 2D 4H  playerOne: 2S 8S AS QS 3S
// playerTwo: 2H 3D 5S 9C KD  playerOne: 2C 3H 4S 8C KH
// playerTwo: 2H 3D 5S 9C KD  playerOne: 2D 3H 5C 9S KH

// Expected Outcome:
// playerOne wins. - with high card: Ace 
// playerTwo wins. - with full house: 4 over 2 
// playerTwo wins. - with high card: 9
// Tie.

public class Hands {
	
	// Converts the String face of the card to an Integer
	public static int convertFace(String face)
	{
		if(face.contains("A"))
		{
			return 14;
		}
		else if(face.contains("K"))
		{
			return 13;
		}
		else if(face.contains("Q"))
		{
			 return 12;
		}
		else if(face.contains("J"))
		{
			return 11;
		}
		else
		{
			return Integer.parseInt(face);
		}
	}
	
	// Converts an integer into its card equivalent
	public static String convertback(int value)
	{
		if(value == 14)
		{
			return("Ace");
		}
		else if(value == 13)
		{
			return("King");
		}
		else if(value == 12)
		{
			return("Queen");
		}
		else if(value == 11)
		{
			return("Jack");
		}
		else
		{
			return (String.valueOf(value));
		}
	}
	

	
	// Counts the number of cards with the same suit
	public static int noSameSuits(String[] suits)
	{
		int noOf = 0;
		for(int i = 0; i < 5; i++)
		{
			for(int j = i + 1; j < 5; j++)
			{
				if(suits[i].equals(suits[j]))
				{
					noOf+=1;
				}
			}
		}
		return noOf;
	}
	
	// Counts the number of cards with the same value
	public static int noSameValues(int[] values)
	{
		int noOf =  0;
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = i + 1; j < 5; j++)
			{
				if(values[i] == (values[j]))
				{
					noOf+=1;
				}
			}
		}
		
		return noOf;
	}

	// Given an array converts all strings to integers
	public static int[] convertToValues(String[] hand)
	{
		int handValues[] = new int[5];
		
		for(int i = 0; i < 5; i++)
		{
			String check = hand[i].substring(0,1);
			
			if(check.contains("1"))
			{
				handValues[i] = 10;
			}
			else
			{
				handValues[i] = convertFace(check);
			}
		}
		
		return handValues;
	}
	
	// Given an array of Strings, finds the point value
	public static int valueOfHand(String[] hand)
	{
		int value = 0;
		
		int handValues[] = new int[5];
		String handSuits[] = new String[5];
		
		for(int i = 0; i < 5; i++)
		{
			String check = hand[i].substring(0,1);
			
			if(check.contains("1"))
			{
				handValues[i] = 10;
				handSuits[i] = hand[i].substring(2);
			}
			else
			{
				handValues[i] = convertFace(check); 
				handSuits[i] = hand[i].substring(1);
			}
		}
		
		value = checkForCombos.checkCombos(handSuits, handValues);
		return value;
	}
	
	// Prints the players point value and the winner, if there is one.
	public static void winner(String[] playerOneHand, String[] playerTwoHand, String playerOne, String playerTwo)
	{
		System.out.print(playerOne + " has a: ");
		int playerOnePoints = valueOfHand(playerOneHand);
		System.out.println(" for " + playerOnePoints + " points.");
		
		System.out.print(playerTwo + " has a: ");
		int playerTwoPoints = valueOfHand(playerTwoHand);
		System.out.println(" for " + playerTwoPoints + " points.");
		
		
		if(playerOnePoints == playerTwoPoints)
		{
			int[] tiebreaker = checkForCombos.checkHighCard(convertToValues(playerOneHand), convertToValues(playerTwoHand));
			if(tiebreaker != null)
			{
				if(tiebreaker[0] == 0)
				{
					System.out.print(playerOne + " wins. - with high card: ");
					System.out.println(convertback(tiebreaker[1]));
				}
				else
				{
					System.out.print(playerTwo + " wins. - with high card: ");
					System.out.println(convertback(tiebreaker[1]));
				}
			}
			else
			{
				System.out.println("Both player's hands have the same ranking, Tie.");
			}
		}
		else
		{
			if(playerOnePoints >= playerTwoPoints)
			{
				System.out.println(playerOne + " wins.");
			}
			else
			{
				System.out.println(playerTwo + " wins.");
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		boolean gameRunning = true;
		
		while(gameRunning)
		{
			String playerOne[] = new String[5];
			String playerTwo[] = new String[5];
			
			String playerOneName = input.next();
			playerOne[0] = input.next();
			playerOne[1] = input.next();
			playerOne[2] = input.next();
			playerOne[3] = input.next();
			playerOne[4] = input.next();
			
			String playerTwoName = input.next();
			playerTwo[0] = input.next();
			playerTwo[1] = input.next();
			playerTwo[2] = input.next();
			playerTwo[3] = input.next();
			playerTwo[4] = input.next();
			
			winner(playerOne, playerTwo, playerOneName, playerTwoName);
			
			System.out.println("Keep Playing? Y/N");
			String answer = input.next();
			if(answer.contains("N"))
			{
				gameRunning = false;
			}
		}
		input.close();
	}
}
