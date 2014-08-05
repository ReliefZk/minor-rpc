package com.zk.rpc.common.common;
/**
 * 网络配置信息
 * @author sunney
 *
 */
public class NetConfig {
	private int connectTimeout = 10;// 秒
	private int readTimeout = 10;// 秒
	private boolean tcpNoDelay = true;
	private boolean reuseAddress = true;
	private int soLinger = -1;
	private int sendBufferSize = 256;
	private int receiveBufferSize = 1024;

	public NetConfig() {
		super();
	}

	public NetConfig(int connectTimeout, int readTimeout) {
		super();
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}

	public NetConfig(int connectTimeout, int readTimeout, boolean tcpNoDelay, boolean reuseAddress, int soLinger, int sendBufferSize, int receiveBufferSize) {
		super();
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.tcpNoDelay = tcpNoDelay;
		this.reuseAddress = reuseAddress;
		this.soLinger = soLinger;
		this.sendBufferSize = sendBufferSize;
		this.receiveBufferSize = receiveBufferSize;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}

	public boolean isReuseAddress() {
		return reuseAddress;
	}

	public void setReuseAddress(boolean reuseAddress) {
		this.reuseAddress = reuseAddress;
	}

	public int getSoLinger() {
		return soLinger;
	}

	public void setSoLinger(int soLinger) {
		this.soLinger = soLinger;
	}

	public int getSendBufferSize() {
		return sendBufferSize;
	}

	public void setSendBufferSize(int sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
	}

	public int getReceiveBufferSize() {
		return receiveBufferSize;
	}

	public void setReceiveBufferSize(int receiveBufferSize) {
		this.receiveBufferSize = receiveBufferSize;
	}

}
