package com.wzp.pet.controller.postadmin;


import com.wzp.pet.po.Post;
import com.wzp.pet.po.User;
import com.wzp.pet.service.PostService;
import com.wzp.pet.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/postadmin")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    @Qualifier("PostServiceImpl")
    private PostService postService;
    @RequestMapping(value = "/addpost", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> addpost(HttpServletRequest request){

        User user=(User) request.getSession().getAttribute("user");
        //System.out.println(user);
        //User user=new User();
        //user.setUserId(45);
        String postTitle= HttpServletRequestUtil.getString(request,"postTitle");
        String postContent= HttpServletRequestUtil.getString(request,"postContent");
        Post post=new Post();
        post.setPostTitle(postTitle);
        post.setPostContent(postContent);
        //user.setUserId(user.getUserId());
        post.setPostTime(new Date());
        post.setPostAuthor(user);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int flag;
        try {
            flag = postService.issuePost(post);
            if (flag == 1) {
                modelMap.put("success", true);
                modelMap.put("errMsg", "发布成功！");
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "发布失败！");
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", "发布失败！");
        }
        return modelMap;
    }
    @RequestMapping(value = "/getpost", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getpost(HttpServletRequest request){

        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex= HttpServletRequestUtil.getInt(request,"pageIndex");
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        if (pageIndex<=0) pageIndex=1;
        int rowIndex=(pageIndex-1)*4; //从多少条记录开始
        List<Post> postList = null;
        try{

            postList=postService.postpage(queryKey,rowIndex,4);
            modelMap.put("postList", postList);
            modelMap.put("total", postList.size());
            modelMap.put("success", true);
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }
    @RequestMapping(value = "/getrecordCount",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object>  getPageCount(HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        int recordCount=postService.getRecordCount(queryKey);
        map.put("success",true);
        map.put("recordCount",recordCount);
        return map;
    }


    @RequestMapping(value = "/findUserMsg",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object>  findUserMsg(HttpServletRequest request){
        HashMap<String,Object> modelMap = new HashMap<String,Object>();
        User user= (User)request.getSession().getAttribute("user");
        int userId=user.getUserId();
        int pageIndex= HttpServletRequestUtil.getInt(request,"pageIndex");
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");

        if (pageIndex<=0) pageIndex=1;
        int rowIndex=(pageIndex-1)*4;
        List<Post> postList=postService.findUserMsg(userId,queryKey,rowIndex,4);
        try {
            modelMap.put("postList", postList);
            modelMap.put("total", postList.size());
            modelMap.put("success", true);

        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }
    @RequestMapping(value = "/getrecordCountByUserId",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object>  getPageCountByUserId(HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        String queryKey= HttpServletRequestUtil.getString(request,"queryKey");
        User user=(User) request.getSession().getAttribute("user");
        int userId=user.getUserId();
        int recordCount=postService.getRecordCountByUserId(queryKey,userId);
        map.put("success",true);
        map.put("recordCount",recordCount);
        return map;
    }
    @RequestMapping(value = "/deletepost",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object>  deletepost(HttpServletRequest request){
        HashMap<String,Object>modelMap = new HashMap<String,Object>();
        int postId= HttpServletRequestUtil.getInt(request,"postId");
        int flag;
        try {
            flag = postService.delpost(postId);
            if (flag == 1) {
                modelMap.put("success", true);
                modelMap.put("errMsg", "删除成功！");
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "删除失败！");
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", "删除失败！");
        }
        return modelMap;
    }
}
