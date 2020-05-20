//package assignment4;

public class StressTestPut extends StressTest{
    StressTestPut(Integer timeOut){
    	super(timeOut);
    }
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
    Boolean tester(){
	try{
	    MyHashTable<String, Song>songTable = new MyHashTable<String,Song>(100);
	    for (Song song: data) {
		songTable.put(song.getTitle(), song);
	    }
	    int tableSize = 46343;//TODO:Change this hardcorded value
	    if( songTable.size() == tableSize){
		if (verbose)
		    this.out.println("[PASS] Put all "+ tableSize +" songs");
		return true;
	    }else{
		if (verbose)
		    this.err.println("[FAIL] Table size incorrect: " + songTable.size());
		return false;
	    }
	}
	catch(Exception e){
	    this.err.println(e);
	    return false;
	}
    }

}
