package com.rossbailey.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rossbailey.dao.SessionDao;
import com.rossbailey.objects.Session;

@Service
public class SessionService {

	@Autowired
	SessionDao sessionDao;

	public Session createSession() {
		return sessionDao.createSession();
	}

	public Session get(String cid) {
		return sessionDao.getSession(cid);
	}

}
