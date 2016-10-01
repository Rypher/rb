package com.rossbailey.services;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rossbailey.objects.Session;

@Service
public class PageService {

	@Autowired
	SessionService sessionService;

	public Map<String, Object> getPage(String cid) {

		Session session = null;
		if (cid != null) {
			session = sessionService.get(cid);
		}
		if (session == null) {
			session = sessionService.createSession();
		}

		Map<String, Object> map = new HashMap<>();

		map.put("session", session);

		return map;
	}
}
