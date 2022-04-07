/**
Samuel Durigon SMD 152@pitt.edu
John C. Ramirez
CS 0445 Sec1200
*/
import java.util.*;
import java.util.Random;

public class RandIndexQueue<T> implements MyQ<T>, Indexable<T>, Shufflable
{
  protected T[] theQueue;
  protected int size, moves, front, back;

  public RandIndexQueue(int sz)
  {
    @SuppressWarnings("unchecked")
    T[] temp = (T[]) new Object[sz];
    theQueue = temp;
    size = 0;
    front = -1;
    back = -1;
  }

  /** Adds a new entry to the back of this queue.
      @param newEntry  An object to be added. */
  public void enqueue(T newEntry)
  {
    //if queue is full
    if (size == theQueue.length){
      theQueue = resize(theQueue);
    }

    //if queue is empty
    if (front == -1){
      front = 0;
      back = 0;
      theQueue[back] = newEntry;
      moves++;
      size++;
    }
    //if logical back is the end of the array
    else if (back == theQueue.length-1 && front != 0){
      theQueue[back] = newEntry;
      back = 0;
      moves++;
      size++;
    }
    //data is added normally
    else{
      back++;
      theQueue[back] = newEntry;
      moves++;
      size++;
    }
  }

  /** Removes and returns the entry at the front of this queue.
      @return  The object at the front of the queue.
      @throws  EmptyQueueException if the queue is empty before the operation. */
  public T dequeue()
  {
    //if the queue is empty
    if (size == 0)
			throw new EmptyQueueException("Queue is empty");

    T temp = theQueue[front];
    //if there is only one element
    if (front == back){
      front = -1;
      back = -1;
      size = 0;
      moves++;

    }
    //if the logical front is at the end of the array
    else if (front == theQueue.length-1){
      front = 0;
      moves++;
      size--;
    }
    //normal dequeue
    else{
      front++;
      moves++;
      size--;
    }

    return temp;

  }

  /**  Retrieves the entry at the front of this queue.
      @return  The object at the front of the queue.
      @throws  EmptyQueueException if the queue is empty. */
  public T getFront()
  {
    if (size == 0){
      throw new EmptyQueueException("Queue is empty");
    }
    else
      return theQueue[front];
  }

  /** Detects whether this queue is empty.
      @return  True if the queue is empty, or false otherwise. */
  public boolean isEmpty()
  {
    if (size == 0)
      return true;
    else
      return false;
  }

  /** Removes all entries from this queue. */
  public void clear()
  {
    for (int i=0; i<theQueue.length; i++)
      theQueue[i] = null;
    size = 0;
    front = -1;
    back = -1;

  }

  private T[] resize(T[] oldQueue)
  {
    int count = front;
    //creates a new array of double length
    T[] temp = (T[]) new Object[oldQueue.length *2];
    //copies all values from the old array into the new one
    for (int i=0; i<oldQueue.length; i++)
    {
      if (count == oldQueue.length-1){
        temp[i] = oldQueue[count];
        count = 0;
      }else{
        temp[i] = oldQueue[count];
        count++;
      }
    }
    //sets the new front and back values for the new array and returns
    this.front = 0;
    this.back = this.size()-1;
    return temp;
  }

  // Return the number of items currently in the MyQ.  Determining the
	// length of a queue can sometimes very useful.
	public int size()
  {
    return size;
  }

	// Return the length of the underlying data structure which is storing the
	// data.  In an array implementation, this will be the length of the array.
	// This method would not normally be part of a queue but is included here to
	// enable testing of your resizing operation.
	public int capacity()
  {
    return theQueue.length;
  }

	// Methods to get and set the value for the moves variable.  The idea for
	// this is the same as shown in Recitation Exercise 2 -- but now instead
	// of a separate interface these are incorporated into the MyQ<T>
	// interface.  The value of your moves variable should be updated during
	// an enqueue() or dequeue() method call.  However, any data movement required
	// to resize the underlying array should not be counted in the moves.
	public int getMoves()
  {
    return moves;
  }

	public void setMoves(int val)
  {
    moves = val;
  }

