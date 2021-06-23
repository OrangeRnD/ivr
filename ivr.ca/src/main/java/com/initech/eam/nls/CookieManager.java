package com.initech.eam.nls;

import java.util.ArrayList;
import java.util.List;

public class CookieManager {
	
	public static void setEncStatus(boolean b) {
		
	}
	
	public static List readClientTicket(String encTicket2, String hmac) throws Exception {
		List list = new ArrayList();
		list.add(encTicket2);
		return list;
	}
}
