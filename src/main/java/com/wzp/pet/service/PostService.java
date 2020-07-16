package com.wzp.pet.service;

import com.wzp.pet.po.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    public List<Post> getPost();
    public int issuePost(Post post);
    public int delpost(int postId);
    public List<Post> postpage(String queryKey,int rowIndex,int  pageSize);
    public int getRecordCount(String queryKey);
    public List<Post> findUserMsg(int userId,String queryKey,int rowIndex,int  pageSize);
    public int getRecordCountByUserId(String queryKey,int userId);

}
