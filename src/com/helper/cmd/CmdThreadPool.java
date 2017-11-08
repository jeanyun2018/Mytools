package com.helper.cmd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.helper.util.StringTools;
/**
 * 		cmd√¸¡Ó÷¥––Ω¯≥Ã≥ÿ
 * @author jx
 *
 */
public class CmdThreadPool {
	private ExecutorService pool;

	public CmdThreadPool(int threadMax) {
		this.pool = Executors.newFixedThreadPool(threadMax);
		
	}
	public void shutdown()
	{
		pool.shutdown();
	}
	public void addCmd(RunCmd cmd)
	{
		if(StringTools.StringIsNotEmpty(cmd.getCmd()))
		this.pool.execute(cmd);
		
	}
}
