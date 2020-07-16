$(function() {
    // 登录验证的controller url
    var loginUrl = '/admin/logincheck';
    var loginCount = 0;
    //管理员账号失去聚焦事件
    $('#adminname').change(function () {
        var adminname=$('#adminname').val();
        $.ajax({
            url: '/admin/loginNamecheck',
            type: 'POST',
            data: {adminName: adminname},
            dataType: 'json',
            success: function (data) {
                //data[i].id == undefined ? "" : data[i].id;
                if (data.success) {
                    $('#adminpermit').css({"display":"block","color":"#1c7430"});
                    $('#adminpermit').text('该账户可用');
                } else {
                    $('#adminpermit').css({"display":"block","color":"red"});
                    $('#adminpermit').text('该账户不可用');
                }
            }
        });
    })
    //切换密码可见
    $('#view').click(function () {
        var type=$('#password').attr('type');
        if(type=='password'){
            $('#password').attr('type','text');
        }else{
            $('#password').attr('type','password');
        }
       //$('#password').attr('type','password');
       })
   //登录验证
    $('#submit').click(function() {
        // 获取输入的帐号
        var name = $('#adminname').val();
        // 获取输入的密码
        var password = $('#password').val();
        // 获取验证码
        var verifyCodeActual = $('#vercode').val();
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
                verifyCodeActual:verifyCodeActual

            },
            success : function(data) {
                if (data.success) {
                    layer.alert('登录成功！！')
                    window.location.href= '/back/index';
                } else {
                    //询问框
                    layer.confirm('登录失败!', {
                        btn: ['确认','取消'] //按钮
                    }, function(){
                        layer.msg('用户名或密码错误', {icon: 2});
                        $('#j_captcha').click();
                    }, function(){
                        $('#j_captcha').click();
                    });



                }
            }
        });
    });

//回车确认
    $(document).keydown(function(event){
        if(event.keyCode ==13){
            $("#submit").trigger("click");
        }
    });
    //记住密码,获取勾选事件



});