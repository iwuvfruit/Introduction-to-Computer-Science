//package assignment4;
import java.util.Hashtable;

public class StressTestGet extends StressTest{
    private MyHashTable<String, Song> songTable;
    private Hashtable<String, Song> javaSongTable;
    StressTestGet(Integer timeOut){
    	super(timeOut);
    }
    
    public void createHashTable(){
	int bucketCount = (int)(data.size()/0.75) + 1;
	bucketCount /= 4; //trigger rehash
	songTable = new MyHashTable<String,Song>(bucketCount);
	javaSongTable = new Hashtable<String,Song>(bucketCount);
	for (Song song: data) {
	    songTable.put(song.getTitle(), song);
	    javaSongTable.put(song.getTitle(), song);
	}
    }
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
    Boolean tester(){
	try{
	    for (Song song: data) {
		String testKey = song.getTitle();
		Song mySong = songTable.get(testKey);
		Song javaSong = javaSongTable.get(testKey);
		if( !mySong.getArtist().equals(javaSong.getArtist()) ||
		    !mySong.getTitle().equals(javaSong.getTitle()) ||
		    mySong.getYear() != javaSong.getYear()){
		    this.out.println("[FAIL] Get returned wrong value");
		    if(verbose){
			this.err.println("Expected: " + javaSong);
			this.err.println("Found   : " + mySong);
		    }
		    return false;
		}
	    }
	    if(verbose){
		this.out.println("[PASS] returned all "+ songTable.size() + " songs correctly");
	    }
	    return true;
	}catch(Exception e){
	    this.err.println(e);
	    return false;
	}
    }

}
