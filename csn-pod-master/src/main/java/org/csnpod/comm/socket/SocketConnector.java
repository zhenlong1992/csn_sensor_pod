package org.csnpod.comm.socket;

import org.csnpod.comm.data.ConnectResultType;

public interface SocketConnector {
	
	public ConnectResultType connect(String addr, int port);
	
	public int close();
	
	public String read(int byteNum);
	
	public int write(String data);
	
	public String getLocalAddr();
	
	public String getRemoteAddr();
	
	public int getLocalPort();
	
	public int getRemotePort();

}
