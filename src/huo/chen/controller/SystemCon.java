package huo.chen.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import huo.chen.entity.User;
import huo.chen.service.UserService;
import huo.chen.util.CpachaUtil;

//系统主页控制器


@RequestMapping("/system")
@Controller
public class SystemCon {
	@Autowired
	private UserService userservice;
	
	
	
	@RequestMapping(value ="/hello",method=RequestMethod.GET)
	public ModelAndView hello( ModelAndView model) {
		model.setViewName("hello");
		model.addObject("user", "霍");
		return model;
	}
	

	@RequestMapping(value ="/index",method=RequestMethod.GET)
	public ModelAndView index( ModelAndView model) {
		model.setViewName("system/index");
		model.addObject("user", "霍");
		return model;
	}
	
	
	
	//登录页面
	@RequestMapping(value ="/login",method=RequestMethod.GET)
	public ModelAndView login( ModelAndView model) {
		model.setViewName("system/login");
		model.addObject("user", "霍");
		return model;
	}
	
	//登录表单提交
	@RequestMapping(value ="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login( 
			@RequestParam(value="username",required=true) String username,
			@RequestParam(value="password",required=true) String password,
			@RequestParam(value="vcode",required=true) String vcode,
			@RequestParam(value="type",required=true) int type,
			HttpServletRequest request
			) {
		Map<String, String> ret=new HashMap<String,String>();
		if (StringUtils.isEmpty(username)) {
			System.out.println(StringUtils.isEmpty(username));
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空");
			return ret;
		}
		if (StringUtils.isEmpty(password)) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		if (StringUtils.isEmpty(vcode)) {
			ret.put("type", "error");
			ret.put("msg", "验证码不能为空！");
			return ret;
		}
		String loginCpacha=(String) request.getSession().getAttribute("loginCpacha");
		System.out.println(loginCpacha);
		if (StringUtils.isEmpty(loginCpacha)) {
			ret.put("type", "error");
			ret.put("msg", "长时间未操作，会话已失效，请刷新后重试！");
			return ret;
		}
		if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
			ret.put("type", "error");
			ret.put("msg", "验证码错误！");
			return ret;
		}
		request.getSession().setAttribute("loginCpacha", null);
		//从数据库中去查找用户
		if (type==1) {//管理员
			User findUserName = userservice.findUserName(username);
			if (findUserName==null) {
				ret.put("type", "error");
				ret.put("msg", "用户名或密码错误！");
				return ret;
			}
			if (!password.equals(findUserName.getPassword())) {
				ret.put("type", "error");
				ret.put("msg", "用户名或密码错误！");
				return ret;
			}
			request.getSession().setAttribute("user", findUserName);
			
		}
		if (type==2) {//学生
			
			
			
		}
		
		ret.put("type", "success");
		ret.put("msg", "登陆成功");
		return ret;
	}
	
	//验证码
	@RequestMapping(value ="/get_cpacha",method=RequestMethod.GET)
	public void getCpacha(HttpServletRequest request,
			@RequestParam(value="n",defaultValue="4",required=false) Integer n,//验证码字符个数
			@RequestParam(value="w",defaultValue="98",required=false) Integer w,//字符宽度
			@RequestParam(value="h",defaultValue="33",required=false) Integer h,//字符高度
			HttpServletResponse response) {
		
		//System.out.println("获取验证码");
		CpachaUtil cpachaUtil=new CpachaUtil(4, 98, 33);
		String generatorVCode = cpachaUtil.generatorVCode();
		request.getSession().setAttribute("loginCpacha", generatorVCode);
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	
	
	
	

}
