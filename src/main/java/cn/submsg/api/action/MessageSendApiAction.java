package cn.submsg.api.action;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.module.web.action.JsonBaseActionSupport;

import cn.submsg.api.bean.SendMessageResult;
import cn.submsg.api.service.ApiService;

/**
 * 短信发送api
 * @author dogdog
 *
 */
public class MessageSendApiAction extends JsonBaseActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String appid;//应用id
	private String tempid;//短信模版id
	private String to;//发送的目标手机号
	private String timestamp;//服务端时间戳
	private String signature;//签名
	private String sign_type;//签名类型 默认为明文密钥签名  normal  or  md5  or sha1
	private String vars;//变量参数
    public String execute(){
    	ApiService apiService = ServiceCacheFactory.getService(ApiService.class);
    	SendMessageResult result = apiService.sendMsg(appid, tempid, to, timestamp, signature, sign_type, vars,"xsend.json",super.ip());
    	return this.renderObjectResult(result);
    }
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTempid() {
		return tempid;
	}
	public void setTempid(String tempid) {
		this.tempid = tempid;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getVars() {
		return vars;
	}
	public void setVars(String vars) {
		this.vars = vars;
	}
}
