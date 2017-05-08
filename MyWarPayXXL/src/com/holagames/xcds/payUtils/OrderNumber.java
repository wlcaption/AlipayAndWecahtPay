package com.holagames.xcds.payUtils;

import java.io.Serializable;

/**
 * @author Сκ 
 * @Email: wlcaption@gmail.com
 * 2017-2-22 ����4:28:45
 */
public class OrderNumber implements Serializable {
	private static final long serialVersionUID = 1L;
	private String out_trade_no;
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	
	

}
