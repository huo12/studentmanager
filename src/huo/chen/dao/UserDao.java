package huo.chen.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import huo.chen.entity.User;

@Repository
public interface UserDao {
	public User findUserName(String username);
	public int add(User user) ;
	public int edit(User user) ;
	public int delete(String ids) ;
	public List<User> findList(Map<String, Object> queryMap);
	public int getTotal(Map<String, Object> queryMap);
}
