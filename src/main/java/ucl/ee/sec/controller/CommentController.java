package ucl.ee.sec.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ucl.ee.sec.entity.Comment;
import ucl.ee.sec.entity.User;
import ucl.ee.sec.mapper.CommentMapper;

import java.sql.Timestamp;

import java.util.List;

@Controller
@Slf4j
public class CommentController {


    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/comment/comment_list")
    @ResponseBody
    public List<Comment> getCommentList(HttpSession session) {
        //检查是否登录（session是否存在）
        if (session.getAttribute("user") != null) {
            return commentMapper.getTopComment();
        }
        return null;
    }


    @PostMapping("/comment/submit")
    @ResponseBody
    public int submitComment(@RequestParam("content") String submitContent, HttpSession session) {
        //检查是否登录（session是否存在）
        long nowTimestamp = System.currentTimeMillis(); // 获取当前时间的时间戳
        System.out.println(nowTimestamp);
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            Comment comment = new Comment();

            comment.setUserid(user.getUserid());
            comment.setContent(submitContent);
            comment.setPosttime(new Timestamp(System.currentTimeMillis()));

            commentMapper.insertComment(comment);
            return 1;
        }
        return 0;
    }


}
