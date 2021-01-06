package cpsc331.assignment2;

import java.util.NoSuchElementException;
import cpsc331.collections.ElementFoundException;
import java.util.ArrayList;
import cpsc331.assignment2.Treap;

/**
*
* Provides utilities that can be used to test a Treap
*
*/

public class TreapUtilities<E extends Comparable<E>, P extends Comparable<P>> {

 /**
 *
 * Reports whether an input tree is a Treap.
 *
 * @param T the tree to be tested
 * @param verbose indicates whether output is to be displayed when
 *        errors are detected
 * @return true if the input tree is a Treap, and false otherwise
 *
 */
 
 public boolean isTreap(Treap<E, P> T, boolean verbose) {
 
   Treap<E, P>.TreapNode root = T.root();
   
   if (root == null) {
   
     return true;
   
   } else {
   
     boolean nonNullElements = nonNullElements(root);
     if (!nonNullElements && verbose) {
       System.out.print("Elements stored at one or more nodes ");
       System.out.println("of this tree are null.");
     };
     
     boolean nonNullPriorities = nonNullPriorities(root);
     if (!nonNullPriorities && verbose) {
       System.out.print("Priorities stored at one or more nodes ");
       System.out.println("of this tree are null.");
     }
     
     if (!nonNullElements || !nonNullPriorities) {
     
       if (verbose) {
         System.out.print("No other properties of Treaps ");
         System.out.println("have been checked.");
       };
       return false;
     
     } else {
     
       boolean rootOK = true;
       if (root.parent() != null) {
         if (verbose) {
           System.out.println("Parent of root is not null.");
         };
         rootOK = false;
       
       }
     
       return (rootOK && BSTOrder(root, verbose)
               && heapOrder(root, verbose)
               && linksOK(root, verbose));
     
     }
   
   }
 
 }
 
 /**
 *
 * Checks whether the element stored at each node in the subtree,
 * with the input node as root, is non-null.
 * 
 * @param x the root of the subtree to be checked
 * @return true if the element stored at each node of this
 *        subtree is non-null; false otherwise
 *
 */
 
 public boolean nonNullElements(Treap<E, P>.TreapNode x) {
   
   if (x == null) {
   
     return true;
   
   } else if (x.element() == null) {
   
     return false;
   
   } else {
   
     return (nonNullElements(x.left()) && nonNullElements(x.right()));
     
   }
 
 
 }
 
 /**
 *
 * Checks whether the priority stored at each node in the subtree,
 * with the input node as root, is non-null.
 *
 * @param x the root of the subtree to be tested
 * @return true if the priority stored at each node of this
 *         subtree is non-null; false otherwise
 *
 */
 
 public boolean nonNullPriorities(Treap<E, P>.TreapNode x) {
   
   if (x == null) {
   
     return true;
   
   } else if (x.priority() == null) {
   
     return false;
   
   } else {
   
     return (nonNullPriorities(x.left()) && nonNullPriorities(x.right()));
   
   }
 
 }
 
 /**
 *
 * Checks whether elements at the nodes of the subtree with a given
 * node as root are ordered as they should for a binary search tree
 *
 * @param x the root of the subtree to be tested
 * @param verbose indicates whether outputs are to be displayed
 *        when errors are detected
 * @return true if the elements stored at the nodes of the subtree
 *        with this node as root are ordered as they should be
 *        for a binary search tree; false otherwise
 *
 */
 
 public boolean BSTOrder(Treap<E, P>.TreapNode x, boolean verbose) {
 
   if (x == null) {
   
     return true;
   
   } else {
   
     boolean orderOK = true;
     E rootValue = x.element();   
     Treap<E, P>.TreapNode leftChild = x.left();
     Treap<E, P>.TreapNode rightChild = x.right();
     
     if (leftChild != null) {
     
       E leftValue = leftChild.element();
       if (leftValue.compareTo(rootValue) >= 0) {
         if (verbose) {
           System.out.print("Left child of node storing ");
           System.out.print(rootValue.toString());
           System.out.print(" stores an element that is too large.");
         };
         orderOK = false;
       };
       orderOK = orderOK && BSTOrder(leftChild, verbose);
     
     };
     
     if (rightChild != null) {
     
       E rightValue = rightChild.element();
       if (rightValue.compareTo(rootValue) <= 0) {
         if (verbose) {
           System.out.print("Right child of node storing ");
           System.out.print(rootValue.toString());
           System.out.print(" stores an element that is too small.");
         };
         orderOK = false;
       };
       orderOK = orderOK && BSTOrder(rightChild, verbose);
     
     };
   
     return orderOK;
   
   }
 
 }
 
 /**
 *
 * Checks whether priorities at the nodes of the subtree with a given
 * node as root are ordered as they should for a MaxHeap
 *
 * @param x the root of the subtree to be checked
 * @param verbose indicates whether outputs are to be displayed
 *        when errors are detected
 * @return true if the priorities stored at the nodes of the subtree
 *        with this node as root are ordered as they should be
 *        for a MaxHeap; fale otherwise
 *
 */
 
