package org.deepsl.hrm.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deepsl.hrm.dao.UserDao;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.tag.PageModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebRoot/WEB-INF/applicationContext.xml")
public class UserTest {

	@Autowired
	HrmService userService;

	@Autowired
	UserDao userDao;

	@Test
	public void testParams() {
		List<User> users = null;

		User user = new User();
		user.setUsername("%vvv%");

		PageModel pageModel = new PageModel();
		//pageModel.setPageIndex(1);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		Integer count = userDao.count(params);

		System.out.println("UserTest.testParams() count"+count);
		pageModel.setRecordCount(count);
		params.put("pageModel", pageModel);

		users = userDao.selectByPage(params);

		System.out.println("UserTest.testParams() users" + users);

	}
	
	@Test
	public void testupdate() {
		
		User user=new User();
		user.setId(6);
		user.setLoginname("fff");
		user.setUsername("eee");
		user.setPassword("eee");
		user.setStatus(2);
		//user.setCreateDate(new Date());
		userDao.update(user);

	}

}