package com.wzp.pet.mapper;

import com.wzp.pet.po.Board;
import com.wzp.pet.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BoardMapperTest extends BaseTest {
    @Autowired
    private BoardMapper boardMapper;
    @Test
    public void boardList(){
        Board board=new Board();
        board.setBoardContent("年");
        List<Board> boardList=boardMapper.boardList(board,0,3);
        System.out.println(boardList.size());
        System.out.println(boardList.get(0).getBoardTitle());
    }
    @Test
    public void queryBoardcount(){
        Board board=new Board();
        board.setBoardContent("年");
        int num=boardMapper.queryBoardcount(board);
        System.out.println(num);
    }
    @Test
    public void publishBoard(){
        Board board=new Board();
        board.setBoardTitle("最新公告");
        board.setBoardContent("本年度最佳员工开始评比了，希望大家努力争取！");
        Date date=new Date();
        board.setBoardTime(""+date);
        board.setAdminId(10010L);
        int num=boardMapper.publishBoard(board);
        assertEquals(1,num);
    }
    @Test
    public void newestBoard(){
        System.out.println(boardMapper.newestBoard());
    }
    @Test
    public void delBoard(){
        System.out.println(boardMapper.delBoard(11));
    }

}