 public boolean heapOrder(Treap<E, P>.TreapNode x, boolean verbose) {
 
   if (x == null) {
   
     return true;
   
   } else {
   
     boolean orderOK = true;
     P rootPriority = x.priority();
     Treap<E, P>.TreapNode leftChild = x.left();
     Treap<E, P>.TreapNode rightChild = x.right();
     
     if (leftChild != null) {

       P leftPriority = leftChild.priority();
       if (leftPriority.compareTo(rootPriority) > 0) {
         if (verbose) {
           E rootValue = x.element();
           System.out.print("Left child of node storing ");
           System.out.print(rootValue.toString());
           System.out.println(" has higher priority.");
         };
         orderOK = false;
       };
       orderOK = orderOK && heapOrder(leftChild, verbose);
     };
     
     if (rightChild != null) {
     
       P rightPriority = rightChild.priority();
       if (rightPriority.compareTo(rootPriority) > 0) {
         if (verbose) {
           E rootValue = x.element();
           System.out.print("Right child of node storing ");
           System.out.print(rootValue.toString());
           System.out.println(" has higher priority.");
         };
         orderOK = false;
       };
       orderOK = orderOK && heapOrder(rightChild, verbose);
     };
     
     return orderOK;
   
   }
 
 }
 
 /**
 *
 * Checks that references for parents, left and right children in
 * the subtree with a given node as root make sense
 *
 * @param x the root of the subtree to be checked
 * @param verbose indicates whether outputs are to be dispayed
 *        when errors are detected
 * @return true if the links for parents, left and right children
 *        in the subtree with this node as root are consistent;
 *        false otherwise
 *
 */
 
 public boolean linksOK(Treap<E, P>.TreapNode x, boolean verbose) {
 
   if (x == null) {
   
     return true;

   } else {
   
     boolean linksOK = true;
     Treap<E, P>.TreapNode leftChild = x.left();
     Treap<E, P>.TreapNode rightChild = x.right();
     
     if (leftChild != null) {
     
       Treap<E, P>.TreapNode leftParent = leftChild.parent();
       if (leftParent != null) {
         if (!leftParent.equals(x)) {
           if (verbose) {
             E key = x.element();
             System.out.print("Parent of left child of node storing ");
             System.out.print(key.toString());
             System.out.println(" is incorrect.");
           };
           linksOK = false;
         }
       } else {
         if (verbose) {
           E key = x.element();
           System.out.print("Parent of left child of node storing ");
           System.out.print(key.toString());
           System.out.println(" is null.");
         };
         linksOK = false;
       };
       linksOK = linksOK && linksOK(leftChild, verbose);
     
     };
     
     if (rightChild != null) {
     
       Treap<E, P>.TreapNode rightParent = rightChild.parent();
       if (rightParent != null) {
         if (!rightParent.equals(x)) {
           if (verbose) {
             E key = x.element();
             System.out.print("Parent of right child of node storing ");
             System.out.print(key.toString());
             System.out.println(" is incorrect.");
           };
           linksOK = false;
         }
       } else {
         if (verbose) {
           E key = x.element();
           System.out.print("Parent of right child of node storing ");
           System.out.print(key.toString());
           System.out.println(" is incorrect.");
         };
         linksOK = false;
       }
       linksOK = linksOK && linksOK(rightChild, verbose);
     };
   
     return linksOK;
   
   }
 
 }
 
 /**
 *
 * Checks whether an input tree is a Treap, providing a command-line
 * description of the testing being performed and additional documentation
 * if an error is found.
 *
 * @param T the tree to be tested
 *
 */
 
 public void testTree(Treap<E, P> T) {
 
   if (isTreap(T, false)) {
     System.out.println("Result is a valid treap.");
   } else {
     System.out.println("Result is not a valid treap.");
     System.out.println("");
     boolean result = isTreap(T, true);
   };
   System.out.println("");
 
 }
 
 /**
 *
 * Tests a search for an element.
 *
 * @param T the treap in which the search takes place
 * @param x the element being searched for
 * @param true if the element should be found, and false
 *        otherwise
 *
 */
 
 public void testSearch(Treap<E, P> T, E x, boolean success) {
 
   try {
   
     System.out.print("Searching for \"");
     System.out.println(x.toString() + "\"");
     Treap<E, P>.TreapNode y = T.search(x);
     if (success) {
       E e = y.element();
       if (e.equals(x)) {
        System.out.print("Search was successful, ");
        System.out.println("as expected.");
       } else {
         System.out.print("Node storing \"");
         System.out.print(e.toString());
         System.out.println("\" was incorrectly returned.");
       }
     } else {
     
       System.out.print("A \"NoSuchElementException\" was");
       System.out.println(" expected but not thrown.");
     };
     testTree(T);
     
   } catch (NoSuchElementException ex) {
   
     if (success) {
       System.out.print("A \"NoSuchElementException\" was ");
       System.out.println("incorrectly thrown.");
     } else {
       System.out.print("A \"NoSuchElementException\" was ");
       System.out.println("thrown, as expected.");
     };
     testTree(T);
        
   }
 
  }
 
