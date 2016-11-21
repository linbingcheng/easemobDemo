package com.linbingcheng.easemob.api.impl;

import com.easemob.server.example.comm.ClientContext;
import com.linbingcheng.easemob.api.RestAPI;
import com.linbingcheng.easemob.api.RestAPIInvoker;

public abstract class EasemobRestAPI implements RestAPI {
	
	private ClientContext context;
	
	private RestAPIInvoker invoker;

	public abstract String getResourceRootURI();
	
	public ClientContext getContext() {
		return context;
	}

	public void setContext(ClientContext context) {
		this.context = context;
	}

	public RestAPIInvoker getInvoker() {
		return invoker;
	}

	public void setInvoker(RestAPIInvoker invoker) {
		this.invoker = invoker;
	}
	
	
}
