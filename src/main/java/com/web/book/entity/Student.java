package com.web.book.entity;

/**
 * @anthor sily
 * @date 2019/12/15 - 14:55
 */
public class Student {

    private long studentId;
    private long password;

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", password=" + password +
                '}';
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }
}
