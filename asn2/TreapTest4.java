package cpsc331.assignment2;

import java.util.NoSuchElementException;
import cpsc331.collections.ElementFoundException;
import cpsc331.assignment2.Treap;
import cpsc331.assignment2.RSeq;
import cpsc331.assignment2.TreapUtilities;

public class TreapTest4 {

  public static void main(String[] args) {
  
    System.out.println("");
    System.out.print("Initializing a new Treap ");
    System.out.print("storing integers, using integers ");
    System.out.println("as priorities.");
    Treap<Integer, Integer> T = new Treap<Integer, Integer>();
    TreapUtilities<Integer, Integer> Test
                       = new TreapUtilities<Integer, Integer>();
    Test.testTree(T);
    
    RSeq eSeq = new RSeq(100);
    RSeq pSeq = new RSeq(1000);
    boolean[] contents = new boolean[100];
    
    int i = 0;
    while (i < 100) {
      contents[i] = false;
      i = i+1;
    };
    System.out.println("");
    checkContents(T, contents);
    System.out.println("");
    System.out.println("");
    
    i = 0;
    while (i < 100) {
      int e = eSeq.next();
      if (contents[e]) {
        Test.testInsert(T, e, pSeq.next(), false);
      } else {
        Test.testInsert(T, e, pSeq.next(), true);
        contents[e] = true;
      };
      i = i + 1;
    }
    
    System.out.println("");
    checkContents(T, contents);
    System.out.println("");
    System.out.println("");
  
    i = 0;
    while (i < 50) {
      int e = eSeq.next();
      if (contents[e]) {
        Test.testDelete(T, e, true);
        contents[e] = false;
      } else {
        Test.testDelete(T, e, false);
      };
     i = i+1;
    };
    
    System.out.println("");
    checkContents(T, contents);
    System.out.println("");
    System.out.println("");
    
    i = 0;
    while (i < 50) {
      if (contents[i]) {
       Test.testDelete(T, i, true);
       contents[i] = false;
      } else {
        Test.testDelete(T, i, false);
      };
      i = i+1;
    };
    
    System.out.println("");
    checkContents(T, contents);
    System.out.println("");
    System.out.println("");
    
    i = 0;
    while (i < 50) {
     if (contents[99 - i]) {
       Test.testDelete(T, 99 - i, true);
       contents[99 - i] = false;
     } else {
       Test.testDelete(T, 99 - i, false);
     };
     i = i+1;
    };
    
    System.out.println("");
    checkContents(T, contents);
    System.out.println("");
    System.out.println("");
    
    Test.testInsert(T, 10, 0, true);
    
  }
  
  // Checks whether the contents of T are as expected
  
  private static void checkContents(Treap<Integer, Integer> T,
                             boolean[] contents) {
                             
    TreapUtilities<Integer, Integer> Test
                = new TreapUtilities<Integer, Integer>();
    boolean consistent = true;
    Integer i = 0;
    while (i < 100) {
      if (consistent) {
        if (contents[i]) {
          if (!contains(T, i)) {
            System.out.print("Contents ot Treap are not ");
            System.out.println("as expected.");
            System.out.print(i.toString());
            System.out.println(" was expected, but not found.");
            consistent = false;
          }
        } else {
          if (contains(T, i)) {
            System.out.print("Contents of Treap are not ");
            System.out.println("as expected.");
            System.out.print(i.toString());
            System.out.println(" was not expected, but was found.");
            consistent = false;
          }
        }
      } else {
        if (contents[i]) {
          if (!contains(T, i)) {
            System.out.print(i.toString());
            System.out.println(" was expected, but not found.");
          }
        } else {
          if (contains(T, i)) {
            System.out.print(i.toString());
            System.out.println(" was not expected, but was found.");
          }
        }
      };
      i = i + 1;
    
    };
     if (consistent) {
       System.out.println("Contents of Treap are as expected.");
    
     };
                             
  }
  
  // Checks whether a given treap stores a given element of E
  
  private static boolean contains (Treap<Integer, Integer> T, Integer x) {
  
    try {
      Treap<Integer, Integer>.TreapNode y = T.search(x);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  
  }

}