package com.rossbailey.controllers;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossbailey.services.PageService;
import com.rossbailey.services.SessionService;


@Controller
public class EventSocket {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	VelocityEngine velocityEngine;

	@Autowired
	SessionService sessionService;

	@Autowired
	PageService pageService;

	@MessageMapping("/publish")
	@SendTo("/socket/events")
	public JsonNode publish(JsonNode message) throws Exception {
//		JsonNode node = message.get("name");

		return message;
	}
}
