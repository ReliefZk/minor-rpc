package com.zk.rpc.common.common;

import java.net.URI;

public class TransportURL {
	private String host;
	private int port;
	private String path;

	public TransportURL(String url) {
		URI uri = URI.create(url);
		this.host = uri.getHost();
		this.port = uri.getPort();
		this.path = uri.getPath().substring(1);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "TransportURL [host=" + host + ", port=" + port + ", path=" + path + "]";
	}

}
