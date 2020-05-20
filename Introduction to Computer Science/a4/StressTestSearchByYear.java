//package assignment4;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;

public class StressTestSearchByYear extends StressTest{
    MusicStore store;
    private final int SEARCH_QUERY_COUNT = 1000;
    private ArrayList<HashSet<Song>> searchResults;
    private ArrayList<Integer> searchQueries;
    private Boolean extraVerbose;

    StressTestSearchByYear(Integer timeOut){
    	super(timeOut);
	searchResults = new ArrayList<>();
	searchQueries = new ArrayList<>();
	extraVerbose = false;
    }

    @Override
    public void setData(ArrayList<Song> songs){
	this.data = songs;
	this.store = new MusicStore(this.data);
	for (int searches = 0; searches < SEARCH_QUERY_COUNT; ++searches){
	    Integer idx = new Random().nextInt(data.size());
	    Integer randomYear = data.get(idx).getYear();
	    searchQueries.add(randomYear);
	    HashSet<Song> songsInTheYear = new HashSet<>();
	    for (Song song: data) {
		if(song.getYear() == randomYear)
		    songsInTheYear.add(song);
	    }
	    searchResults.add(songsInTheYear);
	}

    }
    
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
    Boolean tester(){
	try{
	    int k = 0;
	    for (Integer queryYear: searchQueries){
		HashSet<Song> mySongsInTheYear = new HashSet<>(this.store.searchByYear(queryYear));
		
		
		if(!mySongsInTheYear.equals(searchResults.get(k++))){
		    if(verbose){
			this.err.println("[FAIL] failed to return all " + searchResults.get(k).size() +" from "+ queryYear);
			return false;
		    }
		}
	    }
	    return true;
	}catch(Exception e){
	    this.err.println(e);
	    return false;
	}
    }

}
