package com.wzp.pet.mapper;

import com.wzp.pet.po.Post;
import com.wzp.pet.po.User;
import com.wzp.pet.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PostMapperTest extends BaseTest {
    @Autowired
    @Qualifier("PostMapper")
    private PostMapper postMapper;
    @Test
    public void queryPost(){
        List<Post> postList=postMapper.queryPost();
        System.out.println(postList.size());
        System.out.println(postList.get(0).getPostTitle());
    }


    @Test
    public void getPost() {
        List<Post> postList = postMapper.getPost();
        System.out.println(postList.size());

    }
    @Test
    public void findPostByAuthor(){

        Integer userId=1;
        List<Post> postList=postMapper.findPostByAuthor(userId);
        System.out.println(postList.size());
    }

    @Test
    public void findPostById(){
        Integer postId=2;
        Post post=postMapper.findPostById(postId);
        assertNotNull(post);
        System.out.println(post);

    }


    @Test
    public void addpost(){
        Post post=new Post();
        User user=new User();
        user.setUserId(1);
        post.setPostTitle("123");
        post.setPostContent("11122333");
        post.setPostAuthor(user);
        post.setPostTime(new Date());
        int flag=postMapper.addpost(post);
        System.out.println(flag);
    }
    @Test
    public void delpost(){
        int postId=9;
        int flag=postMapper.delPostById(postId);
        System.out.println(flag);
    }
    @Test
    public void postpage(){
        int rowIndex=0;
        int pagesize=4;
        List<Post> postList= postMapper.postlistPage(null,0,4);
        System.out.println(postList.size());
        //assertNotNull(postList);
    }

    @Test
    public void getRecordCount(){
        int cnt=postMapper.getRecordCount("宠物");
        System.out.println(cnt);
    }
    @Test
    public void findUserMsg(){
        int userId=1;
        List<Post> postList=postMapper.findUserMsgByPage(userId,"",0,4);
        System.out.println(postList.get(3));
    }
    @Test
    public void getRecordCountByUserId(){
        int userId=1;
        String queryKey=("宠物");
        int flag=postMapper.getRecordCountByUserId(queryKey,userId);
        System.out.println(flag);
    }
}