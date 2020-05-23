package huo.chen.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import huo.chen.entity.User;
import huo.chen.page.Page;
import huo.chen.service.UserService;

@RequestMapping("/user")
@Controller
public class UserCon {
	
	
	@Autowired
	public UserService userservice;

	//用户管理列表页
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/user_list");
		return model;
	}
	
	
	//删除用户
		@RequestMapping(value="/delete",method=RequestMethod.POST)
		@ResponseBody
		public Map<String , String> delete(
				@RequestParam(value="ids[]",required=true) Long[] ids
				) {
			Map<String,String> ret=new HashMap<String,String>();
			if (ids==null) {
				ret.put("type","error");
				ret.put("msg", "请选择要删除的数据");
				return ret;
			}
			String idstring=""; 
			for(Long id:ids) {
				idstring+=id+",";
				
			}
			idstring =idstring.substring(0, idstring.length()-1);
			if (userservice.delete(idstring)<=0) {
				ret.put("type","error");
				ret.put("msg", "删除失败");
				return ret;
			}
			ret.put("type","success");
			ret.put("msg", "删除成功！");
			return ret;
			}
	
	//修改用户信息 
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String , String> edit(User user) {
		Map<String,String> ret=new HashMap<String,String>();
		if (user==null) {
			ret.put("type","error");
			ret.put("msg", "数据绑定出错");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "密码不能为空");
			return ret;
		}
		User existUser= userservice.findUserName(user.getUsername());
		if (existUser!=null) {
			if (user.getId()!=existUser.getId()) {
				ret.put("type","error");
				ret.put("msg", "该用户名已存在!");
				return ret;
			}
			
		}
		if (userservice.edit(user)<=0) {
			ret.put("type","error");
			ret.put("msg", "修改失败");
			return ret;
			
		}
		ret.put("type","success");
		ret.put("msg", "修改成功！");
		return ret;
	
	}
	
	
	
	
	//获取用户列表
	@RequestMapping(value="/get_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String , Object> getList(
			@RequestParam (value ="username",required=false,defaultValue="")String username,
			Page page

			) {
		Map<String,Object> ret=new HashMap<String,Object>();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("username", "%"+username+"%");
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", userservice.findList(queryMap));
		ret.put("total", userservice.getTotal(queryMap));
		return ret;
	}
	//添加用户
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String , String> add(User user) {
		Map<String,String> ret=new HashMap<String,String>();
		if (user==null) {
			ret.put("type","error");
			ret.put("msg", "数据绑定出错");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "密码不能为空");
			return ret;
		}
		User existUser= userservice.findUserName(user.getUsername());
		if (existUser!=null) {
			ret.put("type","error");
			ret.put("msg", "该用户名已存在!");
			return ret;
		}
		
		
		if (userservice.add(user)<=0) {
			ret.put("type","error");
			ret.put("msg", "添加失败");
			return ret;
			
		}
		ret.put("type","success");
		ret.put("msg", "添加成功！");
		return ret;
	
	}
	
}
