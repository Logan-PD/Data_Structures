package cpsc331.assignment2;

import java.util.NoSuchElementException;
import cpsc331.collections.ElementFoundException;
import cpsc331.assignment2.Treap;
import cpsc331.assignment2.TreapUtilities;

public class TreapTest2 {

  public static void main(String[] args) {
  
    System.out.println("");
    System.out.print("Initializing a new Treap ");
    System.out.print("storing integers, using integers ");
    System.out.println("as priorities.");
    Treap<Integer, Integer> T = new Treap<Integer, Integer>();
    TreapUtilities<Integer, Integer> Test
                       = new TreapUtilities<Integer, Integer>();
    Test.testTree(T);
    
    int  i = 0;
    while (i < 20) {
      Test.testInsert(T, i, i, true);
      i = i + 1;
    }
    
    Test.testSearch(T, -1, false);
    i = 0;
    while (i < 20) {
      Test.testSearch(T, i, true);
      i = i+1;
    };
    Test.testSearch(T, 20, false);
  
    i = 0;
    while (i < 10) {
     Test.testDelete(T, 2*i, true);
     i = i+1;
    };
    
    i = 0;
    while (i < 10) {
      Test.testSearch(T, 2*i, false);
      Test.testSearch(T, 2*i+1, true);
      i = i+1;
    };
    
    i = 0;
    while (i < 10) {
      Test.testDelete(T, 2*i, false);
      Test.testDelete(T, 2*i+1, true);
      i = i+1;
    };
    
    Test.testInsert(T, 10, 0, true);
    
  }

}