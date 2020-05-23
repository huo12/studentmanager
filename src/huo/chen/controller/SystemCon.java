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

//ϵͳ��ҳ������


@RequestMapping("/system")
@Controller
public class SystemCon {
	@Autowired
	private UserService userservice;
	
	
	
	@RequestMapping(value ="/hello",method=RequestMethod.GET)
	public ModelAndView hello( ModelAndView model) {
		model.setViewName("hello");
		model.addObject("user", "��");
		return model;
	}
	

	@RequestMapping(value ="/index",method=RequestMethod.GET)
	public ModelAndView index( ModelAndView model) {
		model.setViewName("system/index");
		model.addObject("user", "��");
		return model;
	}
	
	
	
	//��¼ҳ��
	@RequestMapping(value ="/login",method=RequestMethod.GET)
	public ModelAndView login( ModelAndView model) {
		model.setViewName("system/login");
		model.addObject("user", "��");
		return model;
	}
	
	//��¼���ύ
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
			ret.put("msg", "�û�������Ϊ��");
			return ret;
		}
		if (StringUtils.isEmpty(password)) {
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ�գ�");
			return ret;
		}
		if (StringUtils.isEmpty(vcode)) {
			ret.put("type", "error");
			ret.put("msg", "��֤�벻��Ϊ�գ�");
			return ret;
		}
		String loginCpacha=(String) request.getSession().getAttribute("loginCpacha");
		System.out.println(loginCpacha);
		if (StringUtils.isEmpty(loginCpacha)) {
			ret.put("type", "error");
			ret.put("msg", "��ʱ��δ�������Ự��ʧЧ����ˢ�º����ԣ�");
			return ret;
		}
		if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
			ret.put("type", "error");
			ret.put("msg", "��֤�����");
			return ret;
		}
		request.getSession().setAttribute("loginCpacha", null);
		//�����ݿ���ȥ�����û�
		if (type==1) {//����Ա
			User findUserName = userservice.findUserName(username);
			if (findUserName==null) {
				ret.put("type", "error");
				ret.put("msg", "�û������������");
				return ret;
			}
			if (!password.equals(findUserName.getPassword())) {
				ret.put("type", "error");
				ret.put("msg", "�û������������");
				return ret;
			}
			request.getSession().setAttribute("user", findUserName);
			
		}
		if (type==2) {//ѧ��
			
			
			
		}
		
		ret.put("type", "success");
		ret.put("msg", "��½�ɹ�");
		return ret;
	}
	
	//��֤��
	@RequestMapping(value ="/get_cpacha",method=RequestMethod.GET)
	public void getCpacha(HttpServletRequest request,
			@RequestParam(value="n",defaultValue="4",required=false) Integer n,//��֤���ַ�����
			@RequestParam(value="w",defaultValue="98",required=false) Integer w,//�ַ����
			@RequestParam(value="h",defaultValue="33",required=false) Integer h,//�ַ��߶�
			HttpServletResponse response) {
		
		//System.out.println("��ȡ��֤��");
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
