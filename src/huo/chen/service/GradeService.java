package huo.chen.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import huo.chen.entity.Grade;

@Service
public interface GradeService {
	public int add(Grade grade) ;
	public int edit(Grade grade) ;
	public int delete(String ids) ;
	public List<Grade> findList(Map<String, Object> queryMap);
	public int getTotal(Map<String, Object> queryMap);
	public List<Grade> findAll();
}
