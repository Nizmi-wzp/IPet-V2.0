package com.wzp.pet.service.impl;

import com.wzp.pet.mapper.BoardMapper;
import com.wzp.pet.po.Board;
import com.wzp.pet.service.BoardService;
import com.wzp.pet.util.PageCalculator;
import com.wzp.pet.util.ReadImagesFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BoardServiceImpl")
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;
    @Override
    public int publishBoard(Board board) {
        int num=boardMapper.publishBoard(board);
        return num;
    }

    @Override
    public List<Board> getBoardList(Board board, int pageIndex, int pageSize) {
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Board> boardList=boardMapper.boardList(board,rowIndex,pageSize);
        return boardList;
    }

    @Override
    public int queryBoardNum(Board board) {
        int num=boardMapper.queryBoardcount(board);
        return num;
    }
    @Override
    public String newestBoard() {
        String newestBoard=boardMapper.newestBoard();
        return newestBoard;
    }

    @Override
    public int delBoard(int boardId) {
        return boardMapper.delBoard(boardId);
    }
}
