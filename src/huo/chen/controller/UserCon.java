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

	//�û������б�ҳ
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/user_list");
		return model;
	}
	
	
	//ɾ���û�
		@RequestMapping(value="/delete",method=RequestMethod.POST)
		@ResponseBody
		public Map<String , String> delete(
				@RequestParam(value="ids[]",required=true) Long[] ids
				) {
			Map<String,String> ret=new HashMap<String,String>();
			if (ids==null) {
				ret.put("type","error");
				ret.put("msg", "��ѡ��Ҫɾ��������");
				return ret;
			}
			String idstring=""; 
			for(Long id:ids) {
				idstring+=id+",";
				
			}
			idstring =idstring.substring(0, idstring.length()-1);
			if (userservice.delete(idstring)<=0) {
				ret.put("type","error");
				ret.put("msg", "ɾ��ʧ��");
				return ret;
			}
			ret.put("type","success");
			ret.put("msg", "ɾ���ɹ���");
			return ret;
			}
	
	//�޸��û���Ϣ 
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String , String> edit(User user) {
		Map<String,String> ret=new HashMap<String,String>();
		if (user==null) {
			ret.put("type","error");
			ret.put("msg", "���ݰ󶨳���");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "�û�������Ϊ��");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "���벻��Ϊ��");
			return ret;
		}
		User existUser= userservice.findUserName(user.getUsername());
		if (existUser!=null) {
			if (user.getId()!=existUser.getId()) {
				ret.put("type","error");
				ret.put("msg", "���û����Ѵ���!");
				return ret;
			}
			
		}
		if (userservice.edit(user)<=0) {
			ret.put("type","error");
			ret.put("msg", "�޸�ʧ��");
			return ret;
			
		}
		ret.put("type","success");
		ret.put("msg", "�޸ĳɹ���");
		return ret;
	
	}
	
	
	
	
	//��ȡ�û��б�
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
	//����û�
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String , String> add(User user) {
		Map<String,String> ret=new HashMap<String,String>();
		if (user==null) {
			ret.put("type","error");
			ret.put("msg", "���ݰ󶨳���");
			return ret;
		}
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type","error");
			ret.put("msg", "�û�������Ϊ��");
			return ret;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type","error");
			ret.put("msg", "���벻��Ϊ��");
			return ret;
		}
		User existUser= userservice.findUserName(user.getUsername());
		if (existUser!=null) {
			ret.put("type","error");
			ret.put("msg", "���û����Ѵ���!");
			return ret;
		}
		
		
		if (userservice.add(user)<=0) {
			ret.put("type","error");
			ret.put("msg", "���ʧ��");
			return ret;
			
		}
		ret.put("type","success");
		ret.put("msg", "��ӳɹ���");
		return ret;
	
	}
	
}
