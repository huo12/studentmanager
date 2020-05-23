package huo.chen.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import huo.chen.entity.Clazz;
@Service
public interface ClazzService {

	public List<Clazz> findList(Map<String, Object> queryMap);

	public int getTotal(Map<String, Object> queryMap);

	public int edit(Clazz clazz);

	public int add(Clazz clazz);

	public int delete(String joinString);

	public List<Clazz> findAll();
}
