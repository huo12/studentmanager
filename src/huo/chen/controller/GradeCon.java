package huo.chen.controller;

import java.util.Arrays;
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

import huo.chen.entity.Grade;
import huo.chen.page.Page;
import huo.chen.service.GradeService;
import huo.chen.util.StringUtil;

@RequestMapping("/grade")
@Controller
public class GradeCon {

	@Autowired
	private GradeService gradeservice;
	//
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("grade/grade_list");
		return model;
	}
	
	//��ȡ�꼶�б�
		@RequestMapping(value="/get_list",method=RequestMethod.POST)
		@ResponseBody
		public Map<String , Object> getList(
				@RequestParam (value ="name",required=false,defaultValue="")String name,
				Page page

				) {
			Map<String,Object> ret=new HashMap<String,Object>();
			Map<String,Object> queryMap=new HashMap<String,Object>();
			queryMap.put("name", "%"+name+"%");
			queryMap.put("offset", page.getOffset());
			queryMap.put("pageSize", page.getRows());
			ret.put("rows", gradeservice.findList(queryMap));
			ret.put("total", gradeservice.getTotal(queryMap));
			return ret;
		}
		
		//����꼶��Ϣ
		@RequestMapping(value="/add",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> add(Grade grade){
			Map<String,String> ret=new HashMap<String,String>();
			if (StringUtils.isEmpty(grade.getName())) {
				ret.put("type", "error");
				ret.put("msg", "�꼶���Ʋ���Ϊ�գ�");
				return ret;
			}
			if (gradeservice.add(grade)<=0) {
				ret.put("type", "error");
				ret.put("msg", "�꼶���ʧ�ܣ�");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "�꼶��ӳɹ�!");
			return ret;
		}
	
	
		//�޸��꼶��Ϣ
		@RequestMapping(value="/edit",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> edit(Grade grade){
			Map<String,String> ret=new HashMap<String,String>();
			if (StringUtils.isEmpty(grade.getName())) {
				ret.put("type", "error");
				ret.put("msg", "�꼶���Ʋ���Ϊ�գ�");
				return ret;
			}
			if (gradeservice.edit(grade)<=0) {
				ret.put("type", "error");
				ret.put("msg", "�꼶�޸�ʧ�ܣ�");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "�꼶�޸ĳɹ�!");
			return ret;
		}	
		
		//
		@RequestMapping(value="/delete",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> delete(
				@RequestParam(value="ids[]",required=true) Long[] ids
				){
			Map<String,String> ret=new HashMap<String,String>();
			if (ids==null || ids.length==0) {
				ret.put("type", "error");
				ret.put("msg", "��ѡ��Ҫɾ�������ݣ�");
				return ret;
			}
			try {
				if (gradeservice.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
					ret.put("type", "error");
					ret.put("msg", "ɾ��ʧ�ܣ�");
					return ret;
				}
			} catch (Exception e) {
				// TODO: handle exception
				ret.put("type", "error");
				ret.put("msg", "���꼶�´��ڰ༶��Ϣ������嶯��");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "ɾ���ɹ�!");
			return ret;
		}
}
