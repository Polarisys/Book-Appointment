package com.web.book.dao;

import com.web.book.entity.Admin;
import org.apache.ibatis.annotations.Param;

/**
 * @anthor sily
 * @date 2019/12/24 - 20:14
 */
public interface AdminDao {

    Admin queryAdmin(@Param("adminId") String adminId,@Param("adminPassword") String adminPassword);

}
