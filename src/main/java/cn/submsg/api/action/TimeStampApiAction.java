package cn.submsg.api.action;

import com.sr178.module.web.action.JsonBaseActionSupport;
/**
 * 获取服务器时间戳
 * @author dogdog
 */
public class TimeStampApiAction extends JsonBaseActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String execute(){
		return this.renderKeyValueResult("timestamp", System.currentTimeMillis());
	}
}
