// Name: Gavin Dhaliwal

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args) throws FileNotFoundException
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			try {
				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
				
				else if (action.equalsIgnoreCase("STORE"))	// List all songs
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
				{
					mylibrary.listAllAudioBooks(); 
				}
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}
				// Download audiocontent (song/audiobook/podcast) from the store at a specific index
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					int fromIndex = 0;
					int toIndex = 0;
					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt())
					{
						fromIndex = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					System.out.print("To Store Content #: ");
					if (scanner.hasNextInt()) {
						toIndex = scanner.nextInt();
						scanner.nextLine();
					}
					
					for (int i = fromIndex; i <= toIndex; i++) {
						try {
							AudioContent content = store.getContent(i);
							mylibrary.download(content);
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
										
				}
				
				else if (action.equalsIgnoreCase("DOWNLOADA")) {
					// Use artist map
					String artist = "";
					System.out.print("Artist Name: ");
					if (scanner.hasNext()) {
						artist = scanner.nextLine();
					}
					ArrayList<Integer> artistIndices = mylibrary.downloada(artist); // We get the indices of all content for a specific artist, and can download all data individually
					for (int i = 0; i < artistIndices.size(); i++) {
						try {
							AudioContent content = store.getContent(artistIndices.get(i)+1);
							mylibrary.download(content);
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
				
				else if (action.equalsIgnoreCase("DOWNLOADG") ) {
					// Works same as downloada, but with downloadg(genre) returning all indices of a specific genre
					String genre = "";
					System.out.print("Genre: ");
					if (scanner.hasNext()) {
						genre = scanner.next();
						genre = genre.toUpperCase();
						scanner.nextLine();
					}
					ArrayList<Integer> genreIndices = mylibrary.downloadg(genre);
					for (int i = 0; i < genreIndices.size(); i++) {
						try {
							AudioContent content = store.getContent(genreIndices.get(i)+1);
							mylibrary.download(content);
						}
						catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}
				}
				
				// Get the *library* index (index of a song based on the songs list), and play the song
				else if (action.equalsIgnoreCase("PLAYSONG"))
				{
					int index = 0;
					System.out.print("Song Number: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
					try {
						mylibrary.playSong(index);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				// Print the table of contents (TOC) of an audiobook that has been downloaded
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
					int audioBookIndex = 0;
					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt()) {
						audioBookIndex = scanner.nextInt();
						scanner.nextLine();
					}
					try {
						mylibrary.printAudioBookTOC(audioBookIndex);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter 
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					int audioBookIndex = 0;
					System.out.print("Audio Book Number: "); // Add chapter to interact with audiobook too.
					if (scanner.hasNextInt()) {
						audioBookIndex = scanner.nextInt();
						scanner.nextLine();
					}
					int chapterIndex = 0;
					System.out.print("Chapter number: ");
					if (scanner.hasNextInt()) {
						chapterIndex = scanner.nextInt();
						scanner.nextLine();
					}
					try {
						mylibrary.playAudioBook(audioBookIndex, chapterIndex);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				// Specify a playlist title (string) 
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						title = scanner.next();
						title = title.toLowerCase();
						scanner.nextLine();
					}
					try {
						mylibrary.playPlaylist(title);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Most likely error is that a playlist with the provided title does not exist");
					}
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				// Play all the audio content 
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					int index = 0;
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						title = scanner.next();
						title = title.toLowerCase();
						scanner.nextLine();
					}
					System.out.print("Content Number: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						index -= 1;
						scanner.nextLine();
					}
					System.out.println(title);
					try {
						mylibrary.playPlaylist(title, index);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Either invalid title or invalid index");
					}
					
				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				// Read a song index from the keyboard
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					int index = 0;
					System.out.print("Library Song #: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						index -= 1;
						scanner.nextLine();
					}
					try {
						mylibrary.deleteSong(index);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				// Read a title string from the keyboard and make a playlist
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						title = scanner.next();
						title = title.toLowerCase();
						scanner.nextLine();
					}
					try {
						mylibrary.makePlaylist(title);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
				else if (action.equalsIgnoreCase("PRINTPL"))	
				{
					String title = "";
					System.out.print("Playlist Title: ");
						if (scanner.hasNext()) {
							title = scanner.next();
							title = title.toLowerCase();
							scanner.nextLine();
						}
					try {
						mylibrary.printPlaylist(title);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Most likely error is that a playlist with such a title does not exist");
					}
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					String title = "";
					String type = "";
					int playListIndex = 0;
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						title = scanner.next();
						title = title.toLowerCase();
						scanner.nextLine();
					}
					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
					if (scanner.hasNext()) {
						type = scanner.next().toUpperCase();
						scanner.nextLine();
					}
					System.out.print("Library Content #: ");
					if (scanner.hasNextInt()) {
						playListIndex = scanner.nextInt();
						playListIndex -= 1;
						scanner.nextLine();
					}
					try {
						mylibrary.addContentToPlaylist(type, playListIndex, title);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				// Delete content from play list based on index from the playlist
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					int index = 0;
					String title = "";
					System.out.print("Playlist Title: ");
					if (scanner.hasNext()) {
						title = scanner.next();
						title = title.toLowerCase();
						scanner.nextLine();
					}
					System.out.print("Playlist Content #: ");
					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						index -= 1;
						scanner.nextLine();
					}
					try {
						mylibrary.delContentFromPlaylist(index, title);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}
				
				else if (action.equalsIgnoreCase("SEARCH")) {
					String title = "";
					System.out.print("Title: ");
					if (scanner.hasNext()) {
						title = scanner.nextLine();
					}
					try {
						mylibrary.search(title);
					}
					catch (NotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
				else if (action.equalsIgnoreCase("SEARCHA")) {
					String artist = "";
					System.out.print("Artist: ");
					if (scanner.hasNext()) {
						artist = scanner.nextLine();
					}
					try {
						mylibrary.searcha(artist);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				else if (action.equalsIgnoreCase("SEARCHG")) {
					String genre = "";
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
					if (scanner.hasNext() ) {
						genre = scanner.next();
						genre = genre.toUpperCase();
						scanner.nextLine();
					}
					try {
						mylibrary.searchg(genre);
					}
					catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				System.out.print("\n>");
			}
		}
	}
}
