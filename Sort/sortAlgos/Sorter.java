package sortAlgos;

public class Sorter {
	private int[] numbers;

	public void sort(int[] values) {
		// check for empty or null array
		if (values ==null || values.length==0){
			return;
		}
		this.numbers = values;
//		quicksort(0, number);
		System.out.println("INSERTIONSORT");
		insertionSort(values);
	}
	
	void insertionSort(int[] arr) {
		for(int i = 0; i < arr.length - 1; i++) {
			int j = i;
			while(j >= 0 && arr[j] > arr[j + 1]) {
				int temp = arr[j + 1];
				arr[j + 1] = arr[j];
				arr[j] = temp;
				j--;
			}
		}
		
		for(int n : numbers) {
			System.out.println(n);
		}
	}
	
	void bubbleSort(int[] arr) {
		for(int i = arr.length - 1; i > 1; i--) {
			for(int j = 0; j < i; j++) {
				if(arr[j] > arr[j + 1]) {
					int temp = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = temp;
				}
			}
		}
		
		for(int n : arr) {
			System.out.println(n);
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = {-1, 45, 55, 4, 2, 9, 23, -3, 11};
		int[] a2 = {-1, 45, 55, 4, 2, 9, 23, -3, 11};
		new Sorter().sort(a);
		new Sorter().sort(a2);
		System.out.println("BUBBLESORT");
		int[] b = {-1, 45, 55, 4, 2, 9, 23, -3, 11};
		int[] b2 = {55, 45, 23, 11, -1,  4, 2, 9, -3};
		new Sorter().bubbleSort(b);
		new Sorter().bubbleSort(b2);
	}

}
