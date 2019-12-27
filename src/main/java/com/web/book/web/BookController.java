package com.web.book.web;

import com.web.book.dto.AppointExecution;
import com.web.book.dto.Result;
import com.web.book.entity.Admin;
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
        List<Book> list = bookService.getList(1,5);
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

    //实现分页
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    private List<Book> pageLis(Model model,HttpServletRequest request,HttpServletResponse response){
        String secondColumn = request.getParameter("columnNumber");
        String record = request.getParameter("recordNumber");
        List<Book> list = null;
        if(record.equals("")){
            record = "5" ;
        }
       if(secondColumn!=null){
           Integer pageNumber = Integer.parseInt(secondColumn);
           Integer recordNumber = Integer.parseInt(record);
           list = bookService.getList(pageNumber,recordNumber);
       }else {
           list = bookService.getList(1,5);
       }
       return  list;
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
    @RequestMapping(value="/verifyStudent", method = RequestMethod.POST, produces = {
            "application/json; charset=utf-8" })
    @ResponseBody
    private Map validateStudent(HttpServletRequest request, HttpServletResponse response) {
        Map studentMap = new HashMap();
        Student student = null;
        String studentIds = request.getParameter("userId");
        long studentId = Long.parseLong(studentIds);
        String passwords = request.getParameter("password");
        long password  = Long.parseLong(passwords);
        student = bookService.validateStu(studentId, password);
        if (student != null) {
            System.out.println("SUCCESS");
            studentMap.put("result", "SUCCESS");
            return studentMap;
        } else {
            studentMap.put("result", "FAILED");
            return studentMap;
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

    //跳转到添加图书页面
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    private String add(Model model){
        return "admin";
    }

    //添加图书
    @RequestMapping(value = "/bookData")
    @ResponseBody
    private String bookData(HttpServletRequest request,HttpServletResponse response){
        String bookId = request.getParameter("bookId");
        String bookName = request.getParameter("bookName");
        String bookIntrod = request.getParameter("bookIntrod");
        String bookNumber = request.getParameter("bookNumber");
        Long id = Long.parseLong(bookId);
        Integer number = Integer.parseInt(bookNumber);
        Book book = bookService.getById(id);
        if(book == null){
            bookService.addBook(id,bookName,bookIntrod,number);
            return "success";
        }else {
            //数据库存在相同的书籍ID
            return "failed";
        }
    }

    //管理员验证登录
    @RequestMapping(value = "/verifyAdmin",method = RequestMethod.POST)
    @ResponseBody
    private Map validateAdmin(HttpServletRequest request,HttpServletResponse response){
        Map adminMap = new HashMap();
        Admin admin = null;
        String adminId = request.getParameter("userId");
        String password = request.getParameter("password");
        admin = bookService.validateAdmin(adminId,password);
        if(admin != null){
            adminMap.put("result","SUCCESS");
            return adminMap;
        }else{
            adminMap.put("result", "FAILED");
            return adminMap;
        }
    }
}
