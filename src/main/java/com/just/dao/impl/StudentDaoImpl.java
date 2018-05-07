package com.just.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.just.dao.StudentDao;
import com.just.entity.Condition;
import com.just.entity.Pwd;
import com.just.entity.Result;
import com.just.entity.Student;
import com.just.util.SqlSessionUtil;

@Repository("StudentDaoImpl")
public class StudentDaoImpl implements StudentDao{

	public List<Student> findAll() {
		SqlSession session=SqlSessionUtil.getSession();
		List<Student> list=session.selectList("findAll");
		session.close();
		return list;
	}

	public List<Result> findById(Condition con) {
		SqlSession session=SqlSessionUtil.getSession();
		List<Result> list=session.selectList("findById",con);
		session.close();
		return list;
	}

	public String checkPwd(String name) {
		SqlSession session=SqlSessionUtil.getSession();
		String pwd=session.selectOne("checkPwd",name);		
		session.close();
		return pwd;
	}

	public int changePwd(Pwd pwd) {
		SqlSession session=SqlSessionUtil.getSession();
		int num=session.update("changePwd",pwd);
		session.commit();
		session.close();
		return num;
	}




}
