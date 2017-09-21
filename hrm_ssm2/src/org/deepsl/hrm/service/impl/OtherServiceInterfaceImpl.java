package org.deepsl.hrm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deepsl.hrm.dao.DeptDao;
import org.deepsl.hrm.dao.DocumentDao;
import org.deepsl.hrm.dao.EmployeeDao;
import org.deepsl.hrm.dao.JobDao;
import org.deepsl.hrm.dao.NoticeDao;
import org.deepsl.hrm.dao.UserDao;
import org.deepsl.hrm.domain.Dept;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.domain.Notice;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.OtherServiceInterface;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("otherService")
public class OtherServiceInterfaceImpl implements OtherServiceInterface {
	
	@Autowired
	private DeptDao deptDao;
	
	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private DocumentDao documentDao;
	
	/*@Autowired
	private UserDao userDao;*/

	
	@Transactional(readOnly=true)
	@Override
	public List<Employee> findEmployee(Employee employee, PageModel pageModel) {
		// TODO Auto-generated method stub
		List<Employee> employees = null;
		
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("employee", employee);
		params.put("pageModel", pageModel);
		Integer count=employeeDao.count(params);
		
		if(count==0){
			return employees;
		}
		
		pageModel.setRecordCount(count);
		

		employees=employeeDao.selectByPage(params);
		 
		return employees;
	}

	@Override
	public void removeEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		employeeDao.deleteById(id);
	}

	@Override
	public Employee findEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		return employeeDao.selectById(id);
	}

	@Override
	public void addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employee.setCreateDate(new Date());
		employeeDao.save(employee);
	}

	@Override
	public void modifyEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employeeDao.update(employee);
	}

	
	@Transactional(readOnly=true)
	@Override
	public List<Dept> findDept(Dept dept, PageModel pageModel) {
		// TODO Auto-generated method stub
		List<Dept> depts = null;
		
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("dept", dept);
		params.put("pageModel", pageModel);
		Integer count=deptDao.count(params);
		
		if(count==0){
			return depts;
		}
		
		pageModel.setRecordCount(count);
		

		depts=deptDao.selectByPage(params);
		 
		return depts;
	}

	@Override
	public List<Dept> findAllDept() {
		// TODO Auto-generated method stub
		return deptDao.selectAllDept();
	}

	@Override
	public void removeDeptById(Integer id) {
		// TODO Auto-generated method stub
		deptDao.deleteById(id);
	}

	@Override
	public void addDept(Dept dept) {
		// TODO Auto-generated method stub
		deptDao.save(dept);
	}

	@Override
	public Dept findDeptById(Integer id) {
		// TODO Auto-generated method stub
		return deptDao.selectById(id);
	}

	@Override
	public void modifyDept(Dept dept) {
		// TODO Auto-generated method stub
		deptDao.update(dept);
	}

	@Override
	public List<Job> findAllJob() {
		// TODO Auto-generated method stub
		return jobDao.selectAllJob();
	}

	
	@Transactional(readOnly=true)
	@Override
	public List<Job> findJob(Job job, PageModel pageModel) {
		// TODO Auto-generated method stub
		List<Job> jobs = null;
		
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("job", job);
		params.put("pageModel", pageModel);
		Integer count=jobDao.count(params);
		
		if(count==0){
			return jobs;
		}
		
		pageModel.setRecordCount(count);
		//params.put("pageModel", pageModel);

		jobs=jobDao.selectByPage(params);
		 
		return jobs;
	}

	@Override
	public void removeJobById(Integer id) {
		// TODO Auto-generated method stub
		jobDao.deleteById(id);
	}

	@Override
	public void addJob(Job job) {
		// TODO Auto-generated method stub
		jobDao.save(job);
	}

	@Override
	public Job findJobById(Integer id) {
		// TODO Auto-generated method stub
		return jobDao.selectById(id);
	}

	@Override
	public void modifyJob(Job job) {
		// TODO Auto-generated method stub
		jobDao.update(job);
	}

	
	@Transactional(readOnly=true)
	@Override
	public List<Notice> findNotice(Notice notice, PageModel pageModel) {
		// TODO Auto-generated method stub
		
		//notice.setContent("%asdf%");
		
		//pageModel=new PageModel();
		//pageModel.setPageIndex(1);
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("notice", notice);
		params.put("pageModel",pageModel);
		
		Integer count=noticeDao.count(params);
		pageModel.setRecordCount(count);
		
		List<Notice> notices=noticeDao.selectByPage(params);
		return notices;
	}

	@Override
	public Notice findNoticeById(Integer id) {
		// TODO Auto-generated method stub
		return noticeDao.selectById(id);
	}

	@Override
	public void removeNoticeById(Integer id) {
		// TODO Auto-generated method stub
		noticeDao.deleteById(id);
	}

	@Override
	public void addNotice(Notice notice) {
		// TODO Auto-generated method stub
		notice.setCreateDate(new Date());
		noticeDao.save(notice);
	}

	@Override
	public void modifyNotice(Notice notice) {
		// TODO Auto-generated method stub
		noticeDao.update(notice);
	}

	
	@Transactional(readOnly=true)
	@Override
	public List<Document> findDocument(Document document, PageModel pageModel) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("document", document);
		params.put("pageModel",pageModel);
		
		Integer count=documentDao.count(params);
		pageModel.setRecordCount(count);
		
		List<Document> documents=documentDao.selectByPage(params);
		return documents;
	}

	@Override
	public void addDocument(Document document) {
		// TODO Auto-generated method stub
		document.setCreateDate(new Date());
		documentDao.save(document);
	}

	@Override
	public Document findDocumentById(Integer id) {
		// TODO Auto-generated method stub
		return documentDao.selectById(id);
	}

	@Override
	public void removeDocumentById(Integer id) {
		// TODO Auto-generated method stub
		documentDao.deleteById(id);
	}

	@Override
	public void modifyDocument(Document document) {
		// TODO Auto-generated method stub
		documentDao.update(document);
	}

}
