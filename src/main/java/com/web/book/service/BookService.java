package com.web.book.service;

import com.web.book.dto.AppointExecution;
import com.web.book.entity.Appointment;
import com.web.book.entity.Book;
import com.web.book.entity.Student;

import java.util.List;

/**
 * @anthor sily
 * @date 2019/12/15 - 21:07
 */
public interface BookService {

    /*
    查询一本书
     */
    Book getById(long bookId);

    /*
    查询所有的图书
     */
    List<Book> getList();

    /*
    登录时查询数据库是否有该学生的记录
     */
    Student validateStu(long studentId,long password);

    /*
    按图书名称查询
     */
    List<Book> getSomeList(String name);

    /*
    查询某学生的预约图书
     */
    List<Appointment> getAppointByStu(long studentId);

    /*
    预约图书
     */
    AppointExecution appoint(long bookId,long studentId);//返回预约成功后的实体类
}
