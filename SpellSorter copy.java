//I have neither given nor recieved any unauthorized aid on this assignment.

import java.io.*;
import java.util.Scanner;

public class SpellSorter {
	
	public static Scanner textreader, scan = new Scanner(System.in);
	public static BufferedWriter corrected_writer, order_writer, sorted_writer;
	public static String quit = "no", word_instructions = "", file_name, command;
	public static File file_input;
	public static String[] words_cleaned, words;
	public static Word word;
	public static QuadraticProbingHashTable<Word> misspelled = new QuadraticProbingHashTable<Word>(), dictionary = new QuadraticProbingHashTable<Word>();
	public static BucketTable sorted_misspelled = new BucketTable();
	public static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	/*
	 * This main calls the other functions and runs a loop that allows for the spell checker to keep checking different
	 * files for misspelled until the user tells it to quit by setting quit equal to q.
	 */
	public static void main(String[] args) throws IOException {
		readDictionary(args[0]);
		
		getFile(); 
		while(!quit.equals("q")) {
			getCommand(); //gets user input and sets command to see what the user wants to do
			if(command.equals("p")) {
				spellCheckDoc();
				}
			
			else if(command.equals("f")) {
				getFile(); //scan in new file
			}
			
			else if(command.equals("q")) {
				quit = "q"; //so that the loop will stop
			}
		}
		
		System.out.println("Goodbye!");
		scan.close();
		textreader.close();
		}
	
	/*
	 * This function takes in a String fileName and creates a hash table of the words in the file (for this program it 
	 * fills in the dictionary hash table). It returns nothing.
	 */
	public static void readDictionary(String fileName) throws IOException {
		File dict = new File(fileName);
		textreader = new Scanner(dict);
		System.out.println("Reading in Dictionary..");
		while(textreader.hasNextLine()) {
			Word dict_word = new Word(textreader.nextLine());
			dictionary.insert(dict_word);
		}
		System.out.println("Dictionary Read.");
	}
	
	/*
	 * This function takes in nothing and scans the user input to set file_name and file_input and sets the scanner
	 * textreader to this file. It returns nothing.
	 */
	public static void getFile() throws IOException {
		//read the files to see if the words are in the hash table if not then considered misspelled
		System.out.println("Please enter a file to spell check>>");
		file_name = scan.nextLine();
		file_input = new File(file_name);
		textreader = new Scanner(file_input); 
	}
	
	/*
	 * This function prints out the options for the user and sets command to the the user input. It takes in and returns
	 * nothing.
	 */
	public static void getCommand() {
		System.out.println("Print words (p), enter new file (f), or quit (q)?");
		command = scan.nextLine();
	}
	
	/*
	 * This function takes in nothing and returns nothing. It swaps every letter of the word and checks if this new word
	 * is in the dictionary. If it is, then the word is added to the suggestions Word array list in the word class for the 
	 * original word.
	 */
	public static void swap() {
		String newWord = "";
		char firstLetter;
		char secondLetter;
		for(int x = 0; x<word.toString().length()-1; x++) {
			firstLetter = word.getWord().charAt(x);
			secondLetter = word.getWord().charAt(x+1);
			if(x!=0) {
				newWord = word.getWord().substring(0,x) + secondLetter + firstLetter + word.getWord().substring(x+2);
			} 
			else
				newWord = "" + secondLetter + firstLetter + word.getWord().substring(x+2);
			Word new_word = new Word(newWord); //create a word object so that the dictionary can be searched
			if(dictionary.contains(new_word)) {
				if(!word.suggestionsContains(new_word)) //so that repeats are not added to the suggestions list
					word.addSuggestion(new_word); 
			}
		}
	}
	
