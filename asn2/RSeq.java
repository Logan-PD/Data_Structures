package cpsc331.assignment2;

import java.util.Random;

/**
*
* Provides a pseudorandom number generator with seed chosen
* using time of day.
*
* return pseudorandom number generator
*
*/

public class RSeq {

  private Integer range;
  private Random seq;

  /**
  *
  * Constructs a pseudorandom number generator returning integers
  * between 0 and limit&minus;1 if limit is a positive integer.
  * Throws an InvelidArgmentException if limit is not positive.
  *
  * @param limit number of integers that might be returned
  * @throws IllegalArgumentException if limit &le; 0
  *
  */
  
  public RSeq (Integer limit)  throws IllegalArgumentException {
  
    if (limit > 0) {
    
      range = limit;
      seq = new Random(System.currentTimeMillis());
    
    } else {
    
      throw new IllegalArgumentException(limit.toString());
    
    }

  }
  
  /**
  * 
  * Returns the next value in the sequence of integers that can
  * be produced using this pseudorandom number generator
  *
  * @return next integer (between 0 and limit&minus;1) in this
  * sequence
  *
  */
  
  public Integer next() {
  
    Integer val = seq.nextInt() % range;
    if (val < 0) {
      val = val + range;
    };
    return val;
  
  }

}