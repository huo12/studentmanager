package huo.chen.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import huo.chen.entity.Clazz;
@Repository
public interface ClazzDao {

	public List<Clazz> findList(Map<String, Object> queryMap);

	public int getTotal(Map<String, Object> queryMap);
	
	public int edit(Clazz clazz);
	
	public int add(Clazz clazz);
	
	public int delete(String joinString);

	public List<Clazz> findAll();
}
