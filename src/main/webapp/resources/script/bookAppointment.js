var bookAppointment = {
        //封装Ajax的相关的url
        URL: {
            appoint: function (bookId, studentId) {
                return '/book/books/' + bookId + '/appoint?studentId=' + studentId;
            },
            verifyStudent: function () {
                return '/book/books' + '/verifyStudent';
            },
            verifyAdmin: function () {
                return '/book/books' + '/verifyAdmin';
            },
            addBook: function () {
                return '/book/books' + '/admin';
            },
            IP: function () {
                return 'http://localhost:8080';
            },
            addBookData: function () {
                return '/book/books' + '/bookData';
            }
        },

        //验证学号和密码
        validateUser: function (userId, password, userType) {
            if (!userId || !password) {
                return "nothing";
            } else {
                if (bookAppointment.verifyWithDatabase(userId, password, userType)) {
                    return "success";
                } else {
                    return "mismatch";
                }
            }
        },
        //将学号和用户名与数据库相匹配
        verifyWithDatabase: function (userId, password, userType) {
            var result = false;
            var params = {};
            var verifyUrl = '';
            params.userId = userId;
            params.password = password;
            if (userType == "student") {
                verifyUrl = bookAppointment.URL.verifyStudent();
            } else if (userType == "admin") {
                verifyUrl = bookAppointment.URL.verifyAdmin();
            }
            $.ajax({
                type: "post",
                url: verifyUrl,
                data: params,
                dataType: "json",
                async: false, //同步调用，保证先执行result=true,后再执行return result;
                success: function (data) {
                    if (data.result == 'SUCCESS') {
                        window.location.reload();
                        //弹出登录成功！
                        alert("登陆成功！");
                        result = true;
                    } else {
                        result = false;
                    }
                }
            });
            return result;
        },
        list: {
            toAddPage: function () {
                var addUrl = bookAppointment.URL.addBook();
                window.location.href = bookAppointment.URL.IP() + addUrl;
            },
            init: function () {
                // 当前用户为管理员，添加图书按钮才可见
                var userType = $.cookie('userType');
                if (userType == 'student' || $.cookie('userId') == null) {
                    $('#addBook').hide();
                }
                //清除当前页面的缓存
                $('#clearCache').click(function () {
                    $.cookie('userId', '', {path: '/books'});
                    $.cookie('password', '', {path: '/books'});
                });
                var firstNumber = 1, secondNumber = 2, thirdNumber = 3, forthNumber = 4, fifthNumber = 5, recordNum = 5;
                bookAppointment.list.pageCompomentInit(firstNumber, secondNumber, thirdNumber, forthNumber, fifthNumber, recordNum);
                $("#next").click(function () {
                    $('#first-column').text(firstNumber + 5);
                    $('#second-column').text(secondNumber + 5);
                    $('#third-column').text(thirdNumber + 5);
                    $('#forth-column').text(forthNumber + 5);
                    $('#fifth-column').text(fifthNumber + 5);
                    firstNumber += 5;
                    secondNumber += 5;
                    thirdNumber += 5;
                    forthNumber += 5;
                    fifthNumber += 5;
                    $('#previous').show();
                });
                $("#previous").click(function () {
                    $('#first-column').text(firstNumber - 5);
                    $('#second-column').text(secondNumber - 5);
                    $('#third-column').text(thirdNumber - 5);
                    $('#forth-column').text(forthNumber - 5);
                    $('#fifth-column').text(fifthNumber - 5);
                    firstNumber -= 5;
                    secondNumber -= 5;
                    thirdNumber -= 5;
                    forthNumber -= 5;
                    fifthNumber -= 5;
                    if (fifthNumber <= 5) {
                        $("#previous").hide();
                    }
                });
                flag = 0;
                firstColumn = '#first-column';
                secondColumn = '#second-column';
                thirdColumn = '#third-column';
                forthColumn = '#forth-column';
                fifthColumn = '#fifth-column';
                $(firstColumn).click(function () {
                    bookAppointment.list.columnListData(firstColumn);
                });
                $(secondColumn).click(function () {
                    bookAppointment.list.columnListData(secondColumn);
                });
                $(thirdColumn).click(function () {
                    bookAppointment.list.columnListData(thirdColumn);
                });
                $(forthColumn).click(function () {
                    bookAppointment.list.columnListData(forthColumn);
                });
                $(fifthColumn).click(function () {
                    bookAppointment.list.columnListData(fifthColumn);
                });

            },
            columnListData: function (column) {
                var columnNumber = $(column).text();
                var recordNumber = $("#recordNumber").val();
                var params = {};
                if (column == firstColumn) {
                    flag = 1;
                } else if (column == secondColumn) {
                    flag = 2;
                } else if (column == thirdColumn) {
                    flag = 3;
                } else if (column == forthColumn) {
                    flag = 4;
                } else if (column == fifthColumn) {
                    flag = 5;
                }
                params['columnNumber'] = columnNumber;
                params['recordNumber'] = recordNumber;
                $("#bookList").empty();
                $.ajax({
                    type: "get",
                    url: '/book/books/page',
                    data: params,
                    async: false,
                    success: function (result) {
                        for (var i = 0; i < result.length; i++) {
                            $("#bookList").append(
                                '<tr>' +
                                '<td> ' + result[i].bookId + '</td>' +
                                '<td> ' + result[i].name + ' </td>' +
                                '<td> ' + result[i].number + '</td>' +
                                '<td><a class="btn btn-info" href="/book/books/' + result[i].bookId + '/detail" >详细</a></td>' +
                                '</tr>'
                            )
                        }
                        $('#bookList').html();
                    }
                });
            },
            pageCompomentInit: function (firstNumber, secondNumber, thirdNumber, forthNumber, fifthNumber, recordNum) {
                $('#first-column').text(firstNumber);
                $('#second-column').text(secondNumber);
                $('#third-column').text(thirdNumber);
                $('#forth-column').text(forthNumber);
                $('#fifth-column').text(fifthNumber);
                $('#recordNumber').text(recordNum);
                $('#previous').hide();
            }
        },
        //添加图书页面
        addBookPage: {
            init: function () {
                $('#addBtn').click(function () {
                    bookAppointment.addBookPage.addBook();
                });
            },
            validateBook: function (bookId, bookName, bookIntrod, bookNumber) {
                if (!bookId || !bookName || !bookIntrod || !bookNumber) {
                    return "1";
                } else if (bookId.length != 4) {
                    return "2";
                } else if (bookNumber > 100) {
                    return "3";
                } else {
                    return "4";
                }
            },
            addBook: function () {
                //对用户添加行为响应
                var addResultModel = $('#addResultModel');
                var bookId = $('#bookIdKey').val();
                var bookName = $('#bookNameKey').val();
                var bookIntrod = $('#bookIntrodKey').val();
                var bookNumber = $('#bookNumberKey').val();
                var validate = bookAppointment.addBookPage.validateBook(bookId, bookName, bookIntrod, bookNumber);
                if (validate == "1") {
                    $('#addMessage').hide().html('<label class="label label-danger">不能有空值!</label>').show(300);
                } else if (validate == "2") {
                    $('#addMessage').hide().html('<label class="label label-danger">书籍的ID必须为4位数!</label>').show(300);
                } else if (validate == "3") {
                    $('#addMessage').hide().html('<label class="label label-danger">书籍的数量不能大于100!</label>').show(300);
                } else if (validate == "4") {
                    var params = {};
                    var bookDataUrl = bookAppointment.URL.addBookData();
                    params['bookId'] = bookId;
                    params['bookName'] = bookName;
                    params['bookIntrod'] = bookIntrod;
                    params['bookNumber'] = bookNumber;
                    $.ajax({
                        type: "get",
                        url: bookDataUrl,
                        data: params,
                        async: false,
                        success: function (result) {
                            if (result == "success") {
                                $('#resultMessage').text("书籍添加成功！");
                                addResultModel.modal({
                                    show: true,//显示弹出层
                                    backdrop: 'static',//禁止位置关闭
                                    keyboard: false//关闭键盘事件
                                });
                            } else if (result == "failed") {
                                $('#resultMessage').text("书籍添加失败！存在相同的书籍ID");
                                addResultModel.modal({
                                    show: true,//显示弹出层
                                    backdrop: 'static',//禁止位置关闭
                                    keyboard: false//关闭键盘事件
                                });
                            }
                        }
                    });
                }
            }
        },
        //预约图书逻辑
        detail:
            {
                //预定初始化
                init: function (params) {
                    $('#bookAdd').hide();
                    $('#checkAppointment').hide();
                    $('#addBook').click(function () {
                        bookAppointment.list.toAddPage();
                    });
                    var bookId = params["bookId"];
                    var userId = $.cookie('userId');
                    var password = $.cookie('password');
                    var userType = '';
                    if (!userId || !password) {
                        //设置弹出层属性
                        var IdAndPasswordModal = $('#chooseModel');
                        var userModel = $('#userModel');
                        IdAndPasswordModal.modal({
                            show: true,//显示弹出层
                            backdrop: 'static',//禁止位置关闭
                            keyboard: false//关闭键盘事件
                        });
                        $('#studentLogin').click(function () {
                            userModel.modal({
                                show: true,//显示弹出层
                                backdrop: 'static',//禁止位置关闭
                                keyboard: false//关闭键盘事件
                            });
                            $('#userText').html("<h3>学生登录</h3>");
                            userType = 'student';
                        });
                        $('#adminLogin').click(function () {
                            userModel.modal({
                                show: true,//显示弹出层
                                backdrop: 'static',//禁止位置关闭
                                keyboard: false//关闭键盘事件
                            });
                            $('#userText').html("<h3>管理员登录</h3>");
                            userType = 'admin';
                        });
                        $('#userBtn').click(function () {
                            if (userType == 'student') {
                                bookAppointment.detail.userLogin(userId, password, userType);
                            } else if (userType == 'admin') {
                                bookAppointment.detail.userLogin(userId, password, userType);
                            }
                        });
                    } else {
                        var appointBox = $('#appoint-box');
                        bookAppointment.detail.appointment(bookId, userId, appointBox);
                    }
                }
                ,
                userLogin: function (userId, password, userType) {
                    userId = $('#userIdKey').val();
                    password = $('#passwordKey').val();
                    //调用validateStudent函数验证用户id和密码。
                    var temp = bookAppointment.validateUser(userId, password, userType);
                    if (temp == "nothing") {
                        $('#userMessage').html('<label class="label label-danger">帐号或密码为空!</label>').show();
                        setTimeout(function () {
                            $('#userMessage').hide();
                        }, 1000);
                    } else if (temp == "mismatch") {
                        $('#userMessage').html('<label class="label label-danger">帐号密码不匹配!</label>').show();
                        setTimeout(function () {
                            $('#userMessage').hide();
                        }, 1000);
                    } else if (temp == "success") {
                        //学号与密码匹配正确，将学号密码保存在cookie中。不设置cookie过期时间，这样即为session模式，关闭浏览器就不保存密码了。
                        $.cookie('userId', userId, {path: '/'});
                        $.cookie('password', password, {path: '/'});
                        $.cookie('userType', userType, {path: '/'});
                    }
                },
                appointment: function (bookId, studentId, node) {
                    if ($.cookie('userType') == 'student') {
                        node.html('<button class="btn btn-primary btn-lg" id="appointmentBtn">预约</button>');
                        $('#checkAppointment').show();
                    }else {
                        $('#bookAdd').show();
                    }
                    var appointmentUrl = bookAppointment.URL.appoint(bookId, studentId);
                    //绑定一次点击事件
                    $('#appointmentBtn').one('click', function () {
                        //执行预约请求
                        //1丶先禁用请求
                        $(this).addClass("disabled");
                        //2丶发送预约请求执行预约
                        $.post(appointmentUrl, {}, function (result) {
                            //Ajax强大之处，向Controller方法提出请求和返回结果在一处!
                            //同时还可以连续取对象的子对象
                            if (result && result['success']) {
                                var appointResult = result['data'];
                                var state = appointResult['state'];
                                var stateInfo = appointResult['stateInfo'];
                                //显示预约结果,把结果显示给页面，完成了jsp的工作
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }       //因为公用一个node所以，用来显示“stateInfo”时就不会显示上面的“预约”
                        });
                    });
                }
            }
    }
;