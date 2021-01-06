package cpsc331.assignment3;

import java.util.ArrayList;

/**
*
* Provides a hybrid algorithm to sort the elements of an array based on
* Merge Sort.
*
* The methods written as well as the loop invariants were based off
* of Dr. Eberlys slides.
*
* Logan Perry-Din
* UCID: 30070661
* CPSC 331 fall 2019
* assignment3
*
*/

public class BetterMergeSort<T extends Comparable<T>> {

  // Data Fields

  private final int THRESHOLD;

  /**
  *
  * Constructs a hybrid sorting algorithm, based on Merge Sort, using
  * a default value (4) for the threshold.
  *
  */

  public BetterMergeSort() {

    THRESHOLD = 4;

  }

  /**
  *
  * Constructs a hybrid sorting algorithm, based on Merge sort, using
  * an input value for the threshold.
  *
  * @param threshold the value to be used as threshold
  * @throws IllegalArgumentException if the input value is less than or
  *   equal to zero
  *
  */

  public BetterMergeSort(int threshold) throws IllegalArgumentException {

    if (threshold >= 1) {

      THRESHOLD = threshold;

    } else {

      throw new IllegalArgumentException("The input threshold"
                                              + " must be positive.");

    }

  }

  /**
  *
  * Sorts an input ArrayList; does not copy in place.
  *
  * @param A the ArrayList to be sorted
  * @return an ArrayList with the same type and length of A, whose elements
  *   are those of&nbsp;A, reordered in non-decreasing order<br><br>
  *
  * Precondition:<br>
  * <ol style="list-style-type: lower-alpha">
  * <li> An ArrayList&nbsp;A, whose entries are non-null value of
  *      type&nbsp;T, is given as input.
  * </li>
  * </ol>
  * Postcondition:<br>
  * <ol style="list-style-type: lower-alpha">
  * <li> A ArrayList with the same type and length of&nbsp;A is returned as
  *      output. The entries of the output ArrayList are the entries
  *      of&nbsp;A, reordered (with multiplicities preserved) in
  *      non-decreasing order.
  * </li>
  * </ol>
  *
  */

  public ArrayList<T> sort(ArrayList<T> A) {

    if(A.size() <= THRESHOLD){
      // if size falls below threshold, use insertionSort
      ArrayList<T> B = copy(A);
      insertionSort(B);
      return B;

    }else{  // else recursively use mergesort
      // make two new empty arraylists
      ArrayList<T> B1 = new ArrayList<T>();
      ArrayList<T> B2 = new ArrayList<T>();
      // Split the input array in half
      split(A,B1,B2);
      // recursively sort the smaller arraylists
      ArrayList<T> C1 = sort(B1);
      ArrayList<T> C2 = sort(B2);
      // merge the sorted arraylists
      return merge(C1,C2);
    }
  }

  //
  // Returns a copy of an input ArrayList
  //
  // Precondition:
  // a) An ArrayList A with positive length, whose entries are non-null
  //    values with type T, is given as input.
  // Postcondition:
  // a) An ArrayList with the same type, length and contents of A (so that
  //    the entry in position j of this ArrayList is the same as the
  //    as the entry in position j of A, for 0 <= j <= A.size()-1) is
  //    returned as output.


  private ArrayList<T> copy(ArrayList<T> A) {

    ArrayList<T> B = new ArrayList<T>();

    // Loop invariant:
    // a) i is an integer such that 0 <= i <= A.size()
    // b) for every element of B at index j for 0 <= j < i
    //    B[j] = A[j]

    int i = 0;
    while(i < A.size()){
      B.add(A.get(i));
      i++;
    }
    return B;
  }

  //
  // Sorts an input ArrayList in place
  //
  // Precondition:
  // a) An ArrayList B with positive size, whose entries are non-null
  //    values with type T, is given as input.
  // Postcondition:
  // b) The entries of B have been reordered, but otherwise unchanged,
  //    so that they are listed in nondecreasing order.
  //
  // Method writen based off of Dr. Eberlys slides
  //

  public void insertionSort(ArrayList<T> B) {

    // Loop invariant
    // a) B is an input array with length n>= 1 storing values from some
    //    ordered type T.
    // b) The entries of B have been reordered but are otherwise unchanged.
    // c) i is an integer variable such that 1 <= i <= B.length.
    // d) B[h] <= B[h+1] for every integer h such that 0 <= h <= i-2
    int i = 1;
    while(i< B.size()){
      sortMore(B,i);
      i++;
    }
  }

  //
  // Helper method for insertionSort
  //
  // Precondition:
  //  a) A is an input ArrayList with length n>= 1 storing values from
  //    an ordered type T
  //  b) i is an input integer such that 1 <= i < n
  //  c) A[h] <= A[h+1] for every integer h such that 0 <= h <= i-2
  // Postcondition
  //  a) The entries of A have been reordered but otherwise unchanged
  //  b) A[h] <= A[h+1] for every integer h such that 0 <= h < i
  //  c) i has not been changed
  //
  private void sortMore(ArrayList<T> A, int i){

    int j = i;
    int compare = A.get(j-1).compareTo(A.get(j));

    // Loop invariant
    // a) A is an input array with length n>= 1 storing values from some
    //    ordered type T.
    // b) The entries of A have been reordered but are otherwise unchanged.
    // c) i is an input integer such that 1 <= i <= n-2,
    //    whose value has not been changed by this execution  of the loop.
    // d) j is an integer variable such that 0 <= j <= i
    // e) A[h] <= A[h+1] for every integer h such that 0 <= h <= j-2,
    //    and for every integer h such that j <= h <= i-1
    // f) if 1 <= j <= j-1 then A[j-1] <= A[j+1]
    //
    // j is a bound function for this while loop

    while( (j>0) && (compare > 0) ) { //A[j-1] > A[j]
      T tmp = A.get(j-1);
      A.set(j-1,A.get(j));
      A.set(j,tmp);
      j--;
      if(j>0){
          compare = A.get(j-1).compareTo(A.get(j));
      }
    }
  }

