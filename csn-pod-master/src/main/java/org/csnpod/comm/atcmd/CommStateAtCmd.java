package org.csnpod.comm.atcmd;

import java.util.List;

import org.csnpod.comm.modem.ModemIO;
import org.csnpod.comm.modem.ResponseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommStateAtCmd extends DefaultAtCmd {
	private Logger logger = LoggerFactory.getLogger(CommStateAtCmd.class);

	public CommStateAtCmd(ModemIO io, ResponseMode mode) {
		super(io, mode);
	}
	
	/**
	 * 
	 * @return 1은 네트워크 연결, 0은 네트워크 연결 안 됨, -1은 Error
	 */
	public int checkCREG() {
		logger.trace("Start checkCREG method");
		
		logger.debug("AT CMD: AT+CREG?");
		List<String> respLines = processCMD("AT+CREG?");
		String finalResp = respLines.get(respLines.size() - 1);

		if (cmeHdlr.isCmeError(finalResp)) {
			cmeHdlr.checkCmeError(finalResp);
			return -1;
		} else {
			logger.trace("None CME Error");
		}

		int cregResult = -1;
		if (respHdlr.isRespCode(finalResp)) {
			for (String resp : respLines) {
				logger.debug("Temporary response line: \"{}\"", resp);
				if (resp.contains("+CREG:")) {
					cregResult = Integer.parseInt(resp.substring(9));
					logger.debug("CREG Result: {}", cregResult);
				} else {
					cregResult = -1;
				}
			}

			if (cregResult == 0)
				return -1;

			return 1;
		} else {
			logger.error("Can't find appropriate Response! Unknown Error: {}",
					finalResp);
			return -1;
		}
	}

	/**
	 * 
	 * @return 1은 신호상태 양호, 0은 신호상태 불량 혹은 단절, -1은 Error
	 */
	public int checkCSQ() {
		logger.trace("Start checkCSQ method");
		
		logger.debug("AT CMD: AT+CSQ");
		List<String> respLines = processCMD("AT+CSQ");
		String finalResp = respLines.get(respLines.size() - 1);

		if (cmeHdlr.isCmeError(finalResp)) {
			cmeHdlr.checkCmeError(finalResp);
			return -1;
		} else {
			logger.trace("None CME Error");
		}

		int signalStrength = 0;
		if (respHdlr.isRespCode(finalResp)) {
			for (String resp : respLines) {
				logger.debug("Temporary response line: \"{}\"", resp);
				if (resp.contains("+CSQ:")) {
					signalStrength = Integer.parseInt(resp.substring(6).split(
							",")[0]);
					logger.debug("CSQ Result: {}", signalStrength);
				}
			}

			// 신호세기에 대한 해석 7 이하이거나 31이 이상이면 연결 안 된 것
			if (signalStrength < 7 || signalStrength > 31)
				return 0;
			else {
				return 1;
			}

		} else {
			logger.error("Can't find appropriate Response! Unknown Error: {}",
					finalResp);
			return -1;
		}
	}

}
