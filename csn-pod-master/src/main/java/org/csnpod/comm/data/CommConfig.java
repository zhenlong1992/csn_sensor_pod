package org.csnpod.comm.data;

import org.csnpod.comm.modem.ResponseMode;

import com.google.common.base.MoreObjects;

public class CommConfig {

	public static ResponseMode respMode = ResponseMode.NUMERIC;
	public static int statusChkPeriod = 60;
	public static CommunicationType commType = CommunicationType.CELLULAR;

	public static void showCommConfigValue() {
		System.out.println("Response Mode: " + respMode.toString());
		System.out.println("Communication Status Check Period: "
				+ statusChkPeriod + "s");
		System.out.println("CommunicationType: " + commType.toString());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("Response Mode", respMode.toString())
				.add("Status Check Period", statusChkPeriod)
				.add("Communication Type", commType.toString()).toString();
	}
}