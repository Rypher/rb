package com.rossbailey.dao;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossbailey.objects.RossponsysEvent;

@Repository
public class RossponsysDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@PostConstruct
	public void init() {
		jdbcTemplate.execute("CREATE TABLE " +
				"IF NOT EXISTS rossponsysevent( " +
				" id SERIAL PRIMARY KEY, " +
				" cid UUID not null,  " +
				" event text, " +
				" created timestamp " +
				");");
	}

	public void putEvent(Map<String, Object> event) {


		String sql = null;
		try {
			sql = "insert into rossponsysevent (cid, event, created) " +
			"values" +
			"(uuid_generate_v4(),'" +objectMapper.writeValueAsString(event)+ "',now()) " +
			"returning id, cid, event, created";

			Map<String, Object> map = jdbcTemplate.queryForMap(sql);
			System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public List<RossponsysEvent> getEvents(Integer id) {

		String sql = "select id, cid, event, created from rossponsysevent where id > '" + id + "' order by id asc limit 100";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		if (list.isEmpty()) return Collections.EMPTY_LIST;

		return list.stream().map(this::convert).collect(Collectors.toList());
	}

	private RossponsysEvent convert(Map<String, Object> map) {
		try {
			map.put("event", objectMapper.readTree((String) map.getOrDefault("event", "{}")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		RossponsysEvent event = objectMapper.convertValue(map, RossponsysEvent.class);
		return event;
	}


}

