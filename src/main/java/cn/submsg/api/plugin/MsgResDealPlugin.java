package cn.submsg.api.plugin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sr178.game.framework.log.LogSystem;
import com.sr178.game.framework.plugin.IAppPlugin;

import cn.submsg.member.bo.MsgDeleverLog;
import cn.submsg.member.bo.MsgSendLog;
import cn.submsg.member.dao.MsgDeleverLogDao;
import cn.submsg.member.dao.MsgSendLogDao;
import cn.submsg.message.bean.MsgBean;
import cn.submsg.message.service.MessageQueueService;

public class MsgResDealPlugin implements IAppPlugin {
	@Autowired
	private ThreadPoolTaskExecutor taskExecuter;
	
    @Autowired
	private MessageQueueService messageQueueService;
    @Autowired
    private MsgSendLogDao msgSendLogDao;
    
    @Autowired
    private MsgDeleverLogDao msgDeleverLogDao;
    
	@Override
	public void startup() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						MsgBean msgBean = messageQueueService.blockResPopMsg();
						if (msgBean != null) {
							handlerRequest(msgBean);
						} else {
							LogSystem.info("当前没有收到响应的请求");
						}
					} catch (Exception e) {
						LogSystem.error(e, "");
						try {
							Thread.sleep(100);
						} catch (InterruptedException ie) {
							LogSystem.error(ie, "");
						}
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						MsgDeleverLog msgDeleverLog = messageQueueService.blockDeleverResMsg();
						if (msgDeleverLog != null) {
							handlerRequestDelever(msgDeleverLog);
						} else {
							LogSystem.info("当前没有收到发送成功的响应请求！");
						}
					} catch (Exception e) {
						LogSystem.error(e, "");
						try {
							Thread.sleep(100);
						} catch (InterruptedException ie) {
							LogSystem.error(ie, "");
						}
					}
				}
			}
		}).start();
	}
	
	/**
	 * 处理消息
	 * @param msgBean
	 */
	private void handlerRequest(final MsgBean msgBean){
		taskExecuter.execute(new Runnable() {
			@Override
			public void run() {
				try {
				  LogSystem.info("处理一条，message-->api的响应消息"+msgBean.toString());
//				  if(msgBean.getStatus()==MsgSendLog.ST_SEND){
//					  if(!msgSendLogDao.updateLogStatusToSend(msgBean.getSendId(), msgBean.getSendTime())){
//						  LogSystem.error(new RuntimeException("发送状态更新失败"+msgBean.getSendId()),"");
//					  }
//				  }else 
				 if(msgBean.getStatus()==MsgSendLog.ST_FAIL){
					  if(!msgSendLogDao.updateLogStatusToFail(msgBean.getSendId(), msgBean.getResTime())){
						  LogSystem.error(new RuntimeException("失败状态更新失败"+msgBean.getSendId()),"");
					  }
				  }else if(msgBean.getStatus()==MsgSendLog.ST_SUCCESS){
					  if(!msgSendLogDao.updateLogStatusToSuccess(msgBean.getSendId(), msgBean.getResTime(), msgBean.getResCode(), msgBean.getMsgId())){
						  LogSystem.error(new RuntimeException("成功状态更新失败"+msgBean.getSendId()),"");
					  }
				  }else{
					  LogSystem.warn("非正常的响应状态"+msgBean);
				  }
				} catch (Exception e) {
					LogSystem.error(e, "处理消息发生异常");
				}
			}
		});
	}
	
	/**
	 * 处理消息
	 * @param msgBean
	 */
	private void handlerRequestDelever(final MsgDeleverLog msgDeleverLog){
		taskExecuter.execute(new Runnable() {
			@Override
			public void run() {
				try {
					if(msgDeleverLog!=null){
						LogSystem.info("处理一条，网关的delever的响应消息"+msgDeleverLog.toString());
						msgDeleverLogDao.add(msgDeleverLog);
					}
				} catch (Exception e) {
					LogSystem.error(e, "处理消息发生异常");
				}
			}
		});
	}
	
	@Override
	public int cpOrder() {
		return 0;
	}

	@Override
	public void shutdown() throws Exception {
		
		
	}
}