	/*
	 * This function takes in nothing and returns nothing. It inserts a letter before the word and after the word as 
	 * well as in-between each letter in the word and checks if any of these new words are in the dictionary. If a word
	 * is in the dictionary, the new word is added to the suggestions list in the word class of the original word.
	 */
	public static void insert() {
		String newWord = "";
		String newWord2 = ""; //in the case of the first and last iteration of the for loop there are two words produced one with the letter before the word and one with the letter behind the word and one with the letter between the letters as normal
		String newWord2_upper = "";
		for(int x = 0; x<word.toString().length(); x++) {
			for(int alph = 0; alph < 26; alph ++) { //need a double for loop to loop through both the word and every letter of the alphabet for that inserted spot in the word
				char ch = alphabet[alph];
				if(x==0) { //for the reason stated above
					newWord2 = ch + word.getWord(); 
					newWord2_upper = Character.toUpperCase(ch) + word.getWord(); //so that I can check if an upper case letter added to the first word is in the dictionary
				}
				else if(x == word.getWord().length()-1) { //for the reason stated above
					newWord2 = word.getWord() + ch;
				}
				newWord = word.getWord().substring(0,x+1) + ch + word.getWord().substring(x+1);
				Word new_word = new Word(newWord);
				if(dictionary.contains(new_word)) {
					if(!word.suggestionsContains(new_word))
						word.addSuggestion(new_word); 
				}
				Word new_word2 = new Word(newWord2);
				if(dictionary.contains(new_word2)) {
					if(!word.suggestionsContains(new_word2))
						word.addSuggestion(new_word2); 
				}
				Word new_word2_upper = new Word(newWord2_upper);
				if(dictionary.contains(new_word2_upper)) {
					if(!word.suggestionsContains(new_word2_upper))
						word.addSuggestion(new_word2_upper); 
				}
			}
		}
	}
	
	/*
	 * This function takes in nothing and returns nothing. It iterates through a word object and deletes ever letter
	 * it then checks if any of these new words are in the dictionary. If a new word is in the dictionary it adds this
	 * word to the suggestions array list in the word class for the original word.
	 */
	public static void delete() {
		String newWord = "";
		for(int x = 0; x<word.toString().length(); x++) {
			newWord = word.getWord().substring(0,x) + word.getWord().substring(x+1);
			Word new_word = new Word(newWord);
			if(dictionary.contains(new_word)) {
				if(!word.suggestionsContains(new_word))
					word.addSuggestion(new_word); 
			}
		}
	}
	
	/*
	 * This function takes in nothing and returns nothing. It iterates through a word object replacing every character
	 * with every character from the alphabet and checks if any of these new words are in the dictionary. If a new word 
	 * is in the dictionary it adds this word to the suggestions array list in the word class for the original word.
	 */
	public static void replace() {
		String newWord = "";
		String newWord2 = ""; //there will be a second word for the first letter of the word because we need to check if an upper case replace for the first letter of the word is in the dictionary 
		for(int x = 0; x<word.toString().length(); x++) {
			for(int alph = 0; alph < 26; alph ++) { //need a double for loop for the same reason as insert, but now it is replacing a character not adding a letter
				char ch = alphabet[alph];
				if(x==0) {
					newWord2 = Character.toUpperCase(ch) + word.getWord().substring(x+1); 
				}
				newWord = word.getWord().substring(0,x) + ch + word.getWord().substring(x+1); 
				Word new_word = new Word(newWord);
				if(dictionary.contains(new_word)) {
					if(!word.suggestionsContains(new_word))
						word.addSuggestion(new_word); 
				}
				Word new_word2 = new Word(newWord2);
				if(dictionary.contains(new_word2)) {
					if(!word.suggestionsContains(new_word2))
						word.addSuggestion(new_word2); 
				}
			}
		}
	}
	
	/*
	 * This function takes in nothing and returns nothing. It iterates through a word object and splits the word into 
	 * two words between every two letters in the word. It then checks if these new words are both in the dictionary.
	 * If they are both in the dictionary then the words are added together with a space and that combined word object
	 * is added to the suggestions array list in the word class for the original word.
	 */
	public static void split() {
		String newWord = "";
		String newWord2 = ""; 
		for(int x = 0; x<word.getWord().length(); x++) {
			newWord = word.getWord().substring(0,x);
			Word new_word = new Word(newWord);
			newWord2 = word.getWord().substring(x);
			Word new_word2 = new Word(newWord2);
			if(dictionary.contains(new_word) && dictionary.contains(new_word2)) {
				Word combined = new Word(newWord + " " + newWord2); //make a combined word object so the two words can be added to the suggestions array list together
				if(!word.suggestionsContains(combined))
					word.addSuggestion(combined);  
			}
		}
	}
	
