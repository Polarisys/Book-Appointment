<%--
  Created by IntelliJ IDEA.
  User: MY  GODCUP
  Date: 2019/12/6
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>图书列表</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>图书列表</h2>
        </div>
        <form name="firstForm" action="<%=request.getContextPath()%>/books/search" method="post">
            <div class="panel-heading ">
                <table class="table table-bookName">
                    <thead>
                    <tr>
                        <th width="90" align="lift">图书名称：</th>
                        <th width="150" align="lift">
                            <input type="text" name="name" class="allInput" value="${name}" placeholder="输入检索书名^o^"/>
                        </th>
                        <th>
                            <input type="submit" value="检索" id="tabSub"/>
                        </th>
                    </tr>
                    </thead>
                </table>
            </div>
        </form>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>图书ID</th>
                    <th>图书名称</th>
                    <th>馆藏数量</th>
                    <th>详细</th>
                </tr>
                </thead>
                <tbody id="bookList">
                <c:forEach items="${list}" var="sk">
                    <tr>
                        <td>${sk.bookId}</td>
                        <td>${sk.name}</td>
                        <td>${sk.number}</td>
                        <td><a class="btn btn-info" href="/book/books/${sk.bookId}/detail " target="_blank">详细</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <nav aria-label="Page navigation">
            <ul class="pager pagination-lg">
                <li>
                    <a href="#" aria-label="Previous" id="previous"><span aria-hidden="true"><<</span></a>
                </li>
                <li><a href="#" id="first-column" class="columnFlag"></a></li>
                <li><a href="#" id="second-column" class="columnFlag"></a></li>
                <li><a href="#" id="third-column" class="columnFlag"></a></li>
                <li><a href="#" id="forth-column" class="columnFlag"></a></li>
                <li><a href="#" id="fifth-column" class="columnFlag"></a></li>
                <li>
                    <a href="#" aria-label="Next" id="next"><span aria-hidden="true">>></span></a>
                </li>
                <li><span>输入每页的条数</span><input type="text" id="recordNumber" value="5"></li>
            </ul>
        </nav>
    </div>
</div>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/bookAppointment.js" type="text/javascript"></script>
</body>
<script type="text/javascript">
    $(function () {
        bookAppointment.list.init();
    })
</script>
</html>
