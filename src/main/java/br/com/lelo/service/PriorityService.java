package br.com.lelo.service;

import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import br.com.lelo.domain.User;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PriorityService {

	private Map<String, Integer> priorityMap = Maps.newHashMap();

	public void setUserPriority(User user) {
		if (priorityMap.containsKey(user.getId()) == false) {
			user.setPriority(3);
		} else {
			user.setPriority(priorityMap.get(user.getId()));
		}
	}

	public void add(String lineId, int priority) {
		priorityMap.put(lineId, priority);
	}
}
