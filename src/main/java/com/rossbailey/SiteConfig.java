package com.rossbailey;


import javax.sql.DataSource;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
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

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("postgres");
		dataSource.setPassword("Ratatat!1");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
		dataSource.setDriverClassName("org.postgresql.Driver");

		return dataSource;
	}

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
