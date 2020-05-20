//package assignment4;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;

public class StressTestSearchByTitle extends StressTest{
    MusicStore store;
    private final int SEARCH_QUERY_COUNT = 10000;
    private ArrayList<String> searchQueries;

    StressTestSearchByTitle(Integer timeOut){
    	super(timeOut);
	searchQueries = new ArrayList<>();
    }

    @Override
    public void setData(ArrayList<Song> songs){
	this.data = songs;
	this.store = new MusicStore(this.data);
	for (int searches = 0; searches < SEARCH_QUERY_COUNT; ++searches){
	    Integer idx = new Random().nextInt(data.size());
	    String randomTitle = data.get(idx).getTitle();
	    searchQueries.add(randomTitle);
	}

    }
    
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
    Boolean tester(){
	try{
	    int k = 0;
	    for (String queryTitle: searchQueries){
		Song mySongByTitle = this.store.searchByTitle(queryTitle);
		
		if(!mySongByTitle.getTitle().equals(queryTitle)){
		    if(verbose){
			this.err.println("[FAIL] failed to return  songs titled \"" + queryTitle + "\"");
			this.err.println("Returned " + mySongByTitle);
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
