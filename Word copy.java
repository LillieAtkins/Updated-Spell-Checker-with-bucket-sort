import java.util.ArrayList;

public class Word{

	private String word, line_nums;
	private String replacement = ""; //the string associate with this Word object
	private boolean ignored = false;
	private int line_number, num_repeats; 
	private ArrayList<Word> suggestions = new ArrayList<Word>(); 

	/*
	 * This is a constructor for the word object. It takes in a string and sets that equal to the data field word. 
	 * It returns nothing.
	 */
	public Word(String w)
	{
		word = w;
	}
	
	/*
	 * This is a setter for the string word. It takes in a string and sets that equal to the word. It returns nothing.
	 */
	public void setWord(String w)
	{
		word = w;
	}
	
	/*
	 * This is a getter for the string word. It takes in nothing and returns the data field word.
	 */
	public String getWord()
	{
		return word;
	}
	
	/*
	 * This is a getter for the data field ignored. It takes in nothing and returns the boolean ignored.
	 */
	public boolean getIgnored() {
		return this.ignored;
	}
	
	/*
	 * This is a setter for the data field ignored. It takes in a boolean and sets that equal to the data field ignored.
	 */
	public void setIgnored(boolean value) {
		this.ignored = value;
	}
	
	/*
	 * This is a setter for the data field line_number. It takes in a integer and sets that equal to line_number.
	 */
	public void setLine(int line_num) {
		this.line_number = line_num;
	}
	
	/*
	 * This is a getter for the data field line_number. It takes in nothing and returns line_number.
	 */
	public int getLine() {
		return this.line_number;
	}
	
	/*
	 * This is a getter for the data field replacement. It takes in nothing and returns replacement.
	 */
	public String getReplacement() {
		return this.replacement;
	}
	
	/*
	 * This is a setter for replacement. It takes in a string and sets that equal to replacement.
	 */
	public void setReplacement(String replace_word) {
		this.replacement = replace_word;
	}
	
	/*
	 * This is the getter for repeats. It takes in nothing and returns the number of repeats.
	 */
	public int getRepeats() {
		return this.num_repeats;
	}
	
	/*
	 * This is a setter for repeats. It takes in an integer and sets num_repeats equal to that.
	 */
	public void setRepeats(int num) {
		this.num_repeats = num;
	}
	
	/*
	 * This is a getter for the suggestions array list. It takes in nothing and returns the array list of words.s
	 */
	public ArrayList<Word> getSuggestions() {
		return this.suggestions;
	}
	
	/*
	 * This function takes in a word object and adds that word object to the array list suggestions.
	 */
	public void addSuggestion(Word new_word) {
		this.suggestions.add(new_word);
	}
	
	/*
	 * This function takes in nothing and returns nothing. It iterates through the array list and prints out every word
	 * object as a string with its index in parenthesize.
	 */
	public void printSuggestions() {
		for(int i = 0; i < this.suggestions.size(); i++) {
			System.out.print(" (" + i + ") " + this.suggestions.get(i));
		}
	}
	
	/*
	 * This is a getter method that takes in nothing and returns the size of the array list of suggestions.
	 */
	public int getSuggestionsSize() {
		return this.suggestions.size();
	}
	
	/*
	 * This function takes in a word object and checks if it is in the array list suggestions and returns a boolean.
	 */
	public boolean suggestionsContains(Word word) {
		return this.suggestions.contains(word);
	}
	
	/*
	 * This function takes in an integer and returns the word object at that integer in the array list.
	 */
	public Word getOneSuggestion(int index) {
		return this.suggestions.get(index);
	}
	
	/*
	 * This is a setter function that takes in an array list and sets that equal to the array list suggestions. It 
	 * returns nothing.
	 */
	public void setSuggestions(ArrayList<Word> new_list) {
		this.suggestions = new_list;
	}
	
	/*
	 * This is a toString method that takes in nothing and returns the data field word.
	 */
	public String toString() {
		return this.word;
	}
	
	/*
	 * This is a getter method for the string line numbers. That returns this.line_nums.
	 */
	public String getLineNums() {
		return this.line_nums;
	}
	
	/*
	 * This is a setter method for the string line numbers. That sets this.line_nums equal to the string 
	 * word that it takes in.
	 */
	public void setLineNums(String word) {
		this.line_nums = word;
	}
		
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
	
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}
	
	/*
	 * equals method that compares two words
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}	
	
	

}
