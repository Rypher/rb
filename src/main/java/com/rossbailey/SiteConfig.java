package com.rossbailey;


import javax.sql.DataSource;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.rossbailey")
public class SiteConfig {

	@Bean
	public VelocityEngine velocityEngine() {
		VelocityEngine engine = new VelocityEngine();
		engine.addProperty("resource.loader", "classpath");
		engine.addProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		return engine;
	}

	private static final String host = "rdsp1.cfh6ek67sfzu.us-west-2.rds.amazonaws.com";
	private static final Integer port = 5432;
	private static final String user = "rmb";
	private static final String instance = "core";

	@Value("postgres.pass")
	String postgresPassword;

	@Bean
	public DataSource dataSource() {

		if (postgresPassword == null || postgresPassword.isEmpty()) {
			System.out.println("postgresPassword is blank");
			System.exit(0);
		}

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:postgresql://" + host + ":" + port + "/" + instance);
		dataSource.setUsername(user);
		dataSource.setPassword(postgresPassword);
		dataSource.setDriverClassName("org.postgresql.Driver");

		return dataSource;
	}

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
