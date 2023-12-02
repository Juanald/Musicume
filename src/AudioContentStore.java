// Name: Gavin Dhaliwal

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		public static ArrayList<AudioContent> contents; 
		public static Map<String, Integer> titleMap;
		public static Map<String, ArrayList<Integer>> artistMap;
		public static Map<String, ArrayList<Integer>> songGenreMap;
		
		// The constructor method scrapes store.txt, storing data in Song and AudioBook objects
		public AudioContentStore() throws FileNotFoundException
		{
			contents = new ArrayList<AudioContent>();
			try {
				scrapeFile(); // Method that scrapes the file creating contents arrayList
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			
			// initializing a map for search by title functionality
			titleMap = new HashMap<String, Integer>(); 
			for (int i = 0; i < contents.size(); i++) {
				titleMap.put(contents.get(i).getTitle(), i); // Every title is a key to the value of that title's index
			}
			
			// map of all the authors/artists to the indices of their works
			artistMap = new HashMap<String, ArrayList<Integer>>();
			for (int i = 0; i < contents.size(); i++) {
				ArrayList<Integer> indexArrayList = new ArrayList<Integer>();
				AudioContent current = contents.get(i); // Get the content
				switch (current.getType()) {
				case Song.TYPENAME:  // Check if current content is a song
					try {
						Song currentSong = (Song) current;
						String currentArtist = currentSong.getArtist();
						if (artistMap.containsKey(currentArtist)) { // If artist already in map, break to next.
							break;
						}

						indexArrayList.add(i); // Else add index of this artist to the created arraylist
						for (int j = i+1; j < contents.size(); j++) { // Used to compare this artist with the rest of the artists. Add to indices arraylist if same artist.
							Song nextSong = (Song) contents.get(j);
							String nextArtist = nextSong.getArtist();
							if (currentArtist.equals(nextArtist)) {
								indexArrayList.add(j);
							}
						}
						// by the end of this code, we have all the indices of the songs for a given artist, we can now map this artist to an ArrayList of their songs
						artistMap.put(currentArtist, indexArrayList); // Add to map
					}
					catch (Exception e) {
						Song song = (Song) current;
						artistMap.put(song.getArtist(), indexArrayList); // If exception raised(loop accesses an audiobook), means end of applicable list, map the values.
					}
					break;
				case AudioBook.TYPENAME: // Same process with AudioBooks as with Songs
					try {
						AudioBook currentBook = (AudioBook) current;
						String currentAuthor = currentBook.getAuthor();
						if (artistMap.containsKey(currentAuthor)) { // If author already in map, break
							break;
						}
						indexArrayList.add(i);
						for (int j = i+1; j < contents.size(); j++) { // Compare author with all other works
							AudioBook nextBook = (AudioBook) contents.get(j);
							String nextAuthor = nextBook.getAuthor();
							if (currentAuthor.equals(nextAuthor)) {
								indexArrayList.add(j); // Add index of whatever works have the same author
							}
						}
						artistMap.put(currentAuthor, indexArrayList); // Map author to ArrayList of indices of their works in contents ArrayList
					}
					catch (Exception e) {
						AudioBook book = (AudioBook) current;
						artistMap.put(book.getAuthor(), indexArrayList); // If exception raised, end of applicable list, map whatever values obtained
					}
				}
				
					
			}
			
			// Map all the song genres to the indices of every specific song of that genre in contents ArrayList
			songGenreMap = new HashMap<String, ArrayList<Integer>>();
			for (int i = 0; i < Song.Genre.values().length; i++) {
				ArrayList<Integer> indexArrayList = new ArrayList<Integer>(); // Creation of index ArrayList for each genre
				Song.Genre genre = Song.Genre.values()[i]; // For each genre outlined in Songs class
				try {
					for (int j = 0; j < contents.size(); j++) { // iterate through the list to find matches to this genre
						Song song = (Song) contents.get(j);
						Song.Genre compareGenre = song.getGenre();
						if (genre.equals(compareGenre)) {
							indexArrayList.add(j); // If matched, add to index arraylist
						}
					}
				}
				catch (Exception e) {
					songGenreMap.put(genre.toString(), indexArrayList); // End of the applicable list, assign value to map
				}
			}
		}
		
		private void scrapeFile() throws FileNotFoundException {
			Scanner lineScanner = new Scanner(new File("store.txt")); // Open up a scanner with store.txt
			while (lineScanner.hasNextLine()) { // Used to end the scanning of the file
				String type = lineScanner.nextLine(); // Indicates if a song or a book
				switch (type) {
				case Song.TYPENAME: // Collection of all constructor variables for song, store.txt formatted to allow nextLine() calls
					String id = lineScanner.nextLine();
					String title = lineScanner.nextLine();
					int year = lineScanner.nextInt();
					int length  = lineScanner.nextInt();
					lineScanner.nextLine();
					String artist = lineScanner.nextLine();
					String composer = lineScanner.nextLine();
					String genre = lineScanner.next();
					int lyricLines = lineScanner.nextInt();
					String lyrics = "";
					lineScanner.nextLine();
					for (int i = 0; i < lyricLines; i++) { // Use for loop to collect all lyrics into a string
						lyrics += lineScanner.nextLine() + "\n";
					}
					Song.Genre songGenre = Song.Genre.valueOf(genre); // Assign the genre to the correct type
					contents.add(new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, songGenre, lyrics)); // Create new song, add to contents arraylist
					System.out.println("Loading SONG");
					break;
				case AudioBook.TYPENAME: // If the next line is an audiobook, create all the constructor variables for audiobook
					ArrayList<String> chapterTitles = new ArrayList<String>();
					ArrayList<String> chapters = new ArrayList<String>();
					
					String id1 = lineScanner.nextLine();
					String title1 = lineScanner.nextLine();
					int year1 = lineScanner.nextInt();
					int length1 = lineScanner.nextInt();
					lineScanner.nextLine();
					String author = lineScanner.nextLine();
					String narrator = lineScanner.nextLine();
					int numChapters = lineScanner.nextInt();
					lineScanner.nextLine();
					for (int i = 0; i < numChapters; i++) {
						chapterTitles.add(lineScanner.nextLine()); // Add in chapter titles based on how many chapters
					}
					for (int i = 0; i < numChapters; i++) {
						int numLines = lineScanner.nextInt(); // Provides the amount of lines in each chapter
						String chapter = "";
						lineScanner.nextLine();
						for (int j = 0; j < numLines; j++) {
							chapter += lineScanner.nextLine() + "\n"; // Chapter added to created string
						}
						chapters.add(chapter); // Add string chapter to chapters
					}
					contents.add(new AudioBook(title1, year1, id1, AudioBook.TYPENAME, "", length1, author, narrator, chapterTitles, chapters)); // Create new audiobook object, add to contents arraylist
					System.out.println("Loading AUDIOBOOK");
					break;
				}
			}
		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				throw new IndexOutOfBoundsException("Invalid index provided");
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
}