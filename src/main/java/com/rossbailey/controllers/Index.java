package com.rossbailey.controllers;


import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossbailey.dao.RossponsysDao;
import com.rossbailey.objects.RossponsysEvent;
import com.rossbailey.objects.Session;
import com.rossbailey.services.PageService;
import com.rossbailey.services.SessionService;


@RestController
@RequestMapping(value = "/")
public class Index {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	VelocityEngine velocityEngine;

	@Autowired
	SessionService sessionService;

	@Autowired
	PageService pageService;
	
	@Autowired
	RossponsysDao rossponsysDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(
			@CookieValue(value = "cid", required = false) String cid,
			HttpServletRequest request,
			HttpServletResponse response
	) throws JsonProcessingException {

		Map<String, Object> model = pageService.getPage(cid);

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null || ipAddress.isEmpty()) {
			ipAddress = request.getRemoteAddr();
		}


		String json = objectMapper.writeValueAsString(model);
		model.put("json", json);

		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/index.vtl", "UTF-8", model);

		Session session = (Session) model.get("session");
		response.addCookie(new Cookie("cid", session.getCid()));

		return text;
	}


	@RequestMapping(value = "/events/", method = RequestMethod.PUT)
	public void events(
			@RequestBody() Map<String, Object> model
	) {
		rossponsysDao.putEvent(model);
	}


	@RequestMapping(value = "/events/", params = {"id"}, method = RequestMethod.GET)
	public List<RossponsysEvent> events(
			@RequestParam(value = "id", required = false, defaultValue = "0")Integer id
	) {
		List<RossponsysEvent> events = rossponsysDao.getEvents(id);

		return events;
	}

}