	/*
	 * This function takes in nothing. It then prompts the user about what replacement word the user would like to select.
	 * It calls printSuggestions for that word. It then takes in the user_input as a string and returns this.
	 */
	public static String getReplacement() {
		System.out.print("Replace with ");
		word.printSuggestions();
		System.out.println(", or next (n), or quit (q) ?");
		String user_input = scan.nextLine(); 
		return user_input;
	}
	
	/*
	 * This function takes in a string (user_input) sets the repl string equal to the integer value of the input
	 * and then sets the replacement string in the word class equal to the replacement word.
	 */
	public static void setReplacement(String user_input) {
		String repl = word.getOneSuggestion(Integer.parseInt(user_input)).toString();
		word.setReplacement(repl);	
	}
	
	/*
	 * This function takes in an integer. It then writes out the replacement word with the correct punctuation. 
	 */
	public static void writeReplacement(int index) throws IOException {
		Character last_ch = words[index].charAt((words[index].length())-1); //to see if there was originally punctuation after this word
		if(!Character.isLetter(last_ch)) {
			corrected_writer.write(word.getReplacement()+last_ch + " "); //if there was punctuation then write the new word with the old punctuation
		}
		else {
			corrected_writer.write(word.getReplacement() + " "); //otherwise print normally
		}
	}
	
	/*
	 * This function prints out the word and the options for the user. It then takes in the user input and sets 
	 * word_instructions equal to that input.
	 */
	public static void getWordInstructions() {
		System.out.println("--" + word.toString() + " " + word.getLine());
		System.out.println("ignore all (i), replace all (r), next (n), or quit (q)");
		word_instructions = scan.nextLine();
	}
	
	/*
	 * This function takes in nothing and returns nothing. It calls resetforPrint(). It points the writers to the right 
	 * output file. It then loops through the words in the file and for each line it adds the words to the hash 
	 * table misspelled if they are not in the dictionary. It then gets the user input about what to do for every word
	 * so that it can set replacement and ignored for each word.
	 */
	public static void spellCheckDoc() throws IOException {
		resetforPrint();
		
		corrected_writer = new BufferedWriter(new FileWriter(file_name.substring(0,file_name.length()-4)+"_corrected.txt"));
		order_writer = new BufferedWriter(new FileWriter(file_name.substring(0,file_name.length()-4)+"_order.txt")); //the substring takes off the .txt 
		sorted_writer = new BufferedWriter(new FileWriter(file_name.substring(0,file_name.length()-4)+"_sorted.txt"));
		
		String text = "";
		int line_num = 0;
		while(textreader.hasNextLine()) {
			line_num++;
			text = textreader.nextLine(); 
			
			cleanWords(text);
				
				for(int index = 0; index<words_cleaned.length; index++) {
					word = new Word(words_cleaned[index]);
					if(dictionary.contains(word)) {
						corrected_writer.write(words[index] + " "); //write out correctly spelled words to the corrected file
					}
					else {
						word.setLine(line_num); //set line number
						order_writer.write(word + " " + line_num + "\n");
						if(!misspelled.contains(word)) {
							word.setLineNums(Integer.toString(line_num));
							misspelled.insert(word);
							sorted_misspelled.insert(word); //insert into new table
							if(word_instructions.equals("stop")) {
								corrected_writer.write(words[index] + " ");
							}
							else if(!word_instructions.equals("stop")) { //so that if the person quits they no longer are interacting, but the for loop keeps going to update the misspelled word hash table and write out the order and correct files
								getWordInstructions(); //sets word_instructions based on user input
								if(word_instructions.equals("i")) { 
									word.setIgnored(true);
									corrected_writer.write(words[index] + " ");
								}
								else if(word_instructions.equals("r")) {
									findSuggestions(index);
								}
								else if(word_instructions.equals("n")) {
									corrected_writer.write(words[index] + " ");
								}
								else if(word_instructions.equals("q")) {
									word_instructions = "stop"; //so that the else case above is triggered
									corrected_writer.write(words[index] + " ");
								}
							}
						}
							else { //misspelled word already in the misspelled hash table
								int new_line_num = word.getLine(); //set line number from above to an integer as it will go away when I call the word from the hash table
								word = misspelled.get(word);
								word.setRepeats(word.getRepeats()+1);
								word.setLineNums(word.getLineNums() + " " + new_line_num); //keep track of all the line numbers for a word seen more than once
								if(!word.getReplacement().equals("")) { //no replacement selected
									writeReplacement(index);
								}
								else if(!word_instructions.equals("stop") && !word.getIgnored()==true) { //so that the user has no interaction if they have quit or if they have ignored a previous instance of the word
										getWordInstructions();
										
										if(word_instructions.equals("i")) {
											word.setIgnored(true);
											corrected_writer.write(words[index] + " ");
										}
										else if(word_instructions.equals("r")) {
											findSuggestions(index);
										}
										else if(word_instructions.equals("n")) {
											corrected_writer.write(words[index] + " ");
										}
										else if(word_instructions.equals("q")) {
											word_instructions = "stop";
											corrected_writer.write(words[index] + " ");
										}
									}
								else //write out ignored words
									corrected_writer.write(words[index] + " ");
							}
					}
						
				}
				corrected_writer.write("\n"); //so that the spacing of the original document remains the same
			}
			System.out.println("Spell check complete!");
			//sort the sorted_misspelled table
			for(int letter = 0; letter < 52; letter++) {
				sorted_misspelled.QuickSort(sorted_misspelled.getLists()[letter], 0, sorted_misspelled.getLists()[letter].size()-1);
			}
			//write out into the sorted file
			for(int i = 0; i < sorted_misspelled.getLists().length; i++) {
				for(int x = 0; x < sorted_misspelled.getLists()[i].size(); x++) {
					sorted_writer.write(sorted_misspelled.getLists()[i].get(x) + " " + sorted_misspelled.getLists()[i].get(x).getLineNums() + "\n");
				}
			}
			sorted_writer.close();
			corrected_writer.close();
			order_writer.close();
	}
	
