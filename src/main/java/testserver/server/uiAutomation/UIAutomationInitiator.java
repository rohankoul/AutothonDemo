package testserver.server.uiAutomation;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class UIAutomationInitiator extends Thread{
	String browser;
	String device;
	String status;
	
	
	public String getStatus() {
		return status;
	}
	
	public UIAutomationInitiator(String browser, String device) {
		super();
		this.browser = browser;
		this.device = device;
		this.status = "new";
	}
	
	/*   The main part of the test can be here 
	
	Read from a txt file
	get movie names


	Example is below
	*/
	
	public void run() {
		
		startExecutions();
	}
	
	static int MAX_T = 5;
    public void startExecutions() 
    {
    	
        System.out.println( "Executing test" );
      
        try {
        	
        	ArrayList<String> movies = new ArrayList<String>();
        	movies.add("Inception");
        	movies.add("Se7en");
        	movies.add("Dark Knight");
        	/*movies.add("Secret Life of pets");
        	movies.add("A Quiet Place");
        	movies.add("Close Encounters of the third kind");
        	movies.add("jurassic park");
        	movies.add("Isle of Dogs");
        	movies.add("Kubo and the two strings");
        	movies.add("The Adventures of Tintin");
        	*/
        	ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
        	
        	if(this.device.toLowerCase().equals("android"))
        	{
        		this.device = "Android";
        	}
        	else if(this.device.toLowerCase().equals("ios"))
        	{
        		this.device = "iOS";
        	}
        	else if(this.device.toLowerCase().equals("windows"))
        	{        		
        		this.device = "Windows";
        	}
        	else if(this.device.toLowerCase().equals("firefox"))
        	{        		
        		this.device = "Firefox";
        	}
        	else {
        		
        		
        	}
        	for(String movie:movies)
        	{
        		this.status = "ip";
        		Runnable s1 = new SeleniumStandaloneServer(movie,this.device);
        		pool.execute(s1);		
        	}
        	pool.shutdown();   
        	
        }
        catch(Exception e)
        {
        	System.out.println("Exception e ");
        	e.printStackTrace(System.out);
        	
        }finally {   
        	this.status = "done";
        	
        }
    }
	
	
	
}
