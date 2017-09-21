package org.deepsl.hrm.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.service.OtherServiceInterface;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 处理上传下载文件请求控制器
 * @version V1.0
 */

@Controller
public class DocumentController {

	@Autowired
	@Qualifier("otherService")
	private OtherServiceInterface otherService;
	
	@Autowired
	@Qualifier("hrmService")
	private HrmService hrmService;

	@RequestMapping(value = "document/selectDocument")
	public String selectDoc(Integer pageIndex, Model model, Document document) {
		PageModel pageModel = new PageModel();
		if (pageIndex != null && pageIndex >= 0) {
			pageModel.setPageIndex(pageIndex);
		}
		String title = document.getTitle();
		if (title != null && title.trim().equals("") != true) {
			document.setTitle("%" + title + "%");
		}

		List<Document> documents = null;
		documents = otherService.findDocument(document, pageModel);

		model.addAttribute("documents", documents);
		model.addAttribute("pageModel", pageModel);
		return "document/document";
	}

	

	@RequestMapping(value = "document/addDocument")
	public String addDoc(Integer flag, Model model, Document document, //@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request,HttpSession session) throws IllegalStateException, IOException {

		if (flag != null && flag == 1) {
			return "document/showAddDocument";
		}
		if (flag != null && flag == 2) {

			CommonsMultipartFile file=(CommonsMultipartFile) document.getFile();
			document.setFileName(file.getOriginalFilename());
			String realPath = request.getRealPath("/upload");
			File file1 = new File(realPath, file.getOriginalFilename());

			file.transferTo(file1);
			
			User user=(User) session.getAttribute(HrmConstants.USER_SESSION);
			System.out.println("DocumentController.addDoc()"+user);
			
			document.setUser(user);

			otherService.addDocument(document);
			return "redirect:/document/selectDocument";

		}

		return "document/showAddDocument";
	}
	
	@RequestMapping("document/downLoad")
	public ResponseEntity<byte[]> downloadFile(Integer id,HttpServletRequest request, HttpServletResponse response) throws IOException{
		Document document=otherService.findDocumentById(id);
		
		String fileName=document.getFileName();
		
		String realPath = request.getRealPath("/upload");
		File file = new File(realPath, fileName);
		
		
		HttpHeaders headers = new HttpHeaders();  
        //下载显示的文件名，解决中文名称乱码问题  
        String downloadFielName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", downloadFielName); 
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                headers, HttpStatus.CREATED); 
		
	}
	
	@RequestMapping(value="document/updateDocument")
	public String updateUser(Integer flag,Integer id,Model model,Document document,HttpServletRequest request) throws IllegalStateException, IOException{
		
		Document document2=null;
		if(flag!=null&&flag==1){
			document2=otherService.findDocumentById(id);
			model.addAttribute("document", document2);
			return "document/showUpdateDocument";
		}
		if(flag!=null&&flag==2){
			
			CommonsMultipartFile file=(CommonsMultipartFile) document.getFile();
			document.setFileName(file.getOriginalFilename());
			String realPath = request.getRealPath("/upload");
			File file1 = new File(realPath, file.getOriginalFilename());

			file.transferTo(file1);
			otherService.modifyDocument(document);
			return "redirect:/document/selectDocument";
			
		}
		
		return "document/showUpdateDocument";
		
	}

}