	/*
	 * This function takes in nothing and returns nothing. But, it empties the misspelled hash table and sets 
	 * the textreader to whatever the latest file input was. It also resets word_instructions so that the options are
	 * printed to the user when the document is being spell checked.
	 */
	public static void resetforPrint() throws IOException {
		misspelled.makeEmpty();  //so that the hash table is specific to the file that is being read in at this time
		file_input = new File(file_name);
		textreader = new Scanner(file_input); 
		word_instructions = ""; //the lines above this are so that we can quit and then keep printing but it picks up from the word that was quit on is that ok? 
		sorted_misspelled.makeEmpty();
	}
	
	/*
	 * This functions takes in a string and sets words to the array of those words with punctuation. It then cleans out
	 * the punctuation and creates another array of the words without the punctuation that words_cleaned is set equal to.
	 */
	public static void cleanWords(String text) {
		words = text.split(" "); //so I can keep track of the punctuation when writing out later
		String text_cleaned = "";
		for(int x = 0; x<text.length(); x++) { //get rid of punctuation for inserting into hash table
			Character ch = text.charAt(x);
			if(Character.isLetter(ch)) {
				text_cleaned += text.charAt(x);
			}
			if(Character.isWhitespace(ch)) { //keep the spaces so that I can split based on them below
				text_cleaned += text.charAt(x);
			}
		}
			words_cleaned = text_cleaned.split(" ");
	}
	
	/*
	 * This function takes in an integer and calls all the methods for generating suggestions for replacement words. 
	 * It then calls getReplacement() and based on user input it writes the correct word and might call
	 * setReplacement(). If there are not suggestions generated then it prints that and writes the original word.
	 */
	public static void findSuggestions(int index) throws IOException {
		swap(); 
		
		insert();
		
		delete(); 
		
		replace();
		
		split();
		
		if(word.getSuggestionsSize()!=0) { //to see if any suggestions have been generated
			String user_input = getReplacement(); 
			if(user_input.equals("n")) {
				corrected_writer.write(words[index] + " ");
			}
			else if(user_input.equals("q")) {
				word_instructions = "stop";
				corrected_writer.write(words[index] + " ");
			}
			else {
				setReplacement(user_input);
				writeReplacement(index);
			}
		}
		else {
			System.out.println("No suggestions found.");
			corrected_writer.write(words[index] + " ");
			
		}
	}

	}
