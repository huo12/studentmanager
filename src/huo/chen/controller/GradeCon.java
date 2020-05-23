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
	
	//获取年级列表
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
		
		//添加年级信息
		@RequestMapping(value="/add",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> add(Grade grade){
			Map<String,String> ret=new HashMap<String,String>();
			if (StringUtils.isEmpty(grade.getName())) {
				ret.put("type", "error");
				ret.put("msg", "年级名称不能为空！");
				return ret;
			}
			if (gradeservice.add(grade)<=0) {
				ret.put("type", "error");
				ret.put("msg", "年级添加失败！");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "年级添加成功!");
			return ret;
		}
	
	
		//修改年级信息
		@RequestMapping(value="/edit",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> edit(Grade grade){
			Map<String,String> ret=new HashMap<String,String>();
			if (StringUtils.isEmpty(grade.getName())) {
				ret.put("type", "error");
				ret.put("msg", "年级名称不能为空！");
				return ret;
			}
			if (gradeservice.edit(grade)<=0) {
				ret.put("type", "error");
				ret.put("msg", "年级修改失败！");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "年级修改成功!");
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
				ret.put("msg", "请选择要删除的数据！");
				return ret;
			}
			try {
				if (gradeservice.delete(StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
					ret.put("type", "error");
					ret.put("msg", "删除失败！");
					return ret;
				}
			} catch (Exception e) {
				// TODO: handle exception
				ret.put("type", "error");
				ret.put("msg", "该年级下存在班级信息，请勿冲动！");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "删除成功!");
			return ret;
		}
}
