// Name: Gavin Dhaliwal

import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	// Prints all contents of a given playlist, knowing that the playlist may have songs and audiobooks
	public void printContents()
	{
		int index = 0;
		for (int i = 0; i < contents.size(); i++) {
			index += 1; // For indexing at 1 instead of 0
			AudioContent audioContent = contents.get(i);
			switch (audioContent.getType()) { // Switch statement based on content type, as there are different print methods
			case Song.TYPENAME:
				Song song = (Song) audioContent; // If is a song, cast song type to object
				System.out.print(index + ". ");
				song.printInfo(); // Call song print method
				System.out.println();
				break;
			case AudioBook.TYPENAME:
				AudioBook audioBook = (AudioBook) audioContent; // If audiobook, cast audiobook type to object
				System.out.print(index + ". ");
				audioBook.printInfo(); // Call audiobook print method
				System.out.println();
				break;
			}
		}
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		for (int i = 0; i < contents.size(); i++) {
			AudioContent audioContent = contents.get(i);
			switch (audioContent.getType()) { // Switch statement based on type of audiocontent
			case Song.TYPENAME:
				Song song = (Song) audioContent; // If song type, cast song to audioContent
				song.play(); // Call audiobook play method
				System.out.println();
				break;
			case AudioBook.TYPENAME:
				AudioBook audioBook = (AudioBook) audioContent; // If audiobook type, cast audiobook to audiocontent
				audioBook.play(); // Call audiobook play method
				System.out.println();
				break;
			}
		}
	}
	
	// Play the specific AudioContent from the contents array list, making sure index is bounded
	public void play(int index)
	{
		if (index >= 0 && index < contents.size()) { 
			contents.get(index).play();  
		}
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		Playlist otherA = (Playlist) other;
		if (this.title.equals(otherA.title)) {
			return true;
		}
		return false;
	}
	
	// Deletes content from a playlist at a given index
	public void deleteContent(int index)
	{
		if (!contains(index)) return;
		contents.remove(index-1);
	}
	
	
}
