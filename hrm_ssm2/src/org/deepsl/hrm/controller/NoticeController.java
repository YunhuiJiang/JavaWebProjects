package org.deepsl.hrm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.domain.Notice;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.service.OtherServiceInterface;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理公告请求控制器
 * @version V1.0   
 */

@Controller
public class NoticeController {
	
	@Autowired
	@Qualifier("otherService")
	private OtherServiceInterface otherService;
	

	
	@RequestMapping(value="notice/selectNotice")
	public String selectNotice(Integer pageIndex,Model model,Notice notice){
		
		PageModel pageModel=new PageModel();
		if(pageIndex!=null&&pageIndex>=0){
			pageModel.setPageIndex(pageIndex);
		}
		
		
		//把传过来的字段进行模糊处理
		String name=notice.getTitle();
		if(name!=null&&name.trim().equals("")!=true){
			notice.setTitle("%"+name+"%");
		}
		
		
		List<Notice> notices=null;
		notices=otherService.findNotice(notice, pageModel);
		
		model.addAttribute("notices", notices);
		model.addAttribute("pageModel",pageModel);
		return "notice/notice";
	}
	
	/**
	 * 处理删除用户请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	
	
	@RequestMapping(value="notice/addNotice")
	public String addNotice(Integer flag,Model model,Notice notice){
		
		if(flag!=null&&flag==1){
			return "notice/showAddNotice";
		}
		if(flag!=null&&flag==2){
			
			otherService.addNotice(notice);
			return "redirect:/notice/selectNotice";
			
		}
		
		return "notice/showAddNotice";
		
	}
	
	@RequestMapping(value="notice/updateNotice")
	public String updateNotice(Integer flag,Integer id,Model model,Notice notice){
		
		Notice notice2=null;
		if(flag!=null&&flag==1){
			notice2=otherService.findNoticeById(id);
			model.addAttribute("notice", notice2);
			return "notice/showUpdateNotice";
		}
		if(flag!=null&&flag==2){
			
			otherService.modifyNotice(notice);
			return "redirect:/notice/selectNotice";
			
		}
		
		return "notice/showUpdateNotice";
		
	}

	
	 
	
}
