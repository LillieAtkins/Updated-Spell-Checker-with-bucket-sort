import java.util.ArrayList;

public class BucketTable {
	
	private ArrayList<Word>[] lists;

	/*
	 * This is the constructor for the BucketTable. It takes in nothing and returns nothing. But it creates an array
	 * of Word arraylists where each arraylist has been initialized to a new Word arraylist.
	 */
	public BucketTable() {
		this.lists = (ArrayList<Word>[])new ArrayList[52];
		for(int x = 0; x < 52; x++) {
			this.lists[x] = new ArrayList<Word>();
		}
	}
	
	/*
	 * This function is the getter for the array of Word arraylists, which is called lists. It takes in nothing and returns
	 * this.lists.
	 */
	public ArrayList<Word>[] getLists() {
		return this.lists;
	}
	
	/*
	 * This is the setter for this.lists. It takes in a new array of Word arraylists and sets that equal to this.lists.
	 * It then initializes each of the arraylists to a new Word arraylist.
	 */
	public void setLists(ArrayList<Word>[] new_list) {
		this.lists = new_list;
		for(int x = 0; x < 52; x++) {
			this.lists[x] = new ArrayList<Word>();
		}
	}
	
	/*
	 * This function takes in a word and adds it to the appropriate Word arraylist in the array this.lists based on the
	 * ascii value of the first letter in the word. 
	 */
	public void insert(Word word) {
		char ch = word.getWord().charAt(0); //to get the first letter of the word need to apply getWord() because charAt() works on strings not Words
		int ascii = (int) ch; //take the int of a character to get its ascii value
		if(Character.isLowerCase(ch)) { //lower and upper need to be separate as the upper case and lower case letters do not have ascii values that are sequential (there are some symbols in between so to get the index right for lower case there needs to be 7 + 65 subtracted from the ascii)
			lists[ascii-72].add(word); 
		}
		if(Character.isUpperCase(ch)) {
			lists[ascii-65].add(word); //this should be -65 because A has an ascii value of 65 so this way words starting with A will be put into the index 0 and so on
		}
	}
	
	/*
	 * This function takes in an arraylist of Words and an integer first and an integer last. It returns nothing. 
	 */
	public void QuickSort(ArrayList<Word> A, int first, int last) {
		if(last-first <= 3) {
			insertionSort(A);
		}
		else {
			int pivot = mOf3(A,first,last);
			int split_point = Partition(A, first, last, pivot);
			QuickSort(A, first, split_point-1);
			QuickSort(A, split_point+1, last);
		}
	}
	
	/*
	 * This function takes in an arraylist of Words and returns nothing. 
	 */
	public void insertionSort(ArrayList<Word> A) { 
		for(int i = 1; i<A.size(); i++) {
			int compare_value = A.get(i).getWord().compareToIgnoreCase(A.get(i-1).getWord()); //get a word in the array list, turn into a string with the getWord() call, call the compare method that ignores the upper and lower case of letters and then do the same as the first word to get the second word
			int x = i;
			while(compare_value < 0 && x!=0) { //compare_value is less than 0 when the word calling the compareTo is less than the other word
				Word temp = A.get(x);
				A.set(x, A.get(x-1));
				A.set(x-1, temp);
				x--; //so that I can see if the word that was swapped is now in the right place
				if(x!=0) {
					compare_value = A.get(x).getWord().compareToIgnoreCase(A.get(x-1).getWord()); //reset for loop
				}
			}
		}
	}
	
	/*
	 * This function takes in a word arraylist, an integer first, an integer last. It returns an int pivot. 
	 */
	public int mOf3(ArrayList<Word> A, int first, int last) {
		int pivot = 0;
		int compare_value_last_first = A.get(last).getWord().compareToIgnoreCase(A.get(first).getWord()); //get the compare_value in the same way as insertionSort
		int mid = first + last / 2;
		int compare_value_last_mid = A.get(last).getWord().compareToIgnoreCase(A.get(mid).getWord());
		int compare_value_mid_first = A.get(mid).getWord().compareToIgnoreCase(A.get(first).getWord());
		
		if(compare_value_last_first == 0) { //if the first and last words are equal then pick the bigger of the two values
			if(compare_value_last_mid <0) {
				pivot = mid;
			}
			else {
				pivot = last;
			}
		}
		
		if(compare_value_mid_first == 0) { //same as above
			if(compare_value_last_first > 0) {
				pivot = last;
			}
			else {
				pivot = mid;
			}
		}
		
		if(compare_value_last_mid == 0) { //same as above
			if(compare_value_last_first< 0) {
				pivot = first;
			}
			else {
				pivot = mid;
			}
		}
		
		if(compare_value_last_first > 0 && compare_value_last_mid < 0 || compare_value_last_first < 0 && compare_value_last_mid > 0) { //check if the last index has the middle value
			pivot = last;
		}
		
		if(compare_value_last_first > 0 && compare_value_mid_first < 0 || compare_value_last_first < 0 && compare_value_mid_first > 0) { //check if the first index has the middle value
			pivot = first;
		}
		
		if(compare_value_last_mid > 0 && compare_value_mid_first > 0 || compare_value_last_mid < 0 && compare_value_mid_first < 0) { //check if the middle index has the middle value
			pivot = mid;
		}
		
		return pivot;
	}
	
	/*
	 * This function takes in an array list, an int first, an int last, an int pivot. It returns an int i (the split 
	 * point). 
	 */
	public int Partition(ArrayList<Word> A, int first, int last, int pivot) {
		Word temp = A.get(last);
		A.set(last, A.get(pivot));
		A.set(pivot, temp);
		int i = first;
		int j = last -1;
		while(i<j) {
			int compare_valuei = A.get(i).getWord().compareToIgnoreCase(A.get(pivot).getWord()); //same way as insertionSort
			while(i<j && compare_valuei <= 0) { //only do while i has not crossed j because then it'll start trying to check elements that don't exist (past the end of the list)
				i++; 
				compare_valuei = A.get(i).getWord().toUpperCase().compareTo(A.get(pivot).getWord().toUpperCase());
			}
			int compare_valuej = A.get(j).getWord().toUpperCase().compareTo(A.get(pivot).getWord().toUpperCase());
			while(j>i && compare_valuej >= 0) { //same as above, but with the word that j is on and the pivot while j is not crossed with i so that if everything is sorted j won't start checking what i has already checked
				j--;
				compare_valuej = A.get(j).getWord().toUpperCase().compareTo(A.get(pivot).getWord().toUpperCase());
			}
			if(i<j) {
				Word temporary = A.get(i);
				A.set(i, A.get(j));
				A.set(j, temporary);
			}
		}
		temp = A.get(last);
		A.set(last, A.get(pivot));
		A.set(pivot, temp);
		return i;
	}
	
	/*
	 * This function sets this.lists equal to a new array of a arraylists in order to make it empty. It takes in nothing
	 * and returns nothing.
	 */
	public void makeEmpty() {
		this.lists = (ArrayList<Word>[])new ArrayList[52];
		for(int x = 0; x < 52; x++) {
			this.lists[x] = new ArrayList<Word>();
		}
	}

}
