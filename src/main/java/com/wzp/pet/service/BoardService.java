package com.wzp.pet.service;

import com.wzp.pet.po.Board;

import java.util.List;

public interface BoardService {
    int publishBoard(Board board);

    List<Board> getBoardList(Board board, int pageIndex, int pageSize);
    int queryBoardNum(Board board);
    String newestBoard();
    public int delBoard(int boardId);
}
