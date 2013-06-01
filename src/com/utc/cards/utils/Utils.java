package com.utc.cards.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class Utils
{
    private static Logger _log = LoggerFactory.getLogger(Utils.class);

    public static boolean validateIps(String hostIp, String localIp)
    {
	_log.debug("validateIps({},{})", hostIp, localIp);
	return validateIp(hostIp) && validateIp(localIp)
		&& sameNetwork(hostIp, localIp);
    }

    private static boolean validateIp(String ip)
    {
	_log.debug("validateIp({})", ip);
	String[] ips = new String[4];
	int i = 0;
	int j = ip.indexOf(".");
	String val = ip.substring(i, j);
	ips[0] = val;
	i = ++j;
	j = ip.indexOf(".", j);
	val = ip.substring(i, j);
	ips[1] = val;
	i = ++j;
	j = ip.indexOf(".", j);
	val = ip.substring(i, j);
	ips[2] = val;
	i = ++j;
	val = ip.substring(i);
	ips[3] = val;
	_log.debug("ips : " + ips[0] + "." + ips[1] + "." + ips[2] + "."
		+ ips[3]);
	for (String ipp : ips)
	{
	    Integer intIpp;
	    try
	    {
		_log.debug(ipp);
		intIpp = Integer.parseInt(ipp);
	    } catch (NumberFormatException e)
	    {
		return false;
	    }
	    if (intIpp <= 0 || intIpp >= 255)
	    {
		return false;
	    }
	}
	_log.debug("true");

	return true;
    }

    private static boolean sameNetwork(String ip1, String ip2)
    {
	String[] ips1 = new String[4];
	int i = 0;
	int j = ip1.indexOf(".");
	String val = ip1.substring(i, j);
	ips1[0] = val;
	i = ++j;
	j = ip1.indexOf(".", j);
	val = ip1.substring(i, j);
	ips1[1] = val;
	i = ++j;
	j = ip1.indexOf(".", j);
	val = ip1.substring(i, j);
	ips1[2] = val;
	i = ++j;
	val = ip1.substring(i);
	ips1[3] = val;
	_log.debug("ips : " + ips1[0] + "." + ips1[1] + "." + ips1[2] + "."
		+ ips1[3]);
	String[] ips2 = new String[4];
	i = 0;
	j = ip2.indexOf(".");
	val = ip2.substring(i, j);
	ips2[0] = val;
	i = ++j;
	j = ip2.indexOf(".", j);
	val = ip2.substring(i, j);
	ips2[1] = val;
	i = ++j;
	j = ip2.indexOf(".", j);
	val = ip2.substring(i, j);
	ips2[2] = val;
	i = ++j;
	val = ip2.substring(i);
	ips2[3] = val;
	_log.debug("ips : " + ips2[0] + "." + ips2[1] + "." + ips2[2] + "."
		+ ips2[3]);
	return ips1[0].equals(ips2[0]) && ips1[1].equals(ips2[1])
		&& ips1[2].equals(ips2[2]);
    }

    public static String getLocalWifiIpAddress(Application application)
    {
	WifiManager myWifiManager = (WifiManager) application
		.getSystemService(Context.WIFI_SERVICE);
	WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
	int myIp = myWifiInfo.getIpAddress();
	int intMyIp3 = myIp / 0x1000000;
	int intMyIp3mod = myIp % 0x1000000;
	int intMyIp2 = intMyIp3mod / 0x10000;
	int intMyIp2mod = intMyIp3mod % 0x10000;
	int intMyIp1 = intMyIp2mod / 0x100;
	int intMyIp0 = intMyIp2mod % 0x100;

	String localIpAddress = String.valueOf(intMyIp0) + "."
		+ String.valueOf(intMyIp1) + "." + String.valueOf(intMyIp2)
		+ "." + String.valueOf(intMyIp3);
	return localIpAddress;
    }

}
