package org.cjf.android.framework.push;

//连接Push消息回调接口
public interface IPushMsgCallBack {
	//服务器端发送消息回调接口 传回消息
	public void publishMsg(byte[] msg);
	
	//失去链接回调接口   失去链接后判断是否有网络后重新连接
	public void connectionLost();
	
	//断开连接回调函数
	public void disconnect();
	
}