  /**
  *
  * Tests the insertion of an element with a given priority.
  *
  * @param T the treap in which the insertion is performed
  * @param x the element to be inserted
  * @param p the priority to be used
  * @success true if the insertion should succeed, and false if
  *          an ElementFoundException should be thrown instead
  *
  */
 
  public void testInsert (Treap<E, P> T,
                        E x,
                        P p,
                        boolean success) {
                        
  
    try {
    
      System.out.print("Trying to insert \"");
      System.out.print(x.toString() + "\" ");
      System.out.print("with priority \"");
      System.out.println(p.toString() + "\"");
      T.insert(x, p);
      if (success) {
        if (contains(T, x)) {
          System.out.println("Operation succeeded, as expected.");
        } else {
          System.out.print("Operation failed: No exception was ");
          System.out.println("thrown, but element was not added.");
        }
      } else {
        System.out.print("An \"ElementFoundException\" was ");
        System.out.println(" expected but not thrown.");
        if (contains(T, x)) {
          System.out.print("Operation failed: The expected ");
          System.out.print("\"ElementFoundException\" was not");
          System.out.println("thrown.");
        } else {
          System.out.println("Operation failed: The expected ");
          System.out.print("\"ElementFoundException\" was not ");
          System.out.print(" thrown, and the input element is not ");
          System.out.println("in this tree.");
        }
      };
      testTree(T);
    
    } catch (ElementFoundException ex) {
    
      if (success) {
        if (contains(T, x)) {
          System.out.print("An \"ElementFoundException\" was ");
          System.out.print("incorrectly thrown, but the input ");
          System.out.println("was added.");
        } else {
          System.out.print("An \"ElementFoundException\" was ");
          System.out.print("not expected but was thrown; the input ");
          System.out.println("element is not stored.");
        }
      } else {
        if (contains(T, x)) {
          System.out.print("An \"ElementFoundException\" was ");
          System.out.println("thrown, as expected.");
        } else {
          System.out.print("An \"ElementFoundException\" was ");
          System.out.print("thrown, as expected, but the input ");
          System.out.println("element is not stored.");
        }
      };
      testTree(T);
    
    };
                        
  }
  
  /**
  *
  * Tests the deletion of a given element.
  *
  * @param T the treap from which the element is deleted
  * @param x the element of E to be deleted
  * @param success true if the deletion was successful - because
  *        the element was found and removed --- and false if a
  *        NoSuchElementException should be thrown instead
  *
  */
  
  public void testDelete (Treap<E, P> T,
                         E x,
                        boolean success) {
                        
    try {
    
      System.out.print("Trying to delete \"");
      System.out.println(x.toString() + "\".");
      T.delete(x);
      if (success) {
        if (contains(T, x)) {
          System.out.print("Operation Failed: ");
          System.out.println("Element was not removed.");
        } else {
          System.out.println("Operation succeeded, as expected.");
        }
      } else {
        System.out.print("The expected \"NoSuchElementException\"");
        System.out.println("has not been thrown.");
        if (contains(T, x)) {
          System.out.print("A \"NoSuchElementException\" was expected ");
          System.out.print("but not thrown, and the input element ");
          System.out.print("is stored.");
        } else {
          System.out.print("A \"NoSuchElementException\" was expected ");
          System.out.print("but not thrown, and the input element ");
          System.out.println("is not stored.");
        }
      };
      testTree(T);
      System.out.println("");
    
    } catch (NoSuchElementException ex) {
    
      if (success) {
        if (contains(T, x)) {
          System.out.print("A \"NoSuchElementException\" was incorrectly ");
          System.out.println("thrown, and the input element is stored.");
        } else {
          System.out.print("A \"NoSuchElementException\" was incorrectly ");
          System.out.println("thrown, and the input element is not stored.");
        }
      } else {
        if (contains(T, x)) {
          System.out.print("A \"NoSuchElementException\" was thrown, ");
          System.out.println(" but the input element is stored.");
        } else {
          System.out.print("Operation succeeded with a ");
          System.out.print("\"NoSuchElementException\" thrown, ");
          System.out.println("as expected.");
        }
      };
      testTree(T);
      System.out.println("");
    
    }
                        
  }
  
  // Checks whether a given treap stores a given element of E
  
  private boolean contains (Treap<E, P> T, E x) {
  
    try {
      Treap<E, P>.TreapNode y = T.search(x);
      return true;
    } catch (NoSuchElementException ex) {
      return false;
    }
  
  }

}