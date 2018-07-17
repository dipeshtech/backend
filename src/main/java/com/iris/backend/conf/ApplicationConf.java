package com.iris.backend.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ApplicationConf {

	private String intentUrl;

	public String getIntentUrl() {
		return intentUrl;
	}

	public void setIntentUrl(String intentUrl) {
		this.intentUrl = intentUrl;
	}
	
}
