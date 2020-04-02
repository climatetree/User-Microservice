package com.climatetree.user;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.climatetree.user.config.JwtTokenUtilTest;
import com.climatetree.user.controller.UserControllerTest;
import com.climatetree.user.dao.UserDaoTest;
import com.climatetree.user.service.UserServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ JwtTokenUtilTest.class, UserControllerTest.class, UserDaoTest.class,
	UserServiceTest.class })
public class UsersTestSuite {

	@BeforeClass
	public static void setUpClass() {
		System.out.println("Master setup");

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Master tearDown");
	}
}
