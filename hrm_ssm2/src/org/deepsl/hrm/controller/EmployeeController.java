package org.deepsl.hrm.controller;




import java.util.List;

import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.domain.Job;


import org.deepsl.hrm.service.OtherServiceInterface;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


/**   
 * @Description: 处理员工请求控制器   
 * @version V1.0   
 */
@Controller
public class EmployeeController {
	
	@Autowired
	@Qualifier("otherService")
	private OtherServiceInterface otherService;
	

	
	@RequestMapping(value="employee/selectEmployee")
	public String selectEmployee(Integer pageIndex,Model model,Employee employee,Integer dept_id,Integer job_id){
		
		PageModel pageModel=new PageModel();
		if(pageIndex!=null&&pageIndex>=0){
			pageModel.setPageIndex(pageIndex);
		}
		
		
		//把传过来的字段进行模糊处理
		String name=employee.getName();
		if(name!=null&&name.trim().equals("")!=true){
			employee.setName("%"+name+"%");
		}
		
		if(dept_id!=null){
			
			Dept dept=otherService.findDeptById(dept_id);
			
			employee.setDept(dept);
		}
		if(job_id!=null){
			Job job=otherService.findJobById(job_id);
			employee.setJob(job);
		}
		
		List<Employee> employees=null;
		employees=otherService.findEmployee(employee, pageModel);
		
		model.addAttribute("employees", employees);
		model.addAttribute("pageModel",pageModel);
		model.addAttribute("jobs", otherService.findAllJob());
		model.addAttribute("depts", otherService.findAllDept());
		return "employee/employee";
	}
	
	/**
	 * 处理删除用户请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	
	
	@RequestMapping(value="employee/addEmployee")
	public String addEmployee(Integer flag,Model model,Employee employee,Integer dept_id,Integer job_id){
		
		if(flag!=null&&flag==1){
			model.addAttribute("jobs", otherService.findAllJob());
			model.addAttribute("depts", otherService.findAllDept());
			return "employee/showAddEmployee";
		}
		if(flag!=null&&flag==2){
			
			Job job=otherService.findJobById(job_id);
			Dept dept=otherService.findDeptById(dept_id);
			employee.setJob(job);
			employee.setDept(dept);
			
			otherService.addEmployee(employee);
			return "redirect:/employee/selectEmployee";
			
		}
		
		return "employee/showAddEmployee";
		
	}
	
	@RequestMapping(value="employee/updateEmployee")
	public String updateEmployee(Integer flag,Integer id,Model model,Employee employee,Integer dept_id,Integer job_id){
		
		Employee employee2=null;
		if(flag!=null&&flag==1){
			model.addAttribute("jobs", otherService.findAllJob());
			model.addAttribute("depts", otherService.findAllDept());
			
			employee2=otherService.findEmployeeById(id);
			model.addAttribute("employee", employee2);
			return "employee/showUpdateEmployee";
		}
		if(flag!=null&&flag==2){
			
			Job job=otherService.findJobById(job_id);
			Dept dept=otherService.findDeptById(dept_id);
			employee.setJob(job);
			employee.setDept(dept);
			
			otherService.modifyEmployee(employee);
			return "redirect:/employee/selectEmployee";
			
		}
		
		return "employee/showUpdateEmployee";
		
	}
}
