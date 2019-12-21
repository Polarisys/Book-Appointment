package com.web.book.dao;

import com.web.book.entity.Student;
import org.apache.ibatis.annotations.Param;

/**
 * @anthor sily
 * @date 2019/12/15 - 15:08
 */
public interface StudentDao {

    /*
    向数据库中验证输入的密码是否正确
     */
    Student queryStudent(@Param("studentId") long studentId, @Param("password") long password);
}
