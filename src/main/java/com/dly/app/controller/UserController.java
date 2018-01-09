package com.dly.app.controller;




import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dly.app.commons.baes.Result;
import com.dly.app.commons.baes.SuperClass;
import com.dly.app.commons.fastdfs.FastdfsClient;
import com.dly.app.commons.redis.Cacheable;
import com.dly.app.commons.redis.RedisLogService;
import com.dly.app.commons.util.ImageUtil;
import com.dly.app.pojo.Collect;
import com.dly.app.pojo.Comment;
import com.dly.app.pojo.User;
import com.dly.app.service.SmsService;



@RequestMapping(value="personal")
@RestController
public class UserController extends SuperClass {
	private static Logger log = Logger.getLogger(UserController.class);
	@Resource
	public  SmsService smsService;
	@Resource
	private FastdfsClient fast;

	@GetMapping(value="verificationcode")
	public void   VerificationCode(HttpServletResponse response,String phone) throws IOException {
		//利用图片工具生成图片  
	    //第一个参数是生成的验证码，第二个参数是生成的图片  
	    Object[] objs = ImageUtil.createImage();  
	    //将验证码存入Session  
	    //session.setAttribute("imageCode",objs[0]);  
	    System.out.println(objs[0]);
	    String val=String.valueOf(objs[0]);
	    //将图片输出给浏览器  
	    BufferedImage image = (BufferedImage) objs[1];  
	    response.setContentType("image/png");  
	    OutputStream os = response.getOutputStream();  
	    ImageIO.write(image, "png", os);  
	    redisUtil.cacheValue(phone, val.toString(), 300);
	   // return new Result("true","0","验证码成功","");
	//util.VerificationCode(response,key);
		
	}
	
//	@RequestMapping(value="test2")
//	public ModelAndView  test1(ModelAndView mv) {
//		
//		
//		mv.setViewName("test"); 
//		return mv; 
//	}
	
	/**
	 * 登录
	 * @param see
	 * @param user
	 * @return
	 */
	@PostMapping(value="login",produces = "application/json;charset=UTF-8")
	public String  login(@RequestBody User user) {
		log.info("请求参数:"+user);
	 Result re=	userService.login(user);
		String json=JSONObject.toJSONString(re);
		log.info("登录返回参数===:"+json);
		
		return json;
	}
	/**
	 * 注册
	 * @param user
	 * @return 
	 */
	@PostMapping(value="register",produces = "application/json;charset=UTF-8")
	public Object  register(@RequestBody User user) {
		log.info("注册请求参数===="+user.toString());
		 Result re=	userService.register(user);
	       String json=JSONObject.toJSONString(re);
	       log.info("注册返回参数===:"+json);
			//req.
			return json;
	}
	/**
	 * 退出登录
	 * @param jo
	 * @return
	 */
	@PostMapping(value="logout",produces = "application/json;charset=UTF-8")
	public Object  logout(@RequestBody JSONObject jo) {
		log.info("退出登录参数===="+jo);
		 Result re=	userService.logout(jo.getString("tokenId"));
	       String json=JSONObject.toJSONString(re);
	       log.info("退出登录返回参数===:"+json);
			//req.
			return json;
	}
	@GetMapping(value="test")
	public Object  register1(){
	System.out.println("22");
	//	redisUtil.cacheValue("你", "我", 20);
		
			return "";
	}
	/**
	 * 修改用户信息
	 * @return
	 */
	@PostMapping(value="changeUserInfo",produces = "application/json;charset=UTF-8")
	public Object  changeUserInfo(@RequestBody User user){
		log.info("修改用户信息参数===="+user);
		 Result result=userService.changeUserInfo(user);
		 String json=JSONObject.toJSONString(result);
	       log.info("修改用户信息返回===:"+json);
			return json;
	}
	/**
	 * 忘记密码
	 * @return
	 */
	@PostMapping(value="resetPwd",produces = "application/json;charset=UTF-8")
	public Object  resetPassword(@RequestBody User user){
		log.info("忘记密码参数===="+user);
		 Result result=userService.resetPassword(user);
		 String json=JSONObject.toJSONString(result);
	       log.info("修改用户信息返回===:"+json);
			return json;
	}
	
	@GetMapping(value="{userId}",produces = "application/json;charset=UTF-8")
	public Object  getUserInfo(@PathVariable String userId){
		log.info("获取用户信息参数===="+userId);
		User user=new User();
		user.setUserId(userId);
		return userService.getUserInfo(user);
	}

	 @PostMapping(value = "fileUpload",produces = "application/json;charset=UTF-8")
	 public String  fileUpload(@RequestParam("file") CommonsMultipartFile file,String tokenId,String type) throws IOException {
	        long  startTime=System.currentTimeMillis();
	        System.out.println("fileName："+file.getOriginalFilename());
	        log.info("上传文件参数===="+tokenId);
			 Result result=fastdfsService.upLoadUserIcon(tokenId,file,type);
			 String json=JSONObject.toJSONString(result);
		       log.info("上传文件返回===:"+json);
		       long  endTime=System.currentTimeMillis();
		       log.info("上传文件耗时===:"+(endTime-startTime));
				return json;
	    }

	 
	 
	 //用户评论
	 @PostMapping(value = "comment",produces = "application/json;charset=UTF-8")
	 public Object  InsetComment(@RequestBody Comment in) {
		 log.info("用户评论参数+++:"+in);
		 return userService.insertComment(in);	
	    }
	 //获取评论
	 @GetMapping(value = "comment/{groupId}",produces = "application/json;charset=UTF-8")
	 public Object  getComment(@PathVariable String groupId) {
		 log.info("获取评论参数+++:"+groupId);
		 Comment comment =new Comment();
		 comment.setGroupId(groupId);
		 return userService.getComment(comment);
	    }
	 
		//用户收藏文章
		@PostMapping(value="collect",produces = "application/json;charset=UTF-8")
		public  Object userAddCollect(@RequestBody Collect  collect) {
			System.out.println(collect);
			return userService.userAddCollect(collect);
		}
		//用户取消收藏
		@DeleteMapping(value="collect",produces = "application/json;charset=UTF-8")
		public  Object deleteCollect(@RequestBody Collect  collect) {
			System.out.println("取消收藏参数------->"+JSONObject.toJSONString(collect));
			return userService.userDeleteCollect(collect);
		}
		//获取用户收藏的所有文章
		@GetMapping(value="collect/{userId}",produces = "application/json;charset=UTF-8")
		public  Object getUserCollect(@PathVariable  Integer  userId) {
			log.info("获取用户收藏的所有文章----------->>>>>>");
			Collect collect=new Collect();
			collect.setUserId(userId);
			System.out.println(collect);
			log.info("获取用户收藏的所有文章");
			return userService.getUserCollect(collect);
			
		}
		@GetMapping("ttt")
		public Object update() {
			
			return userService.sss();
		}
	
	
}