  // Get and return the value located at logical location i in the implementing
	// collection, where location 0 is the logical beginning of the collection.
	// If the collection has fewer than (i+1) items, throw an IndexOutOfBoundsException
  public T get(int i)
  {
    int distance = theQueue.length-front;
    //if (this.size() < i+1)
      //throw new IndexOutOfBoundsException("Collection has fewer than i+1 items");
    //else
    {
      //array wraps around
      if (front > back)
      {
        for (int j = 0; j<this.size(); j++)
        {
          //before the wrap
          if (j+front < theQueue.length-1)
          {
            if (j==i)
              return theQueue[j+front];
            else
              continue;
          //at the phsyical end of the array
        }else if (j+front == theQueue.length-1)
          {
            if (j==i)
              return theQueue[j+front];
            else
              continue;
          //after the wrap
        }else if (j+front > theQueue.length)
          {
            if (j==i)
              return theQueue[j-distance];
            else
              continue;
          }
        }
      //array does not wrap
      }else if (front < back)
      {
        for (int k=0; k<this.size(); k++)
        {
          if (k==i)
            return theQueue[k+front];
          else
            continue;
        }
      }
      return theQueue[i];
    }
  }
	// Assign item to logical location i in the implementing collection, where location
	// 0 is the logical beginning of the collection.  If the collection has fewer than
	// (i+1) items, throw an IndexOutOfBoundsException
public void set(int i, T item)
  {
    int distance = theQueue.length-front;
    if (this.size() < i+1)
      throw new IndexOutOfBoundsException("Collection has fewer than i+1 items");
    else
    {
      //array wraps around
      if (front > back)
      {
        for (int j = 0; j<this.size(); j++)
        {
          //before the wrap
          if (j+front < theQueue.length-1)
          {
            if (j==i)
              theQueue[j+front] = item;
            else
              continue;
          //at the phsyical end of the array
        }else if (j+front == theQueue.length-1)
          {
            if (j==i)
              theQueue[j+front] = item;
            else
              continue;
          //after the wrap
        }else if (j+front > theQueue.length)
          {
            if (j==i)
              theQueue[j-distance] = item;
            else
              continue;
          }
        }
      //array does not wrap
      }else if (front < back)
      {
        for (int k=0; k<this.size(); k++)
        {
          if (k==i)
            theQueue[k+front] = item;
          else
            continue;
        }
      }
    }
  }
  // Reorganize the items in the object in a pseudo-random way.  The exact
	// way is up to you but it should utilize a Random object (see Random in
	// the Java API).  Note that this should not change the size of the
	// collection.
public void shuffle()
  {
    int rint1, rint2;
    Random rand = new Random();
    T temp;
    //Initilizes two random integers for 2 random spots in the array. The
    //  loop will go through 3 times to shuffle it more than just once.
    for (int i=0; i<this.size()*3; i++)
    {
      rint1 = rand.nextInt(this.size());
      rint2 = rand.nextInt(this.size());
      temp = this.get(rint1);
      this.set(rint1, this.get(rint2));
      this.set(rint2, temp);
    }
  }

  //Copy constructor that makes a new RandIndexQueue that is logically
  //  equivalent to the old one. The front and back variables are not the same.
  public RandIndexQueue(RandIndexQueue<T> old)
  {
    theQueue = (T[]) new Object[old.capacity()];
    for (int i=0; i<theQueue.length - 1; i++){
      theQueue[i] = old.get(i);
    }
    front = 0;
    size = old.size();
    back = size-1;
  }

  //Returns true if the queus are logically equivalent.
  public boolean equals(RandIndexQueue<T> rhs)
  {
    for (int i=0; i<theQueue.length; i++){
      if (this.toString().equals(rhs.toString()))
        return true;
    }
    return false;
  }

  //Converts the RandIndexQueue into a string to be printed at the
  //  command line.
  public String toString()
  {
    String temp = "Contents: ";
    int count = front;
    for (int i = 0; i < this.size(); i++)
    {
      temp += " " + this.get(i);
    }
    return temp;
  }
}
