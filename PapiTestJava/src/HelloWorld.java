import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import GUI.MainWindow;
import PapiManager.*;

public class HelloWorld
{
	public static void main(String[] args)
	{
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						new MainWindow();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
	
    public static void main2(String[] args)
    {
    	//Working the same way, as CPP PapiManagerUsingApp
    	String choosenEventName = "rapl:::PP0_ENERGY:PACKAGE0";
    	ArrayList<PapiEventInfo> choosenEvents = new ArrayList<PapiEventInfo>();
    	ArrayList<PapiEventResult> measurementResults = null;
    	
    	/* PAPI Initialization */
    	if(JPapiManager.Init())
    	{
    		System.out.println("Papi Initalized");
    	}
    	else
    	{
    		System.out.println("Papi not Initalized");
    		return;
    	}
    	
    	/* PAPI Printing PAPI Components and Events */
    	ArrayList<PapiComponentInfo> componentsInfo = JPapiManager.GetComponentsInfo();
		System.out.println("Received information about " + componentsInfo.size() + " PAPI components:");
		
		for (int i = 0; i < componentsInfo.size(); i++) {
    		System.out.println("\t" + componentsInfo.get(i).GetName() + " Number of events: " + componentsInfo.get(i).GetEvents().size());
    		
    		for (int j = 0; j < componentsInfo.get(i).GetEvents().size(); j++)
    		{
    			System.out.println("\t\tName: " + componentsInfo.get(i).GetEvents().get(j).GetName()
    								+ "\tUnit: " + componentsInfo.get(i).GetEvents().get(j).GetUnit()
    								+ "\tCode: " + componentsInfo.get(i).GetEvents().get(j).GetCode()
    								+ "\tEventType: " + componentsInfo.get(i).GetEvents().get(j).GetEventType()
    								+ "\tReturnDataType: " + componentsInfo.get(i).GetEvents().get(j).GetReturnDataType());
    			
    			if(componentsInfo.get(i).GetEvents().get(j).GetName().equals(choosenEventName))
    			{
    				choosenEvents.add(componentsInfo.get(i).GetEvents().get(j));
    			}
    		}
    	}
    	
    	/* Idle 1 */
    	System.out.println("\nStarting measure - Idle 1");
    	if(!JPapiManager.StartMeasure(choosenEvents))
    	{
    		System.out.println("Measure start error. Aborting...");
    		return;
    	}

    	try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

    	System.out.println("Stopping measure");
    	measurementResults = JPapiManager.StopMeasure();
    	
    	System.out.println("Printing results");
		System.out.println("Received result from " + measurementResults.size() + " PAPI events:");
		
    	for (int i = 0; i < measurementResults.size(); i++)
    	{
    		System.out.println("\t" + measurementResults.get(i).GetName() + " event result: " + measurementResults.get(i).GetResult() + measurementResults.get(i).GetUnit());
    	}

    	/* Idle 2 */
    	System.out.println("\nStarting measure - Idle 2");
    	if(!JPapiManager.StartMeasure(choosenEvents))
    	{
    		System.out.println("Measure start error. Aborting...");
    		return;
    	}

    	try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }

    	System.out.println("Stopping measure");
    	measurementResults = JPapiManager.StopMeasure();
    	
    	System.out.println("Printing results");
		System.out.println("Received result from " + measurementResults.size() + " PAPI events:");
		
    	for (int i = 0; i < measurementResults.size(); i++)
    	{
    		System.out.println("\t" + measurementResults.get(i).GetName() + " event result: " + measurementResults.get(i).GetResult() + measurementResults.get(i).GetUnit());
    	}

    	/* BubbleSort */
    	System.out.println("\nStarting measure - BubbleSort");
    	if(!JPapiManager.StartMeasure(choosenEvents))
    	{
    		System.out.println("Measure start error. Aborting...");
    		return;
    	}

    	System.out.println("BubbleSort testing");
    	RunExternalCommnad("./SortingApp b 100");

    	System.out.println("Stopping measure");
    	measurementResults = JPapiManager.StopMeasure();
    	
    	System.out.println("Printing results");
		System.out.println("Received result from " + measurementResults.size() + " PAPI events:");
		
    	for (int i = 0; i < measurementResults.size(); i++)
    	{
    		System.out.println("\t" + measurementResults.get(i).GetName() + " event result: " + measurementResults.get(i).GetResult() + measurementResults.get(i).GetUnit());
    	}

    	/* QuickSort */
    	System.out.println("\nStarting measure - QuickSort");
    	if(!JPapiManager.StartMeasure(choosenEvents))
    	{
    		System.out.println("Measure start error. Aborting...");
    		return;
    	}

    	System.out.println("BubbleSort testing");
    	RunExternalCommnad("./SortingApp q 100");

    	System.out.println("Stopping measure");
    	measurementResults = JPapiManager.StopMeasure();
    	
    	System.out.println("Printing results");
		System.out.println("Received result from " + measurementResults.size() + " PAPI events:");
		
    	for (int i = 0; i < measurementResults.size(); i++)
    	{
    		System.out.println("\t" + measurementResults.get(i).GetName() + " event result: " + measurementResults.get(i).GetResult() + measurementResults.get(i).GetUnit());
    	}
    	
    	/* Printing papi_native_avail command result */
    	System.out.println("Checking GetPapiNativeAvailStringCmdResult()...");
    	CheckStringResult(JPapiManager.GetPapiNativeAvailStringCmdResult(), false);
    	
    	/* Printing papi_component_avail command result */
    	System.out.println("Checking GetPapiComponentAvailCmdResult...");
    	CheckStringResult(JPapiManager.GetPapiComponentAvailCmdResult(), false);

    	/* Printing papi_avail command result */
    	System.out.println("Checking GetPapiAvailCmdResult()...");
    	CheckStringResult(JPapiManager.GetPapiAvailCmdResult(), false);

    	/* PPAPI DeInitialization */
    	if(PapiManager.JPapiManager.Close())
    	{
    		System.out.println("Papi Closed");
    	}
    	else
    	{
    		System.out.println("Papi not Closed");
    	}
    	
    	return;
    }
    
    private static void CheckStringResult(String result, boolean printResult)
    {
    	if(result == null || result.isEmpty())
    	{
    		System.out.println("Result is empty or null");
    	}
    	else
    	{
    		if(printResult)
    		{
    			System.out.println(result);
    		}
    		else
    		{
    			System.out.println("Non-empty result.");
    		}
    	}
    }
    
    private static void RunExternalCommnad(String command)
    {
    	try
    	{
			Runtime r = Runtime.getRuntime();
		    Process p;     // Process tracks one external native process
		    BufferedReader is;  // reader for output of process
		    String line;
		
		    System.out.println("Executing \"" + command + "\" command...");
		    
		    p = r.exec(command);
		
		    is = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
			while ((line = is.readLine()) != null)
			  System.out.println(line);
		    
		    System.out.println("End of command output. Waiting for exit...");
		    System.out.flush();

		    p.waitFor();  // wait for process to complete

		    System.out.println("Process done, exit status was " + p.exitValue());
		
		}
    	catch (IOException e1)
    	{
			e1.printStackTrace();
		}
    	catch (InterruptedException e2)
    	{
			e2.printStackTrace();
		}
    	
	    return;
    }
    
}
