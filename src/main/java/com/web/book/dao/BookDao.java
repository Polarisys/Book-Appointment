package com.web.book.dao;

import com.web.book.entity.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @anthor sily
 * @date 2019/12/15 - 14:57
 */
public interface BookDao {

    /*
    根据id查询书
     */
    Book queryById(long bookId);

    /*
    根据书名查询书
     */
    List<Book> querySome(String bookName);

    /*
    根据每页的记录和页数筛选数据
     */
    List<Book> queryAll(@Param("startNumber") int startNumber,@Param("recordNumber") int recordNumber);

    /*
    减少库存的数量
    用返回值判断当前的库存是否还有书籍
     */
    int reduceNumber(long bookId);
}
