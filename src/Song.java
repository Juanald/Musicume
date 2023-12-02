// Name: Gavin Dhaliwal

/*
 * A Song is a type of AudioContent. A Song has extra fields artist, composer, genre, and lyrics
 */

public class Song extends AudioContent implements Comparable<Song>
{
	public static final String TYPENAME =	"SONG";
	
	public static enum Genre {POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL}; 
	private String artist; 		// Can be multiple names separated by commas
	private String composer; 	// Can be multiple names separated by commas
	private Genre  genre; 
	private String lyrics;
	
	
	
	public Song(String title, int year, String id, String type, String audioFile, int length, String artist,
			String composer, Song.Genre genre, String lyrics)
	{
		super(title, year, id, type, audioFile, length);
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
		this.lyrics = lyrics;
	}
	
	public String getType()
	{
		return TYPENAME;
	}
	
	// Print information about the song. First printing the basic information of the AudioContent 
	public void printInfo()
	{
		super.printInfo();
		System.out.println( "Artist: " + this.artist + " Composer: " + this.composer + " Genre: " + this.genre);
	}
	
	// Play the song by setting the audioFile to the lyrics string and then calling the play() method of the superclass
	public void play()
	{
		setAudioFile(this.lyrics);
		super.play();
	}
	
	public String getComposer()
	{
		return composer;
	}
	public void setComposer(String composer)
	{
		this.composer = composer;
	}
	
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	
	public String getLyrics()
	{
		return lyrics;
	}
	public void setLyrics(String lyrics)
	{
		this.lyrics = lyrics;
	}

	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre)
	{
		this.genre = genre;
	}	
	
	// Two songs are equal if their AudioContent information is equal and both the composer and artists are the same
	public boolean equals(Object other) // FIX
	{
		Song otherA = (Song) other;
		if (super.equals(otherA) && this.artist.equals(otherA.artist) && this.composer.equals(otherA.composer) && this.genre.equals(otherA.genre) && this.lyrics.equals(otherA.lyrics)) {
			return true;
		}
		return false;
	}
	
	// This method will allow songs to be sorted alphabetically, implements the comparable interface
	public int compareTo(Song other)
	{
		return this.getTitle().compareToIgnoreCase(other.getTitle()); // Implementing compareTo() method for use in library
	}
}
