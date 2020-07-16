package com.wzp.pet.service.impl;

import com.wzp.pet.mapper.PostMapper;
import com.wzp.pet.po.Post;
import com.wzp.pet.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PostServiceImpl")
public class PostServiceImpl implements PostService {
    @Autowired
    @Qualifier("PostMapper")
    private PostMapper postMapper;

    @Override
    public List<Post> getPost(){

        return postMapper.getPost();
    }
    @Override
    public int issuePost(Post post){
        return postMapper.addpost(post);
    }
    @Override
    public int delpost(int postId){
        return postMapper.delPostById(postId);
    }
    @Override
    public List<Post> postpage(String queryKey,int rowIndex,int  pageSize){

        return postMapper.postlistPage(queryKey,rowIndex,pageSize);


    }

    @Override
    public int getRecordCount(String queryKey){
        return postMapper.getRecordCount(queryKey);
    }
    @Override
    public List<Post> findUserMsg(int userId,String queryKey,int rowIndex,int  pageSize){
        return postMapper.findUserMsgByPage(userId,queryKey,rowIndex,pageSize);
    }
    @Override
    public int getRecordCountByUserId(String queryKey,int userId){

        return postMapper.getRecordCountByUserId(queryKey,userId);
    }

}
