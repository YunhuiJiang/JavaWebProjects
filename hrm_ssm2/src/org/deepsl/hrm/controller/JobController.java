package org.deepsl.hrm.controller;

import java.util.List;

import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.domain.Job;
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
 * @Description: 处理职位请求控制器  
 * @version V1.0   
 */

@Controller
public class JobController {

	@Autowired
	@Qualifier("otherService")
	private OtherServiceInterface otherService;
	

	
	@RequestMapping(value="job/selectJob")
	public String selectJob(Integer pageIndex,Model model,Job job){
		
		PageModel pageModel=new PageModel();
		if(pageIndex!=null&&pageIndex>=0){
			pageModel.setPageIndex(pageIndex);
		}
		
		
		//把传过来的字段进行模糊处理
		String name=job.getName();
		if(name!=null&&name.trim().equals("")!=true){
			job.setName("%"+name+"%");
		}
		
		
		List<Job> jobs=null;
		jobs=otherService.findJob(job, pageModel);
		
		model.addAttribute("jobs", jobs);
		model.addAttribute("pageModel",pageModel);
		return "job/job";
	}
	
	/**
	 * 处理删除用户请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	
	
	@RequestMapping(value="job/addJob")
	public String addJob(Integer flag,Model model,Job job){
		
		if(flag!=null&&flag==1){
			return "job/showAddJob";
		}
		if(flag!=null&&flag==2){
			
			otherService.addJob(job);
			return "redirect:/job/selectJob";
			
		}
		
		return "job/showAddJob";
		
	}
	
	@RequestMapping(value="job/updateJob")
	public String updateJob(Integer flag,Integer id,Model model,Job job){
		
		Job job2=null;
		if(flag!=null&&flag==1){
			job2=otherService.findJobById(id);
			model.addAttribute("job", job2);
			return "job/showUpdateJob";
		}
		if(flag!=null&&flag==2){
			
			otherService.modifyJob(job);
			return "redirect:/job/selectJob";
			
		}
		
		return "job/showUpdateJob";
		
	}
 
}
