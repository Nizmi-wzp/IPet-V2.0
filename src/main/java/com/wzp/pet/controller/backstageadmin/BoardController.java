package com.wzp.pet.controller.backstageadmin;

import com.wzp.pet.po.Board;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/boardadmin")
public class BoardController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    @Qualifier("BoardServiceImpl")
    private com.wzp.pet.service.BoardService boardService;

    @RequestMapping(value = "/addboard", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> listAdmin(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long adminId=(Long)request.getSession().getAttribute("loginId");
        String content= HttpServletRequestUtil.getString(request,"content");
        Board board=new Board();
        board.setBoardTitle("最新公告");
        board.setBoardContent(content);
        Date date = new Date();
        String sdf=(new SimpleDateFormat("yyyy-MM-dd")).format(date);
        board.setBoardTime(sdf);
        board.setAdminId(adminId);
        if(board!=null){
            int num=boardService.publishBoard(board);
            modelMap.put("success",true);
        }
        else{
            modelMap.put("success",false);
        }

        return modelMap;
    }

    @RequestMapping(value = "/listboardpage", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listboardpage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取前台传过来的页码
        int pageIndex=HttpServletRequestUtil.getInt(request, "page");
        int pageSize = HttpServletRequestUtil.getInt(request, "limit");

        // 空值判断
        if ((pageIndex > -1) && (pageSize > -1) ) {
            // 获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
            // 筛选的条件可以进行排列组合
            String queryKey= HttpServletRequestUtil.getString(request, "queryKey");
            logger.info("输出获取传入的值："+queryKey);
            Board board=new Board();
            if(queryKey!=null && !"".equalsIgnoreCase(queryKey))
                board.setBoardContent(queryKey);
            // 传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            List<Board> boardList = boardService.getBoardList(board, pageIndex, pageSize);
            int recordCount=boardService.queryBoardNum(board);

            modelMap.put("count",recordCount);
            modelMap.put("boardList",boardList);
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex ");
        }
        return modelMap;
    }

    @RequestMapping(value = "/boardnum", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> boardnum(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long adminId=(Long)request.getSession().getAttribute("adminId");
        String content= HttpServletRequestUtil.getString(request,"content");
        Board board=new Board();
        board.setBoardTitle("最新公告");
        board.setBoardContent(content);
        board.setAdminId(adminId);
        if(board!=null){
            int num=boardService.publishBoard(board);
            modelMap.put("success",true);
        }
        else{
            modelMap.put("success",false);
        }

        return modelMap;
    }

    @RequestMapping(value = "/newestboard", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> newestboard(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String newestBoard="";
        newestBoard=boardService.newestBoard();
        modelMap.put("success",true);
        modelMap.put("newestBoard",newestBoard);
        return modelMap;
    }
    @RequestMapping(value = "/delboard", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> delboard(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int boardId=HttpServletRequestUtil.getInt(request,"boardId");
        int num=boardService.delBoard(boardId);
        if(num>0) {
            modelMap.put("success", true);
        }else{
            modelMap.put("success", false);
        }
        return modelMap;
    }

}
