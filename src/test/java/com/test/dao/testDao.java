package com.test.dao;

import com.web.book.dao.AppointmentDao;
import com.web.book.dao.BookDao;
import com.web.book.dao.StudentDao;
import com.web.book.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @anthor sily
 * @date 2019/12/15 - 20:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
public class testDao {

    @Resource
    private BookDao bookDao;

    @Resource
    private AppointmentDao appointmentDao;

    @Resource
    private StudentDao studentDao;

    @Test
    public void test01() {
        //List<Book> list = bookDao.queryAll();
        //Book list = bookDao.queryById(1000);
        //List<Book> list = bookDao.querySome("设计");
        //List<Appointment> list = appointmentDao.queryAppointment(321120001);
        Student list = studentDao.queryStudent(321120001,346543);
        System.out.println(list);
    }
}
