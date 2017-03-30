// GP/src/utils
package utils;

import java.util.List;
import javax.swing.JFrame;

public class SortingAlgorithms {

 public void insertionSort(int[] arr) {
  for(int i = 0; i < arr.length - 1; i++) {
	          int j = i;
     while(j >= 0 && arr[j] > arr[j + 1]) {
	   int temp = arr[j + 1];
		            arr[j + 1] = arr[j];
    arr[j] = temp;
	    j--;
		     }
}
  
	  System.out.println(Arrays.toString(arr));
}

public void bubbleSort(int[] arr) {
    for(int i = arr.length - 1; i > 1; i--) {
  for(int j = 0; j < i; j++) {
	     if(arr[j] > arr[j + 1]) {
		 int temp = arr[j + 1];
      arr[j + 1] = arr[j];
          arr[j] = temp;
                       }
   }
 }
		
    System.out.println(Arrays.toString(arr));
}

	/**
	 * @param args
	 */
public static void main(String[] args) {
int[] a1 = {-1, 45, 55, 4, 2, 9, 23, -3, 11};
int[] a2 = {55, 45, 23, 11, -1,  4, 2, 9, -3};
int[] b1 = {-1, 45, 55, 4, 2, 9, 23, -3, 11};
int[] b2 = {55, 45, 23, 11, -1,  4, 2, 9, -3};
}

}
