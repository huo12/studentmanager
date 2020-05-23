package huo.chen.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

import huo.chen.entity.User;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import net.sf.json.JSONObject;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		
		String url=request.getRequestURI();
		System.out.println("½øÈëÀ¹½ØÆ÷"+url);
		User user = (User) request.getSession().getAttribute("user");
		if (user==null) {
			//±íÊ¾Î´µÇÂ¼»òÕßµÇÂ¼Ê§Ð§
			System.out.println("Î´µÇÂ¼»òµÇÂ¼Ê§Ð§,url = "+url);
			
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				Map<String,String> ret=new HashMap<String,String>();
				ret.put("type","error");
				ret.put("msg", "µÇÂ¼×´Ì¬ÒÑÊ§Ð§");
				response.getWriter().write(JSONObject.fromObject(ret).toString());;
				return false;
			}
			
			response.sendRedirect(request.getContextPath()+"/system/login");
			return false;		
		}
		return true;
	}

}