  //
  // Splits an input ArrayList into a pair with approximately the same size
  //
  // Precondition:
  // a) An ArrayList A with size greater than or equal to two, whose
  //    entries are non-null values with type T, is given as a first input.
  //    A pair of ArrayLists B1 and B2, the each store values with type T
  //    but that both initially have size zero, are given as the second
  //    and third inputs.
  // b) The input array A has not changed. If it has size "length" then
  //    the sizes of B1 and B2 are the ceiling and floor of length/2,
  //    respectively. For 0 <= h <= ceil(length/2)-1, the entry in
  //    position h of B1 is equal to the entry in position h of A. For
  //    ceil(length/2) <= h <= length-1, the entry in position
  //    h - ceil(length/2) of B2 is equal to the entry in position h of A.

  private void split(ArrayList<T> A, ArrayList<T> B1, ArrayList<T> B2) {

    int ceil = ((A.size() + 2 - 1) / 2);
    int floor = A.size()/2;

    int i = 0;

    //Loop invariant
    // a) i is an integer such that 0 <= i <= ceiling(A.size/2)
    // b) B1[h] <= B1[h+1] for every integer h such that 0 <= h < i
    while(i < ceil){
      B1.add(A.get(i));
      i++;
    }

    //Loop invariant
    // a) i is an integer such that 0 <= i <= floor(A.size/2)
    // b) B2[h] <= B2[h+1] for every integer h such that 0 <= h < i
    i=0;
    while(i < floor){
      B2.add(A.get(i+ceil));
      i++;
    }



  }

  // Merges a pair of sorted input ArrayLists.
  //
  // Precondition:
  // a) C1 is an ArrayList with positive size storing non-null elements
  //    from an ordered type T that is sorted in non-decreasing order -
  //    so that for every integer h such that 0 <= h <= C1.size()-2,
  //    C1.get(h) <= C1.get(h+1).
  // b) C2 is an ArrayList with positive size storing non-null elements
  //    from an ordered type T that is sorted in non-decreasing order -
  //    so that for every integer h such that 0 <= h <= C2.size()-2,
  //    C2.get(h) <= C2.get(h+1).
  // Postcondition:
  // a) An ArrayList D, storing non-null values from the ordered type T,
  //    such that D.zize() = C1.size() + C2.size(), is returned as output.
  //    The entries stored in D are the entries stored in C1 and in C2
  //    (with multiplicities preserved), rearranged in non-decreasing order
  //    - so that for every integer h such that 0 <= h <= D.size()-2,
  //    D.get(h) <= D.get(h+1)

  private ArrayList<T> merge(ArrayList<T> C1, ArrayList<T> C2) {

    int n1 = C1.size();
    int n2 = C2.size();

    ArrayList<T> D = new ArrayList<T>(n1+n2);

    int i1 = 0;
    int i2 = 0;
    int j = 0;

    int compare = C1.get(i1).compareTo(C2.get(i2));

    //Loop invariant for all three while loops
    // a) C1 is an input ArrayList with length n1 >= 1, storing elements
    //    from some ordered type T, whose entries are sorted in nondecreasing
    //    order.
    // b) C2 is an input ArrayList with length n2 >= 1, storing elements
    //    from some ordered type T, whose entries are sorted in nondecreasing
    //    order.
    // c) D is an array with length n1 + n2 storing elements from some
    //    ordered type T.
    // d) i1 is an integer variable such that 0 <= i <= n1
    // e) i2 is an integer variable such that 0 <= i <= n2
    // f) j is an integer variable such that j = i1 + i2
    // g) The first j entries of D include the first i1 entries of c1 and the
    //   first i2 entries of c2 in some order.
    // h) D[h] <= D[h+1] for every integer h such that 0 <= h <= j-2

    while( (i1 < n1) && (i2 <n2) ){
      if(compare <= 0){ // C1[i1] <= C2[i2]
        D.add(j,C1.get(i1));  //add smaller element
        i1++;
      } else{ //C2[i2] > c1[i1]
        D.add(j,C2.get(i2));
        i2++;
      }
      j++;

      if( (i1 < n1) && (i2 < n2) ){
        compare = C1.get(i1).compareTo(C2.get(i2));
      }
    }

    while(i1 < n1){ // add the rest of the elements
      D.add(j,C1.get(i1));
      i1++;
      j++;
    }

    while(i2 < n2){ // add the rest of the elements
      D.add(j,C2.get(i2));
      i2++;
      j++;
    }
    //return the sorted array
    return D;
  }

















}
