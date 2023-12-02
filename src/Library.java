// Name: Gavin Dhaliwal

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	public ArrayList<Song> 			songs;  
	public ArrayList<AudioBook> 	audiobooks; 
	private ArrayList<Playlist> 	playlists; 
	
	public Library()
	{
		songs = new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	}

	// Allows you to download a piece of AudioContent
	public void download(AudioContent content)
	{
		switch (content.getType()) { // Switch statement dependent on the type of content
		case Song.TYPENAME:
			if (songs.contains(content)) { // If a song, check if already in songs. If so, return an error
				throw new AlreadyDownloadedException("SONG " + content.getTitle() + " already downloaded"); // Throw this exception if song is already downloaded
			}
			songs.add((Song) content); // Else, cast it as a song, and add it to songs ArrayList simulating all the songs for a user
			System.out.println("SONG " + content.getTitle() + " Added to Library");
			break;
		case AudioBook.TYPENAME:
			if (audiobooks.contains(content)) { // If an audiobook, check if in books, if so, return error. 
				throw new AlreadyDownloadedException("AudioBook " + content.getTitle() + " already downloaded"); // Throw this exception if book already downloaded
			}
			audiobooks.add((AudioBook) content); // Else, cast as a book, add to books
			System.out.println("AUDIOBOOK " + content.getTitle() + " Added to Library");
			break;
			}
	}
	
	// Prints all the info of all downloaded songs
	public void listAllSongs()
	{
		int index = 0;
		for (int i = 0; i < songs.size(); i++)
		{
			index += 1; // Used for indexing from 1
			System.out.print("" + index + ". ");
			songs.get(i).printInfo(); // Print info for each song as defined in song file
			System.out.println();	
		}
	}
	
	// Lists info of all AudioBooks
	public void listAllAudioBooks()
	{
		int index = 0;
		for(int i = 0; i < audiobooks.size(); i++) {
			index += 1; // Used for indexing from 1
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo(); // Print book info as defined in audiobook file
			System.out.println();
		}
	}
	
	// Prints all playlists created by the user
	public void listAllPlaylists()
	{
		int index = 0;
		for(int i = 0; i < playlists.size(); i++) {
			index += 1; // 1-indexing
			System.out.print(index + ". " + playlists.get(i).getTitle()); // Print out title and 1-indexed position
			System.out.println();
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		ArrayList<String> newArrayList = new ArrayList<String>();
		for (int i = 0; i < songs.size(); i++) { 
			if (!newArrayList.contains(songs.get(i).getArtist())) { // Add to newArrayList if newArrayList does not contain the artist (avoids duplicates)
				newArrayList.add(songs.get(i).getArtist()); 
			}
		}
		
		// Printing the artists to the console
		int index = 0;
		for (int j = 0; j < newArrayList.size(); j++ ) { 
			index += 1;
			System.out.println(index + ". " + newArrayList.get(j));
		}
	}

	// Deletes a given song from library (Songs ArrayList) and any playlists made by the user
	public void deleteSong(int index)
	{
		 if (index < 0 || index >= songs.size()) {
			 throw new NotFoundException("Invalid index provided. No such song.");
		 }
		Song delSong = songs.get(index);
		songs.remove(index); // Remove the specified song from songs
		for (int i = 0; i < playlists.size(); i++) { 
			ArrayList<AudioContent> currentContent = playlists.get(i).getContent(); // get the playlist's contents
			for (int j = 0; j < currentContent.size(); j++) { // This loop checks if the current playlist's content contains the to be deleted song, if it does, remove it.
				if (currentContent.contains(delSong)) {
					currentContent.remove(delSong);
				}
			}
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		Collections.sort(songs, new SongYearComparator()); // Use comparator to sortbyyear
	
	}

	private class SongYearComparator implements Comparator<Song>
	{

		public int compare(Song song1, Song song2) {
			return song1.getYear() - song2.getYear(); // Comparison via subtraction
		}
	}
		
	public void sortSongsByLength()
	{	
		Collections.sort(songs, new SongLengthComparator()); // Check SongLengthComparator to sort each song
	}
	
	private class SongLengthComparator implements Comparator<Song>
	{

		@Override
		public int compare(Song song1, Song song2) {
			return song1.getLength() - song2.getLength(); // Comparison via subtraction
		}

	}
	
	public void sortSongsByName() // Sorts the songs arrayList
	{
		Collections.sort(songs); 
	}
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new NotFoundException("Song not found");
		}
		songs.get(index-1).play();
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size()) // Check if adequate index, if not, produce an error message to be printed to console
		{
			//errorMsg = "Audiobook Not Found"; // 
			throw new NotFoundException("AudioBook not found");
		}
		AudioBook audioBook = audiobooks.get(index-1);
		if (chapter < 1 || chapter > audioBook.getChapters().size()) { // Check if adequate index, otherwise update error message to be printed to console
			throw new NotFoundException("No such chapter found");
		}
		audioBook.selectChapter(chapter);
		audioBook.play();
	}
	
	// Prints AudioBook Table of Contents, see AudioBook for printTOC() function, does index bound checking
	public void printAudioBookTOC(int index)
	{	
		if (index < 0 || index > audiobooks.size()) {
			throw new IndexOutOfBoundsException("Invalid index for audiobook");
		}
		if (index >=0 && index <= audiobooks.size()) {
			AudioBook audioBook = audiobooks.get(index-1);
			audioBook.printTOC();
		}
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list making sure a playlist with the same name doesn't already exist
	public void makePlaylist(String title)
	{
		if (playlists.contains(newPlayList)) {
			throw new AlreadyExistsException("Playlist " + title + " Already Exists"); // Throw this exception if the playlist already exists
		}
		else {
			Playlist newPlayList = new Playlist(title);
			playlists.add(newPlayList);
		}
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		if (!playlists.contains(new Playlist(title))) {
			throw new NotFoundException("No such playlist");
		}
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(title)) { // If playlist found
				playlists.get(i).printContents(); // Print contents of playlist
			}	
		}
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		if (!playlists.contains(new Playlist(playlistTitle))) {
			throw new NotFoundException("No such playlist exists");
		}
		Playlist desiredPlaylist = null;
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) { // If desired playlist found, play all content in desired playlist
				desiredPlaylist = playlists.get(i);
				desiredPlaylist.playAll();
			}
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		Playlist desiredPlaylist = null;
		if (indexInPL < 0 || indexInPL >= 0) {
			throw new NotFoundException("No such index in playlist");
		}
		if (!playlists.contains(new Playlist(playlistTitle))) {
			throw new NotFoundException("No such playlist exists");
		}
			for (int i = 0; i < playlists.size(); i++) {
				if (playlists.get(i).getTitle().equals(playlistTitle)) { // If desired playlist found, play specific indexed content in playlist
					desiredPlaylist = playlists.get(i);
					desiredPlaylist.play(indexInPL);
				}
			}	
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Takes a string representing a type of AudioContent(Song, AudioBook), index in contents ArrayList (see AudioContentStore), and title of playlist to be matched
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{	
		// Getting desired playlist to add content to
		Playlist desiredPlaylist = null;
		if (index < 0 || index >= playlists.size()) {
			throw new NotFoundException("Invalid index provided. No such audiocontent.");
		}
		if (!playlists.contains(new Playlist(playlistTitle))) {
			throw new NotFoundException("No such playlist");
		}
		
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) {
				desiredPlaylist = playlists.get(i); 
			}
		}
		
		// Adding content to desired arraylist
		switch (type) {
		case Song.TYPENAME:
			Song desiredSong = songs.get(index);
			desiredPlaylist.addContent(desiredSong);
			break;
		case AudioBook.TYPENAME:
			AudioBook desiredAudioBook = audiobooks.get(index);
			desiredPlaylist.addContent(desiredAudioBook);
			break;
		}
	}

 	// Delete a song/audiobook/podcast from a playlist with the given title
	public void delContentFromPlaylist(int index, String title)
	{
		if (!playlists.contains(new Playlist(title))) {
			throw new NotFoundException("No such playlist exists");
		}
		for (int i = 0; i < playlists.size(); i++) {
			if (index >= 0 && index < playlists.get(i).getContent().size()) { // If index smaller than size of contents of playlist
				if (playlists.get(i).getTitle().equals(title)) {
					ArrayList<AudioContent> removedFromContent = playlists.get(i).getContent(); // Content from which a specific audiocontent will be removed
					removedFromContent.remove(index); // Remove content at this index
				}
			}
		}
	}
	
	/*
	 * Implement map search functionality in audiocontentstore
	 */

	// Search for a title in contents, checking if that title exists, using the titleMap of AudioContentStore
	public void search(String title) {
		Map<String, Integer> titleMap = AudioContentStore.titleMap; // Access the map created in AudioContentStore
		if(!titleMap.containsKey(title)) {
			throw new NotFoundException("No matches for " + title); // If the title is non-existent in the map, throw this exception.
		}
		AudioContent desiredSearchContent = AudioContentStore.contents.get(titleMap.get(title)); // Get the desired content using the map index to index into contents arraylist
		switch (desiredSearchContent.getType()){
		case Song.TYPENAME: // Printing to console if song
			Song desiredSong = (Song) desiredSearchContent;
			System.out.print(titleMap.get(title)+1 + ". ");
			desiredSong.printInfo();
			break;
		case AudioBook.TYPENAME: // Printing to console if book
			AudioBook book = (AudioBook) desiredSearchContent;
			book.printInfo();
		}
	}
	
	// Searches for all songs/audiobooks by an artist, printing the info of their materials
	public void searcha(String artist) {
		Map<String, ArrayList<Integer>> artistMap = AudioContentStore.artistMap; // Access map of indices
		if (!artistMap.containsKey(artist)) {
			throw new NotFoundException("No matches for " + artist); // If the user types in an artist that is not in the map
		}
		ArrayList<Integer> artistArrayList = artistMap.get(artist); // Get the arraylist for a specific artist in the map
		ArrayList<AudioContent> storeContents = AudioContentStore.contents; // Access store contents
		AudioContent testContent = storeContents.get(artistArrayList.get(0)); // Used to check if artist is an author or artist in switch statement
		switch (testContent.getType()) {
		case Song.TYPENAME:
			for (int i = 0; i < artistArrayList.size(); i++) { // For each index in artistArrayList, print out that song
				Song song = (Song) storeContents.get(artistArrayList.get(i));
				System.out.print(artistArrayList.get(i)+1 + ". ");
				song.printInfo();
				System.out.println();
			}
			break;
		case AudioBook.TYPENAME:
			for (int i = 0; i < artistArrayList.size(); i++) { // For each index in artistArrayList, print that book
				AudioBook book = (AudioBook) storeContents.get(artistArrayList.get(i));
				System.out.println(artistArrayList.get(i)+1 + ". ");
				book.printInfo();
				System.out.println();
			}
		}
	}
	
	// Searching by genre functionality through songgenreMap, printing out all relevant content
	public void searchg(String genre) {
		Map<String, ArrayList<Integer>> songGenreMappy = AudioContentStore.songGenreMap; // Access the appropriate map
		if (!songGenreMappy.containsKey(genre)) {
			throw new NotFoundException("This genre does not exist"); // If an invalid genre put in 
		}
		ArrayList<Integer> songGenreIndexList = songGenreMappy.get(genre); // Access the arraylist of a specific genre
		ArrayList<AudioContent> storeContents = AudioContentStore.contents; // Get the contents
		for (int i = 0; i < songGenreIndexList.size();i++) { // Printing out each song of a specific genre provided
			Song song = (Song) storeContents.get(songGenreIndexList.get(i));
			System.out.print(songGenreIndexList.get(i) + 1 + ". ");
			song.printInfo();
			System.out.println();
		}
	}
	
	// Downloads all works from an artist, returns indices that can be downloaded seperately in download function
	public ArrayList<Integer> downloada(String artist) {
		Map<String, ArrayList<Integer>> artistMappy = AudioContentStore.artistMap; // Access the artist map
		if (!artistMappy.containsKey(artist)) {
			throw new NotFoundException("The artist " + artist + " was not found in the store"); // If the artist is not in the map, throw exception
		}
		ArrayList<Integer> artistIndices = artistMappy.get(artist); // Return the indices to AudioUI, where download method called in a for loop to download at all indices.
		return artistIndices;
	}
	
	// Download all songs of a specific genre, returning indices of songs of a specific genre. Each index can then be downloaded separately in UI
	public ArrayList<Integer> downloadg(String genre) {
		Map<String, ArrayList<Integer>> genreMappy = AudioContentStore.songGenreMap; // Access genre map
		if (!genreMappy.containsKey(genre)) {
			throw new NotFoundException("Genre not found."); // Throw exception if invalid genre input
		}
		ArrayList<Integer> genreIndices = genreMappy.get(genre); // Return indices to be downloaded in for loop calling download method in AudioUI
		return genreIndices;
	}
}

// Custom exception classes
class AlreadyDownloadedException extends RuntimeException {
	public AlreadyDownloadedException() {}
	public AlreadyDownloadedException(String message) {
		super(message);
	}
}
class NotFoundException extends RuntimeException {
	public NotFoundException() {}
	public NotFoundException(String message) {
		super(message);
	}
}

class AlreadyExistsException extends RuntimeException {
	public AlreadyExistsException() {}
	public AlreadyExistsException(String message) {
		super(message);
	}
}


