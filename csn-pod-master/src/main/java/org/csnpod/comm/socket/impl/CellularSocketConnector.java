package org.csnpod.comm.socket.impl;

import org.csnpod.comm.atcmd.SocketAtCmd;
import org.csnpod.comm.data.CommConfig;
import org.csnpod.comm.data.ConnectResultType;
import org.csnpod.comm.socket.SocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellularSocketConnector implements SocketConnector {
	private Logger logger = LoggerFactory.getLogger(CellularSocketConnector.class);
	private SocketAtCmd sockAtCmd;
	private String remoteAddr;
	private String localAddr;
	private int remotePort;
	private int localPort;

	public CellularSocketConnector(SocketAtCmd sockAtCmd) {
		this.sockAtCmd = sockAtCmd;
	}

	@Override
	public ConnectResultType connect(String addr, int port) {
		logger.trace("Start connect method");
		
		this.remoteAddr = addr;
		this.remotePort = port;
		String tempAddr = sockAtCmd.prepareSock();
		if(tempAddr == null)
			return ConnectResultType.FAILURE;
		localAddr = tempAddr;
		
		logger.trace("End connect method");
		if(sockAtCmd.connSock(remoteAddr, remotePort)==0){
			return ConnectResultType.SUCCESS;
		}else{
			return ConnectResultType.FAILURE;
		}
	}

	@Override
	public int close() {
		logger.trace("Start close method");
		
		int ret = sockAtCmd.closeSock();
		
		if(ret > -1)
			ret = sockAtCmd.freeSock();
		
		logger.trace("End close method");
		return ret;
	}

	@Override
	public String read(int byteNum) {
		logger.trace("Start read method");
		return sockAtCmd.readData(byteNum);
	}

	@Override
	public int write(String data) {
		logger.trace("Start write method");
		return sockAtCmd.sendData(data);
	}

	@Override
	public String getLocalAddr() {
		return this.localAddr;
	}

	@Override
	public String getRemoteAddr() {
		return this.remoteAddr;
	}

	@Override
	public int getLocalPort() {
		return this.localPort;
	}

	@Override
	public int getRemotePort() {
		return this.remotePort;
	}

}
