package org.deepsl.hrm.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deepsl.hrm.dao.EmployeeDao;
import org.deepsl.hrm.dao.JobDao;
import org.deepsl.hrm.dao.NoticeDao;
import org.deepsl.hrm.domain.Employee;
import org.deepsl.hrm.domain.Job;
import org.deepsl.hrm.domain.Notice;
import org.deepsl.hrm.service.OtherServiceInterface;
import org.deepsl.hrm.util.tag.PageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebRoot/WEB-INF/applicationContext.xml")
public class EmployeeTest {

	@Autowired
	OtherServiceInterface otherServiceInterface;

	@Autowired
	EmployeeDao employeeDao;
	
	@Test
	public void testSelectId(){
		Employee employee=null;
		
		employee=employeeDao.selectById(1);
		
		System.out.println("EmployeeTest.testSelectId()"+employee);
	}
	
	@Test
	public void testSelectByPage(){
		Employee employee=new Employee();
		
		Job job=new Job();
		job.setId(6);
		employee.setJob(job);
		
		//employee.setName("%爱丽丝%");
		
		PageModel pageModel=new PageModel();
		//pageModel.setPageIndex(1);
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("employee", employee);
		params.put("pageModel",pageModel);
		
		Integer count=employeeDao.count(params);
		System.out.println("EmployeeTest.testSelectByPage()"+count);
		pageModel.setRecordCount(count);
		
		List<Employee> employees=employeeDao.selectByPage(params);
		
		System.out.println("EmployeeTest.testSelectByPage()"+employees);
		
		/*Integer count=noticeDao.count(params);
		System.out.println("NoticeTest.testSelectByPage()"+count);*/
	}

	@Test
	public void testParams() {
		/*List<Job> jobs = null;

		Job job=new Job();
		job.setName("%经理%");

		PageModel pageModel = new PageModel();
		//pageModel.setPageIndex(1);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("job", job);
		Integer count = jobDao.count(params);

		System.out.println("UserTest.testParams() count"+count);
		pageModel.setRecordCount(count);
		params.put("pageModel", pageModel);

		jobs = jobDao.selectByPage(params);

		System.out.println("UserTest.testParams() jobs" + jobs);*/

	}
	
	@Test
	public void testupdate() {
		
		/*User user=new User();
		user.setId(6);
		user.setLoginname("fff");
		user.setUsername("eee");
		user.setPassword("eee");
		user.setStatus(2);
		//user.setCreateDate(new Date());
		jobDao.update(user);*/

	}

}