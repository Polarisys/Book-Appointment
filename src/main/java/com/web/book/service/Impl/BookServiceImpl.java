package com.web.book.service.Impl;

import com.web.book.dao.AppointmentDao;
import com.web.book.dao.BookDao;
import com.web.book.dao.StudentDao;
import com.web.book.dto.AppointExecution;
import com.web.book.entity.Appointment;
import com.web.book.entity.Book;
import com.web.book.entity.Student;
import com.web.book.enums.AppointStateEnum;
import com.web.book.exception.AppointException;
import com.web.book.exception.NoNumberException;
import com.web.book.exception.RepeatAppointException;
import com.web.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @anthor sily
 * @date 2019/12/15 - 21:07
 */
@Service
public class BookServiceImpl implements BookService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource()
    private BookDao bookDao;

    @Resource
    private AppointmentDao appointmentDao;

    @Resource
    private StudentDao studentDao;

    @Override
    public Book getById(long bookId) {
        return bookDao.queryById(bookId);
    }

    @Override
    public List<Book> getList() {
        return bookDao.queryAll();
    }

    @Override
    public Student validateStu(long studentId, long password) {
        return studentDao.queryStudent(studentId,password);
    }

    @Override
    public List<Book> getSomeList(String name) {
        return bookDao.querySome(name);
    }

    @Override
    public List<Appointment> getAppointByStu(long studentId) {
        return appointmentDao.queryAppointment(studentId);
    }

    @Override
    @Transactional
    public AppointExecution appoint(long bookId, long studentId) {//在Dao的基础上组织逻辑，形成与web成交互用的方法
        try{													  //返回成功预约的类型。
            int update=bookDao.reduceNumber(bookId);//减库存
            if(update<=0){//已经无库存！
                throw new NoNumberException("no number");
            }else{
                //执行预约操作
                int insert=appointmentDao.insertAppointment(bookId, studentId);
                if(insert<=0){//重复预约
                    throw new RepeatAppointException("repeat appoint");
                }else{//预约成功
                    return new AppointExecution(bookId, AppointStateEnum.SUCCESS);
                }
            }
        } catch (NoNumberException e1) {
            throw e1;
        } catch (RepeatAppointException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译期异常转换为运行期异常
            throw new AppointException("appoint inner error:" + e.getMessage());
        }
    }
}
