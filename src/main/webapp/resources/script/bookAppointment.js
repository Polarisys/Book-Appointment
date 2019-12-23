var bookAppointment = {
        //封装Ajax的相关的url
        URL: {
            appoint: function (bookId, studentId) {
                return '/book/books/' + bookId + '/appoint?studentId=' + studentId;
            },
            verify: function () {
                return '/book/books' + '/verify';
            }
        },

        //验证学号和密码
        validateStudent: function (studentId, password) {
            if (!studentId || !password) {
                return "nothing";
            } else if (studentId.length != 9 || isNaN(studentId) || password.length != 6 || isNaN(password)) {
                return "type error"
            } else {
                if (bookAppointment.verifyWithDatabase(studentId, password)) {
                    return "success";
                } else {
                    return "mismatch";
                }
            }
        },
        //将学号和用户名与数据库相匹配
        verifyWithDatabase: function (studentId, password) {
            var result = false;
            var params = {};
            params.studentId = studentId;
            params.password = password;
            var verifyUrl = bookAppointment.URL.verify();
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
            init: function () {
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

        //预约图书逻辑
        detail: {
            //预定初始化
            init: function (params) {
                var bookId = params["bookId"];
                var studentId = $.cookie('studentId');
                var password = $.cookie('password');
                if (!studentId || !password) {
                    //设置弹出层属性
                    var IdAndPasswordModal = $('#verifyModal');
                    IdAndPasswordModal.modal({
                        show: true,//显示弹出层
                        backdrop: 'static',//禁止位置关闭
                        keyboard: false//关闭键盘事件
                    });
                    $('#studentBtn').click(function () {
                        studentId = $('#studentIdKey').val();
                        password = $('#passwordKey').val();
                        //调用validateStudent函数验证用户id和密码。
                        var temp = bookAppointment.validateStudent(studentId, password);
                        if (temp == "nothing") {
                            $('#studentMessage').html('<label>学号或密码为空!</label>').show();
                            setTimeout(function () {
                                $('#studentMessage').hide();
                            }, 1000);
                        } else if (temp == "type error") {
                            $('#studentMessage').html('<label>格式不正确!</label>').show();
                            setTimeout(function () {
                                $('#studentMessage').hide();
                            }, 1000);
                        } else if (temp == "mismatch") {
                            $('#studentMessage').html('<label>学号密码不匹配!</label>').show();
                            setTimeout(function () {
                                $('#studentMessage').hide();
                            }, 1000);
                        } else if (temp == "success") {
                            //学号与密码匹配正确，将学号密码保存在cookie中。不设置cookie过期时间，这样即为session模式，关闭浏览器就不保存密码了。
                            $.cookie('studentId', studentId, {path: '/'});
                            $.cookie('password', password, {path: '/'});
                            // 跳转到预约逻辑
                            var appointBox = $('#appoint-box');
                            bookAppointment.appointment(bookId, studentId, appointBox);
                        }
                    });
                } else {
                    var appointBox = $('#appoint-box');
                    bookAppointment.appointment(bookId, studentId, appointBox);
                }
            }
        }
        ,
        appointment: function (bookId, studentId, node) {
            node.html('<button class="btn btn-primary btn-lg" id="appointmentBtn">预约</button>');
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
;