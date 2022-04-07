/**
Samuel Durigon SMD 152@pitt.edu
John C. Ramirez
CS 0445 Sec1200
*/
import java.util.*;

public class Blackjack
{
  protected static int totalRounds, currentRound, trace, decks, dealerWin, playerWin, push;
  protected static BlackjackCards shoe, playerHand, dealerHand, discard;

  public static void main(String [] args)
  {
    //Three variables for the three arguments
    int totalRounds = Integer.parseInt(args[0]);
    int decks = Integer.parseInt(args[1]);
    int trace = Integer.parseInt(args[2]);
    System.out.println("\nBeginning a game of Blackjack with " + totalRounds + " rounds and " + decks + " decks!");
    System.out.println();

    //creates shoe
    System.out.println("Creating shoe of " + decks + " decks...");
    shoe = new BlackjackCards(52 * decks);
    discard = new BlackjackCards(52 * decks);
    for (int i = 0; i < decks; i++)
    {
      for (Card.Suits suit : Card.Suits.values())
      {
        for (Card.Ranks rank : Card.Ranks.values())
        {
          shoe.enqueue(new Card(suit, rank));
        }
      }
    }
    shoe.shuffle();
    System.out.println("Shoe created!");

    //playing rounds
    playerWin = 0;
    dealerWin = 0;
    push = 0;
    currentRound = 1;
    //main loop for the rounds of the game
    for (int j = 0; j < totalRounds; j++)
    {
      //these if statements allow only the traced rounds to be shown
      if (j < trace){
        System.out.println();
        System.out.println("Round " + currentRound + " beginning!");
        System.out.println();
      }

      //creates a new hand each round of 21, as this is the
      //  theoretical max amount of cards you could ever have in your hand
      playerHand = new BlackjackCards(21);
      dealerHand = new BlackjackCards(21);


      if (j < trace)
      {
        System.out.println("Dealing cards");
        System.out.println();
      }

      //deals cards
      playerHand.enqueue(shoe.dequeue());
      dealerHand.enqueue(shoe.dequeue());
      playerHand.enqueue(shoe.dequeue());
      dealerHand.enqueue(shoe.dequeue());

      if (j < trace)
      {
        System.out.println("Player's hand: " + playerHand.toString() + ": " + playerHand.getValue());
        System.out.println("Dealer's hand: " + dealerHand.toString() + ": " + dealerHand.getValue());
        System.out.println();
      }

      //checks for blackjacks in both player and dealer hand to determine
      // if there is a push or an immediate winner
      if (playerHand.getValue() == 21 && dealerHand.getValue() == 21)
      {
        if (j < trace)
          System.out.println("\nThat's a push!");
        push++;
        roundEnd();
        continue;
      }else if (playerHand.getValue() == 21)
      {
        if (j < trace)
          System.out.println("\nResult: Player wins with Blackjack!");
        playerWin++;
        roundEnd();
        continue;
      }else if (dealerHand.getValue() == 21)
      {
        if (j < trace)
          System.out.println("\nResult: Dealer wins with Blackjack!");
        dealerWin++;
        roundEnd();
        continue;
      }

      //this is the loop for the player's hits. the player will continuously
      //  hit until their hand reaches 17 or more
      while (playerHand.getValue() < 17)
      {
        playerHand.enqueue(shoe.dequeue());
        playerHand.getValue();
        if (j < trace)
          System.out.println("Player hits: " + playerHand.get(playerHand.size()-1));

      }

      //checks player hand for a bust, otherwise the player stands
      if (playerHand.getValue() > 21){
        if (j < trace){
          System.out.println("\nPlayer busts! " + playerHand.toString() + " : " + playerHand.getValue());
          System.out.println("Result: Dealer Wins!");
        }
        dealerWin++;
        roundEnd();
        continue;
      }
      else
      {
        if (j < trace)
        {
          System.out.println("Player stands! " + playerHand.toString() + " : " + playerHand.getValue());
        }
      }

      //the dealer hits in the same way as the player
      while (dealerHand.getValue() < 17)
      {
        dealerHand.enqueue(shoe.dequeue());
        dealerHand.getValue();
        if (j < trace)
          System.out.println("Dealer hits: " + dealerHand.get(dealerHand.size()-1));

      }

      //checks dealer hand for a bust, otherwise the dealer stands
      if (dealerHand.getValue() > 21){
        if (j < trace){
          System.out.println("\nDealer busts! " + dealerHand.toString() + " : " + dealerHand.getValue());
          System.out.println("Result: Player Wins!");
        }
        playerWin++;
        roundEnd();
        continue;
      }
      else
      {
        if (j < trace)
        {
          System.out.println("Dealer stands! " + dealerHand.toString() + " : " + dealerHand.getValue());
        }
      }

      //checks final result. Whichever person has a higher value wins, or if
      //  there is a tie its a push and the round ends
      if (playerHand.getValue() > dealerHand.getValue())
      {
        if (j < trace)
          System.out.println("\nResult: Player Wins!");
        playerWin++;
        roundEnd();
        continue;
      }
      else if (dealerHand.getValue() > playerHand.getValue())
      {
        if (j < trace)
          System.out.println("\nResult: Dealer Wins!");
        dealerWin++;
        roundEnd();
        continue;
      }
      else
      {
        if (j < trace)
          System.out.println("\nThat's a push!");
        push++;
        roundEnd();
        continue;
      }
    }
    System.out.println();
    System.out.println("--------------------------------------------");
    System.out.println();
    System.out.println("After " + totalRounds + " rounds, here are the results: ");
    System.out.println("Dealer Wins: " + dealerWin);
    System.out.println("Player Wins: " + playerWin);
    System.out.println("Pushes: " + push);
    System.out.println("Thanks for playing!");

  }

  //roundEnd method to help save time
  public static void roundEnd()
  {
    //emptying the player's hand into discard
    while (playerHand.size() != 0)
			discard.enqueue(playerHand.dequeue());
    //emptying the dealer's hand into discard
		while (dealerHand.size() != 0)
			discard.enqueue(dealerHand.dequeue());
    currentRound++;

    //when the shoe has less than 1/4 its capacity, it merges with discard
    //  and shuffles
    if(shoe.size() < shoe.capacity()/4)
    {

      while (discard.size() > 0)
      {
        shoe.enqueue(discard.dequeue());

      }
      shoe.shuffle();
      System.out.println();
      System.out.println("Shuffling shoe at round " + currentRound);
    }
  }
}
