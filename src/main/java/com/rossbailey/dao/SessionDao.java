package com.rossbailey.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossbailey.objects.Session;

@Repository
public class SessionDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Session createSession() {

		String sql =
				"insert into session (cid, context, created) " +
				"values" +
				"(uuid_generate_v4(),'{}',now()) " +
				"returning id, cid, context, created";
		Map<String, Object> map = jdbcTemplate.queryForMap(sql);
		return convert(map);
	}

	public Session getSession(String cid) {

		String sql = "select id, cid, context, created from session where cid = '" + cid + "' limit 1";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		if (list.isEmpty()) return null;

		return convert(list.get(0));
	}

	private Session convert(Map<String, Object> map) {
		try {
			map.put("context", objectMapper.readTree((String) map.getOrDefault("context", "{}")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Session session = objectMapper.convertValue(map, Session.class);
		return session;
	}


}

