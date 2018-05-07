package com.just.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionUtil {

	 public static SqlSession getSession(){
		 SqlSessionFactoryBuilder factoryBuilder=new SqlSessionFactoryBuilder();
		 InputStream config=SqlSessionUtil.class.getClassLoader().getResourceAsStream("sqlMapConfig.xml");
		 SqlSessionFactory sf=factoryBuilder.build(config);
		 SqlSession session=sf.openSession();
		 return session;
	 }
	 
	 public static void main(String[] args){
		 System.out.println(getSession());
	 }
}
