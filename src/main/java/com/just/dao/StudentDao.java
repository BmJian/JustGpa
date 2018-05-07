package com.just.dao;

import java.util.List;

import com.just.entity.Condition;
import com.just.entity.Pwd;
import com.just.entity.Result;
import com.just.entity.Student;

public interface StudentDao {

	public List<Student> findAll();
	public List<Result> findById(Condition con);
	public String checkPwd(String name);
	public int changePwd(Pwd pwd);
}
