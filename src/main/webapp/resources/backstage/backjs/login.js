$(function() {
    // 登录验证的controller url
    var loginUrl = '/admin/logincheck';
    var loginCount = 0;

    $('#submit').click(function() {
       // 获取输入的帐号
        var name = $('#num1').val();
        // 获取输入的密码
        var password = $('#num2').val();
        // 获取验证码信息
        if (loginCount >= 3) {
            // 那么就需要验证码校验了

                alert('请输入验证码！');

        }

        // 访问后台进行登录验证
        $.ajax({
            url : loginUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                adminName : name,
                adminPassword : password,

            },
            success : function(data) {
                if (data.success) {
                    $.toast({text:"登录成功！！！",textAlign:'center',position:'mid-center',hideAfter:5000});
                    /*$(document).ready(
                        function () {
                            //alert("执行中")
                            //创建cookie,时间为7天，路径为上下文路径
                            $.cookie('adminname',name,{expires:7,path:'/'});
                            //alert("创建cookie")
                            adminname=$.cookie('adminname');
                            $('#num1').val(adminname);
                            //alert(adminname);

                            $.cookie('adminpassword',password,{expires:7,path:'/'});
                            adminpassword=$.cookie('adminpassword');
                            $('#num2').val(adminpassword);
                            //alert(adminpassword);

                        }
                    )*/
                    window.location.href= '/backstage/backmain';
                    //页面显示session

                   var loginname=request.getSession().getAttribute("loginname")

                    $("#logname").innerText=loginname+"欢迎您";

                } else {
                    alert('登录失败！' + data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        // 登录失败三次，需要做验证码校验
                        window.location.href = '/backstage/loginerror';
                    }
                }
            }
    });
    });


    $(document).keydown(function(event){
        if(event.keyCode ==13){
            $("#submit").trigger("click");
        }
    });

    $(".loginName").text(document.cookie.fixed());



});