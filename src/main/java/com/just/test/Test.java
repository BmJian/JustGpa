package com.just.test;

import com.just.dao.StudentDao;
import com.just.dao.impl.StudentDaoImpl;
import com.just.entity.Pwd;

public class Test {

	public static void main(String[] args) {
//		//测试获取全部数据
//		StudentDao studao=new StudentDaoImpl();
//		List<Student> list=studao.findAll();
//		for(Student stu:list){
//			System.out.println(stu.getXH()+stu.getXM()+stu.getXF()+stu.getKKXQ());
//		}
		
//		StudentDao studao=new StudentDaoImpl();
//		Condition con=new Condition();
//		con.setXH("158111545133");
//		con.setKKXQ("2016-2017-2");
//		List<Result> list=studao.findById(con);
//		for(Result result:list){
//			System.out.println(result.getXH()+" "+result.getXM()+" "+result.getKKXQ()+" "+result.getKCH()+" "+result.getKCM()+" "+result.getZCJ()+" "+result.getXF());
//		}
		
		StudentDao studao=new StudentDaoImpl();
		Pwd pwd=new Pwd();
		pwd.setId("test");
		pwd.setPwd("2222");
		System.out.println(studao.changePwd(pwd));
	}

}
