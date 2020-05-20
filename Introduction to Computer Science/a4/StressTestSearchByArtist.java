//package assignment4;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;

public class StressTestSearchByArtist extends StressTest{
    MusicStore store;
    private final int SEARCH_QUERY_COUNT = 1000;
    private ArrayList<HashSet<Song>> searchResults;
    private ArrayList<String> searchQueries;
    private Boolean extraVerbose;

    StressTestSearchByArtist(Integer timeOut){
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
	    String randomArtist = data.get(idx).getArtist();
	    searchQueries.add(randomArtist);
	    HashSet<Song> songsByArtist = new HashSet<>();
	    for (Song song: data) {
		if(song.getArtist().equals(randomArtist))
		    songsByArtist.add(song);
	    }
	    searchResults.add(songsByArtist);
	}

    }
    
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
    Boolean tester(){
	try{
	    int k = 0;
	    for (String queryArtist: searchQueries){
		HashSet<Song> mySongsByArtist = new HashSet<>(this.store.searchByArtist(queryArtist));

		if(!mySongsByArtist.equals(searchResults.get(k++))){
		    if(verbose){
			this.err.println("[FAIL] failed to return all " + searchResults.get(k).size() +" songs by "+ queryArtist);
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
