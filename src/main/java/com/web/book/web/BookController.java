package com.web.book.web;

import com.web.book.dto.AppointExecution;
import com.web.book.dto.Result;
import com.web.book.entity.Appointment;
import com.web.book.entity.Book;
import com.web.book.entity.Student;
import com.web.book.enums.AppointStateEnum;
import com.web.book.exception.NoNumberException;
import com.web.book.exception.RepeatAppointException;
import com.web.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @anthor sily
 * @date 2019/12/15 - 22:35
 */
@Controller
@RequestMapping("/books")
public class BookController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private BookService bookService;

    //获取图书列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private String List(Model model) {
        List<Book> list = bookService.getList();
        model.addAttribute("list", list);
        return "list";
    }

    //搜寻是否有某图书
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    private void search(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        //接收页面的值
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        name = name.trim();
        //向页面传值
        request.setAttribute("name", name);
        request.setAttribute("list", bookService.getSomeList(name));
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }

    //查看某图书的详细情况
    //@PathVariable是请求占位符的意思
    @RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
    private String detail(@PathVariable("bookId") Long bookId, Model model) {
        Book book = bookService.getById(bookId);
        if (book == null) {
            return "forward:/books/list";
        }
        model.addAttribute("book", book);
        return "detail";
    }

    //验证输入的用户名、密码是否正确
    @RequestMapping(value="/verify", method = RequestMethod.POST, produces = {
            "application/json; charset=utf-8" })
    @ResponseBody
    private Map validate(Long studentId, Long password) {   //(HttpServletRequest req,HttpServletResponse resp){
        Map resultMap = new HashMap();
        Student student = null;
        student = bookService.validateStu(studentId, password);
        if (student != null) {
            System.out.println("SUCCESS");
            resultMap.put("result", "SUCCESS");
            return resultMap;
        } else {
            resultMap.put("result", "FAILED");
            return resultMap;
        }
    }
    //执行预约的逻辑
    @RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {
            "application/json; charset=utf-8" })
    @ResponseBody
    private Result<AppointExecution> execute(@PathVariable("bookId") Long bookId,@RequestParam("studentId") Long studentId){
        Result<AppointExecution> result;
        AppointExecution execution=null;

        try{//手动try catch,在调用appoint方法时可能报错
            execution=bookService.appoint(bookId, studentId);
            result=new Result<AppointExecution>(true,execution);
            return result;

        } catch(NoNumberException e1) {
            execution=new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
            result=new Result<AppointExecution>(true,execution);
            return result;
        }catch(RepeatAppointException e2){
            execution=new AppointExecution(bookId,AppointStateEnum.REPEAT_APPOINT);
            result=new Result<AppointExecution>(true,execution);
            return result;
        }catch (Exception e){
            execution=new AppointExecution(bookId,AppointStateEnum.INNER_ERROR);
            result=new Result<AppointExecution>(true,execution);
            return result;
        }
    }
    //查看已预约的书籍
    @RequestMapping(value ="/appoint")
    private String appointBooks(@RequestParam("studentId") long studentId, Model model){

        List<Appointment> appointList=new ArrayList<Appointment>();
        appointList=bookService.getAppointByStu(studentId);
        model.addAttribute("appointList", appointList);
        return "appointBookList";
    }
}
