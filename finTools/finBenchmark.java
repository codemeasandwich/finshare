 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Object:finBenchmark*****/

package finTools;

import java.lang.System;
 
public class finBenchmark 
{
	private long startTimer;
	private long endTimer;
	private long elapsed;
	 
	public finBenchmark()
	{
		startTimer = System.currentTimeMillis();
	}
	public void reStart()
	{
		startTimer = System.currentTimeMillis();
	}
	public String elapsed()
	{
		stop();
		return new String((elapsed/1000)+"."+(elapsed%1000));
	}
	public long elapsedSeconds()
	{
		stop();
		return elapsed/1000;
		
	}
	public long elapsedMilliseconds()
	{
		stop();
		return elapsed%1000;
	}
	
	private void stop()
	{
		endTimer = System.currentTimeMillis();
		elapsed = endTimer - startTimer;
	}
	public String toString()
	{
		return "This Benchmarking class was Built for the finShare project";
	}
	
}
