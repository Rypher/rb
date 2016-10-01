package com.rossbailey.services;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class Main {

	@PostConstruct
	public void init() {

		System.out.println("we have started.");

	}

}
