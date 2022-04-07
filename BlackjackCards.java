/**
Samuel Durigon SMD 152@pitt.edu
John C. Ramirez
CS 0445 Sec1200
*/
import java.util.*;

public class BlackjackCards extends RandIndexQueue<Card>
{
  protected int test = 11;
  public BlackjackCards(int sz){
    super(sz);
  }
  //returns a single value for the current BlackjackCards object.
  public int getValue(){
    int hand = 0;
    int aces = 0;
    //if the array is empty, return 0
    if (this.size() == 0)
    {
      return hand;
      //if there are cards in the array
    }else if (this.size() >0)
    {
      //goes through array to find cards
      for (int i = 0; i<this.size(); i++)
      {
        //if the current hand plus the next card is less than 21, add the
        //  new card to the hand. If this new card is an ace, record it.
        //  If the current hand plus the next card is over 21, add
        //  value2() to the hand, ensuring aces are measured as 1 instead
        //  of 11.
        if (hand + this.get(i).value() <= 21)
        {
          hand+= this.get(i).value();
          if (test == this.get(i).value())
            aces++;
        }
        else
          hand+=this.get(i).value2();
      }
      //If the hand goes over 21 and there are aces in the hand, subtract
      // the value of the hand by 10 to convert any existing aces to 1.
      while (hand > 21 && aces > 0)
      {
        hand-=10;
        aces--;
      }
    }else
    {
      return hand;
    }
    return hand;
  }
}
