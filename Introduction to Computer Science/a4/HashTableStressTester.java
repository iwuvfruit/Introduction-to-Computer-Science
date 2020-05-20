//package assignment4;
import java.util.ArrayList;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Class for testing your implementation of the HashTable class.
 */
public class HashTableStressTester {
    
    /**
     * Returns a list of songs to use for testing the hash table.
     * @return A list of songs to use for testing the hash table
     */
    private static String songsDatabaseFilename;
    private static ArrayList<Song> songs;
    

    
    public static void main(String[] args) throws IOException{
	if (args.length > 0)
	    songsDatabaseFilename = args[0];
	else
	    songsDatabaseFilename = "./assignment4/songSubset.txt";
	songs = readSongDataset();
	
	testPut(1000);//200 ms
	testGet(1000);//100 ms
	testSearchByYear(1000);//300 ms
	testSearchByArtist(500);//5 ms
	testSearchByTitle(500);//10 ms
	System.exit(0);
    }

    private static ArrayList<Song> readSongDataset() throws IOException{
	ArrayList<Song> allSongs = new ArrayList<>();
	BufferedReader songsReader;
	try{
	    songsReader = new BufferedReader( new FileReader(songsDatabaseFilename));
	    int counter = 0;
	    String line;
	    while((line = songsReader.readLine()) != null){
		Integer year = Integer.valueOf(line);
		String artist = songsReader.readLine();
		String songName = songsReader.readLine();
		allSongs.add(new Song(songName,artist,year));
	    }
	    return allSongs;
	}
	catch (IOException e){
	    System.out.println("[IOError]:: Error reading file  falling back to static array");
	    return allSongs;
	}
    }

    private static void testPut(Integer timeOut){
	System.out.println("----------------Put Stress Test------------");
	StressTest putTest = new StressTestPut(timeOut);
	putTest.setData(songs);
	Long testTime = putTest.run();
	if (testTime < timeOut*2)
	    System.out.println("Put stress test approx time : "+ testTime+ "ms");
	return;
    }

    private static void testGet(Integer timeOut){
	System.out.println("----------------Get Stress Test------------");
	StressTestGet getTest = new StressTestGet(timeOut);
	getTest.setData(songs);
	getTest.createHashTable();
	Long testTime = getTest.run();
	if (testTime < timeOut*2)
	    System.out.println("Get stress test approx time : "+ testTime+ "ms");

    }
    private static void testSearchByYear(Integer timeOut){
	System.out.println("----------------Search By Year Stress Test------------");
	StressTestSearchByYear sYearTest = new StressTestSearchByYear(timeOut);
	sYearTest.setData(songs);
	Long testTime = sYearTest.run();
	if (testTime < timeOut*2)
	    System.out.println("Search by year stress test approx time : "+ testTime+ "ms");
	return;
    }

    private static void testSearchByArtist(Integer timeOut){
	System.out.println("----------------Search By Artist Stress Test------------");
	StressTestSearchByArtist sArtistTest = new StressTestSearchByArtist(timeOut);
	sArtistTest.setData(songs);
	Long testTime = sArtistTest.run();
	if (testTime < timeOut*2)
	    System.out.println("Search by artist stress test approx time : "+ testTime+ "ms");
	return;
    }

    private static void testSearchByTitle(Integer timeOut){
	System.out.println("----------------Search By Title Stress Test------------");
	StressTestSearchByTitle sTitleTest = new StressTestSearchByTitle(timeOut);
	sTitleTest.setData(songs);
	Long testTime = sTitleTest.run();
	if (testTime < timeOut*2)
	    System.out.println("Search by title stress test approx time : "+ testTime+ "ms");
	return;
    }

}
