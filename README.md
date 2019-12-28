# Book-Appointment
使用SSM+MAVEN搭建的图书预约管理项目
整个项目的编写流程（具体见详细代码）
Dao:
（1）首先编写entity层中代码，编写Appointment Book Student 实体类
（2）编写Dao层中的代码
（3）编写spring-dao.xml，jdbc.properties,mybatis-config.xml
（4）编写Dao接口的实现类 BookDao.xml AppointmentDao.xml StudentDao.xml

Service:
（1）首先编写service接口类 BookService 加上预约成功后的实体类AppointExecution
（2）预约图书时可能出现异常，所以要编写异常类AppoinException，NoNumberException，RepeatAppoint
（3）编写接口的实现类 BookServiceImpl
（4）配置spring-service.xml

Web:
（1）配置spring-web.xml  然后修改web.xml中的内容，再可以添加日志文件logback.xml
（2）编写controller层中的代码
（3）把执行预约逻辑返回的信息封装起来，编写Result
（4）为了给web显示预约后的反馈信息，我们建立一个常量数据字典类存放这些要反馈给客户的信息
AppointStateEnum
后端编写完毕

最后编写前端的代码，使用bootstrap轻量一站式框架开发前端，具体见详细代码。

登录时会选择管理员或者是学生
![image](https://github.com/sily-baby/Book-Appointment/blob/master/src/main/webapp/resources/image/2019-12-27_220219.png)

选择学生登录成功时会显示预约按钮和查看已预约书籍
![image](https://github.com/sily-baby/Book-Appointment/blob/master/src/main/webapp/resources/image/2019-12-27_221531.png)

选择管理员登录成功时会显示添加图书的按钮
![image](https://github.com/sily-baby/Book-Appointment/blob/master/src/main/webapp/resources/image/2019-12-27_221738.png)

点击添加图书后会进入添加图书的界面
![image](https://github.com/sily-baby/Book-Appointment/blob/master/src/main/webapp/resources/image/2019-12-27_221828.png)
