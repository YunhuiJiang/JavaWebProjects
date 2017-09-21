package org.deepsl.hrm.controller;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * 处理用户请求控制器
 * */
@Controller
public class UserController {
	
	/**
	 * 自动注入UserService
	 * */
	@Autowired
	@Qualifier("hrmService")
	private HrmService hrmService;
		
	/**
	 * 处理登录请求
	 * @param String loginname  登录名
	 * @param String password 密码
	 * @return 跳转的视图
	 * */
	@RequestMapping(value="/login")
	 public ModelAndView login(@RequestParam("loginname") String loginname,
			 @RequestParam("password") String password,
			 HttpSession session,
			 ModelAndView mv){
		// 调用业务逻辑组件判断用户是否可以登录
		User user = hrmService.login(loginname, password);
		if(user != null){
			// 将用户保存到HttpSession当中
			session.setAttribute(HrmConstants.USER_SESSION, user);
			// 客户端跳转到main页面
			mv.setViewName("redirect:/main");
		}else{
			// 设置登录失败提示信息
			mv.addObject("message", "登录名或密码错误!请重新输入");
			// 服务器内部跳转到登录页面
			mv.setViewName("forward:/loginForm");
		}
		return mv;
		
	}
	
	@RequestMapping(value="/logout.action")
	 public ModelAndView logout( HttpSession session,ModelAndView mv){

		session.invalidate();

		mv.setViewName("redirect:/");
		
		return mv;
		
	}
	
	/**
	 * 处理查询请求
	 * @param pageIndex 请求的是第几页
	 * @param employee 模糊查询参数
	 * @param Model model
	 * */
	
	
	@RequestMapping(value="user/selectUser")
	public String selectUser(Integer pageIndex,Model model,User user){
		
		PageModel userPageModel=new PageModel();
		if(pageIndex!=null&&pageIndex>=0){
			userPageModel.setPageIndex(pageIndex);
		}
		String username=user.getUsername();
		if(username!=null&&username.trim().equals("")!=true){
			user.setUsername("%"+username+"%");
		}
		
		
		List<User> users=null;
		users=hrmService.findUser(user, userPageModel);
		
		model.addAttribute("users", users);
		model.addAttribute("pageModel",userPageModel);
		return "user/user";
	}
	
	/**
	 * 处理删除用户请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	
	
	@RequestMapping(value="user/addUser")
	public String addUser(Integer flag,Model model,User user){
		
		if(flag!=null&&flag==1){
			return "user/showAddUser";
		}
		if(flag!=null&&flag==2){
			
			hrmService.addUser(user);
			return "redirect:/user/selectUser";
			
		}
		
		return "user/showAddUser";
		
	}
	
	@RequestMapping(value="user/updateUser")
	public String updateUser(Integer flag,Integer id,Model model,User user){
		
		User user2=null;
		if(flag!=null&&flag==1){
			user2=hrmService.findUserById(id);
			model.addAttribute("user", user2);
			return "user/showUpdateUser";
		}
		if(flag!=null&&flag==2){
			
			hrmService.modifyUser(user);
			return "redirect:/user/selectUser";
			
		}
		
		return "user/showUpdateUser";
		
	}
	
	
 
	 
	
}
