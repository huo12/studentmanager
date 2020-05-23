package huo.chen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import huo.chen.dao.ClazzDao;
import huo.chen.entity.Clazz;
import huo.chen.service.ClazzService;
@Service
public class ClazzServiceImpl implements ClazzService{

	@Autowired
	private ClazzDao clazzdao;
	
	@Override
	public List<Clazz> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return  clazzdao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return clazzdao.getTotal(queryMap);
	}

	@Override
	public int edit(Clazz clazz) {
		// TODO Auto-generated method stub
		return clazzdao.edit(clazz);
	}

	@Override
	public int add(Clazz clazz) {
		// TODO Auto-generated method stub
		return clazzdao.add(clazz);
	}

	@Override
	public int delete(String joinString) {
		// TODO Auto-generated method stub
		return clazzdao.delete(joinString);
	}

	@Override
	public List<Clazz> findAll() {
		// TODO Auto-generated method stub
		return clazzdao.findAll();
	}


}
