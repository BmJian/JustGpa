package com.just.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.just.dao.StudentDao;
import com.just.entity.Condition;
import com.just.entity.Pwd;
import com.just.entity.Result;
import com.just.entity.Student;
import com.just.md5.StringMD5;
import com.just.util.MySessionContext;

@Controller
@CrossOrigin(allowCredentials="true",origins = "*", maxAge = 3600,allowedHeaders="*")
public class AjaxController {

	@Resource(name="StudentDaoImpl")  //指定使用哪个实现类
	private StudentDao studao;
	
	
	@RequestMapping(value="login",method={RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody boolean checkLogin(@RequestParam(value="name",required=true)String name,String pwd,HttpServletRequest req,HttpServletResponse resp) throws UnsupportedEncodingException, NoSuchAlgorithmException{

		char[] mdPwd=StringMD5.getMD5(pwd);
		String realPwd=studao.checkPwd(name);
		if(String.valueOf(mdPwd).equals(realPwd)){
			System.out.println("1");
			HttpSession session=req.getSession(true);
			System.out.println("登陆的SessionID:"+session.getId());
			session.setAttribute("isLogin","yes");
			session.setAttribute("id", name);
			return true;
		}
		System.out.println("0");
		return false;
	}
	
	@RequestMapping(value="changePwd",method={RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody boolean changePwd(@RequestParam("name")String name,String oldPwd,String newPwd) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		char[] mdPwd=StringMD5.getMD5(oldPwd);
		String realPwd=studao.checkPwd(name);
		if(String.valueOf(mdPwd).equals(realPwd)){
			char[] mdNewPwd=StringMD5.getMD5(newPwd);
			Pwd pwd=new Pwd();
			pwd.setId(name);
			pwd.setPwd(String.valueOf(mdNewPwd));
			if(studao.changePwd(pwd)>0){
				System.out.println("用户:"+name+"修改密码成功");
				return true;
			}
			return false;
		}

	   return false;
	}
	
	@RequestMapping("getAll")
	public @ResponseBody List<Student> showAll(){//供json获取所有数据
		return studao.findAll();
	}

	@RequestMapping("getScore")
	public @ResponseBody List<Result> showScore(HttpServletRequest req,String term){//供json获取所有数据

		System.out.println("获取成绩列表的SessionID:"+req.getSession().getId());
		Condition con=new Condition();
		con.setXH((String)req.getSession().getAttribute("id"));
		con.setKKXQ(term);
		System.out.println((String)req.getSession().getAttribute("id"));
		return studao.findById(con);
	}
	
	@RequestMapping("computeGPA")
	public @ResponseBody double getGPA(HttpServletRequest req,String term){//供json获取所有数据
		System.out.println("计算绩点的SessionID:"+req.getSession().getId());
		List<Result> list=showScore(req,term);
		System.out.println(list.size());
		double totalscore=0;
		double totalcredit=0;
		for(Result result:list){
			double score=0;			
			
			if(result.getBCXQ()!=null&&result.getKSXZ().equals("补考一")){
				totalcredit-=result.getXF();
			}
			
			if(result.getZCJ().equals("优")){
				score=95;
			}else if(result.getZCJ().equals("良")){
				score=85;
			}else if(result.getZCJ().equals("中")||result.getZCJ().equals("通过")){
				score=75;
			}else if(result.getZCJ().equals("及格")){
				score=65;
			}else{
				score=new Double(result.getZCJ());
			}

			if(score>=60){
				if(result.getKSXZ().equals("补考一")){
					if(score>75){
						score=75;
					}
				}
				totalscore+=((score/10-5)*result.getXF());
			}
				totalcredit+=result.getXF();

		}
		System.out.println(totalscore);
		System.out.println(totalcredit);
		System.out.println((double) Math.round(totalscore/totalcredit * 100) / 100);
		return (double) Math.round(totalscore/totalcredit * 100) / 100;
	}
}
