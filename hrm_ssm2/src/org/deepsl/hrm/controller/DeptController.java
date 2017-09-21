package org.deepsl.hrm.controller;

import java.util.List;

import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.service.OtherServiceInterface;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理部门请求控制器
 * @author   
 * @version V1.0   
 */

@Controller
public class DeptController {

	@Autowired
	@Qualifier("otherService")
	private OtherServiceInterface otherService;
	

	
	@RequestMapping(value="dept/selectDept")
	public String selectDept(Integer pageIndex,Model model,Dept dept){
		
		PageModel pageModel=new PageModel();
		if(pageIndex!=null&&pageIndex>=0){
			pageModel.setPageIndex(pageIndex);
		}
		
		
		//把传过来的字段进行模糊处理
		String name=dept.getName();
		if(name!=null&&name.trim().equals("")!=true){
			dept.setName("%"+name+"%");
		}
		
		
		List<Dept> depts=null;
		depts=otherService.findDept(dept, pageModel);
		
		model.addAttribute("depts", depts);
		model.addAttribute("pageModel",pageModel);
		return "dept/dept";
	}
	
	/**
	 * 处理删除用户请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	
	
	@RequestMapping(value="dept/addDept")
	public String addDept(Integer flag,Model model,Dept dept){
		
		if(flag!=null&&flag==1){
			return "dept/showAddDept";
		}
		if(flag!=null&&flag==2){
			
			otherService.addDept(dept);
			return "redirect:/dept/selectDept";
			
		}
		
		return "dept/showAddDept";
		
	}
	
	@RequestMapping(value="dept/updateDept")
	public String updateDept(Integer flag,Integer id,Model model,Dept dept){
		
		Dept dept2=null;
		if(flag!=null&&flag==1){
			dept2=otherService.findDeptById(id);
			model.addAttribute("dept", dept2);
			return "dept/showUpdateDept";
		}
		if(flag!=null&&flag==2){
			
			otherService.modifyDept(dept);
			return "redirect:/dept/selectDept";
			
		}
		
		return "dept/showUpdateDept";
		
	}

 
}
