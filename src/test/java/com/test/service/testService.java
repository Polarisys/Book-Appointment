package com.test.service;

import com.web.book.dto.AppointExecution;
import com.web.book.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @anthor sily
 * @date 2019/12/15 - 22:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/*.xml")
public class testService {

    @Resource
    private BookService bookService;

    @Test
    public void test01(){
        //Book list = bookService.getById(1000);
        //List<Book> list = bookService.getList();
        //List<Book> list = bookService.getSomeList("设计");
        //Student list = bookService.validateStu(321120001,346543);
       AppointExecution list =  bookService.appoint(321120001,346543);
        System.out.println(list);
    }
}
