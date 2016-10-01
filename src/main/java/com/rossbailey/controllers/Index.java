package com.rossbailey.controllers;


import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(@CookieValue(value = "cid", required = false) String cid,
						HttpServletResponse response) throws JsonProcessingException {

		Map<String, Object> model = pageService.getPage(cid);

		String json = objectMapper.writeValueAsString(model);
		model.put("json", json);

		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/index.vtl", "UTF-8", model);

		Session session = (Session) model.get("session");
		response.addCookie(new Cookie("cid", session.getCid()));

		return text;
	}

}
