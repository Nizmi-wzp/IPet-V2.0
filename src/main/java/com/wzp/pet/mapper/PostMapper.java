package com.wzp.pet.mapper;

import com.wzp.pet.po.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("PostMapper")
public interface PostMapper {
    /*1》输出帖子列表*/
    List<Post> queryPost();
    /*2》根据条件查询帖子信息*/
    List<Post> queryPostList(@Param("PostCondition") Post PostCondition);
    /*3》插入单条记录*/
    int insertPost(Post Post);
    /* 4》批量插入*/
    int batchInsertPost(List<Post> PostList);
    /*5》根据帖子Id删除单条记录*/
    int deletePost(long PostId);
    /*6》根据Id列表批量删除*/
    int batchDeletePost(List<Long> PostIdList);
    /*7》根据帖子ID号修改*/
    int updatePost(Post Post);


    public List<Post> getPost();
    public Post findPostById(@Param("postId")int postId);
    public List<Post> findPostByAuthor(@Param("userId")int userId);
    public int addpost(@Param("post")Post post);
    public int delPostById(@Param("postId")int postId);
    public List<Post> postlistPage(@Param("queryKey") String queryKey,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
    public int getRecordCount(@Param("queryKey") String queryKey);
    public List<Post> findUserMsgByPage(@Param("userId")int userId,@Param("queryKey") String queryKey,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
    public int getRecordCountByUserId(@Param("queryKey") String queryKey,@Param("userId")int userId);
}
