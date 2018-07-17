package com.iris.backend;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class IrisContextAware implements ApplicationContextAware {

	static private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext input) throws BeansException {
		applicationContext = input;
	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}

}