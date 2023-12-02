// Name: Gavin Dhaliwal

import java.util.ArrayList;

/*
 * An AudioBook is a type of AudioContent.
 * It contains all the attributes of the AudioContent class, but with an author, narrator, a list of chapterTitles, a list of chapters, and a pointer to the current chapter.
 * It is a recording made available on the internet of a book being read aloud by a narrator
 */
public class AudioBook extends AudioContent
{
	public static final String TYPENAME =	"AUDIOBOOK";
	
	private String author; 
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	
	public AudioBook(String title, int year, String id, String type, String audioFile, int length,
									String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters)
	{
		// Initializing additional AudioBook instance variables 
		super(title, year, id, type, audioFile, length);
		this.author = author;
		this.narrator = narrator;
		this.chapterTitles = chapterTitles;
		this.chapters = chapters;
		this.currentChapter = 0;
	}
	
	public String getType()
	{
		return TYPENAME;
	}

	// Prints information about the audiobook through the use of AudioContent printInfo() function, with author and narrator added
	public void printInfo()
	{
		super.printInfo();
		System.out.println( "Author: " + this.author + " Narrated by: " + this.narrator);
	}

	// Playing is done by setting the audioFile to the current chapter title, from chapterTitles arraylist, followed by chapter from chapters arraylist. Play() function called from superclass.
	public void play() // Chapters
	{
		setAudioFile(chapterTitles.get(currentChapter) + "\n" + chapters.get(currentChapter));
		super.play(); // Calling superclass to play audio file
	}
	
	// Prints table of contents of book by looping over chapterTitles ArrayList
	public void printTOC()
	{
		int index = 0;
		for (int i = 0; i < chapterTitles.size(); i++) {
			index += 1; // Indexing from 1
			System.out.println("Chapter " + index + ". " + chapterTitles.get(i) + "\n");
		}
	}

	// selects chapter with input checking for bounds
	public void selectChapter(int chapter)
	{
		if (chapter >= 1 && chapter <= chapters.size())
		{
			currentChapter = chapter - 1;
		}
	}
	
	//Two AudioBooks are equal if their AudioContent information is equal and both the author and narrators are equal
	public boolean equals(Object other)
	{
		AudioBook otherA = (AudioBook) other;
		if (super.equals(other) && this.author.equals(otherA.author) && this.narrator.equals(otherA.narrator)) {
			return true;
		}
		return false;
	}
	
	public int getNumberOfChapters()
	{
		return chapters.size();
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getNarrator()
	{
		return narrator;
	}

	public void setNarrator(String narrator)
	{
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles()
	{
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles)
	{
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters()
	{
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters)
	{
		this.chapters = chapters;
	}

}
