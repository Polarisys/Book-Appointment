package com.web.book.dao;

import com.web.book.entity.Book;

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
    查询所有的书籍
     */
    List<Book> queryAll();

    /*
    减少库存的数量
    用返回值判断当前的库存是否还有书籍
     */
    int reduceNumber(long bookId);
}
