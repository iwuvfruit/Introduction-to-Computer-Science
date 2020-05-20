//package assignment4;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.Callable;
import java.io.PrintStream;
import java.util.ArrayList;

abstract  class StressTest{
    protected  Integer timeOut;
    protected  PrintStream out;
    protected  PrintStream err;
    protected  Boolean verbose;
    protected ArrayList<Song > data; 
    StressTest(Integer timeOut){
	this.timeOut = timeOut;
	out = System.out;
	err = System.err;
	verbose = true;
    }
    void setData(ArrayList<Song> songs){
	this.data = songs;
    }
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
    abstract Boolean tester();
    
    public Long run(){
	StressTestCaller job = new StressTestCaller();
	final ExecutorService service = Executors.newSingleThreadExecutor();
	final Future<Long> futureResult  = service.submit(job);
	Long result = 0l;
	try{
	    result = futureResult.get(timeOut, TimeUnit.MILLISECONDS);// TimeUnit.SECONDS);
	}catch(Exception e){
	    this.out.println(e.getClass().getSimpleName());
	    result = (new Long(StressTest.this.timeOut))*5;
	}
	return result;
	
    }

    private class StressTestCaller implements Callable<Long>{
	public StressTestCaller(){}
	public Long call(){
	    try{
		//time hack
		Long startTime = System.currentTimeMillis();
		tester();
		return System.currentTimeMillis() - startTime;
	    }catch(Exception e){
		out.println("Exception: " + e);
		return (new Long(StressTest.this.timeOut))*5;
	    }
	}
    }
}
