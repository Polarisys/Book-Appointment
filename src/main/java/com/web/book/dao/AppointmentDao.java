package com.web.book.dao;

import com.web.book.entity.Appointment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @anthor sily
 * @date 2019/12/15 - 15:05
 */
public interface AppointmentDao {

    /*
    根据图书ID和学生ID预约书籍，并插入
     */
    int insertAppointment(@Param("bookId") long bookId, @Param("studentId") long studentId);

    /*
    根据一个学生ID查询预约了哪些书
     */
    List<Appointment> queryAppointment(long studentId);
}
