package huo.chen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import huo.chen.dao.GradeDao;
import huo.chen.entity.Grade;
import huo.chen.service.GradeService;
@Service
public class GradeServiceImpl implements GradeService{

	@Autowired
	private GradeDao gradedao;
	
	
	@Override
	public int add(Grade grade) {
		// TODO Auto-generated method stub
		return gradedao.add(grade);
	}

	@Override
	public int edit(Grade grade) {
		// TODO Auto-generated method stub
		return gradedao.edit(grade);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return gradedao.delete(ids);
	}

	@Override
	public List<Grade> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return gradedao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return gradedao.getTotal(queryMap);
	}

	@Override
	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		return gradedao.findAll();
	}

}
