package com.web.book.entity;

/**
 * @anthor sily
 * @date 2019/12/15 - 14:47
 */
public class Book {
    private long bookId;//图书ID
    private String name;//图书名称
    private int number;//馆藏数量
    private String introd;//图书简介

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", introd='" + introd + '\'' +
                '}';
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getIntrod() {
        return introd;
    }

    public void setIntrod(String introd) {
        this.introd = introd;
    }
}
