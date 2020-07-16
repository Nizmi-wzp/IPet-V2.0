package com.wzp.pet.mapper;

import com.wzp.pet.po.Board;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BoardMapper")
public interface BoardMapper {
    //发布公告，插入
    public List<Board> boardList(@Param("boardCondition") Board boardCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
    int queryBoardcount(@Param("boardCondition") Board boardCondition);
    public int publishBoard(Board board);
    //查询最新一条公告
    public String newestBoard();
    public int delBoard(int boardId);
}
