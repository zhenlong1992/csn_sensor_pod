package org.csnpod.comm.socket;

import org.csnpod.comm.atcmd.SocketAtCmd;
import org.csnpod.comm.socket.impl.CellularSocketConnector;
import org.csnpod.comm.socket.impl.EthernetSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketConnFactory {
	private Logger logger = LoggerFactory.getLogger(SocketConnFactory.class);

	public CellularSocketConnector getCellularConnector(SocketAtCmd sockAtCmd) {
		logger.trace("CellularSocketConnector will be created");
		return new CellularSocketConnector(sockAtCmd);
	}

	public EthernetSocketConnector getEthernetConnector() {
		logger.trace("EthernetSocketConnector will be created");
		return new EthernetSocketConnector();
	}
}
