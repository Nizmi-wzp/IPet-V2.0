$(function() {
    //绑定帐号的controller url
    var bindUrl = '/admin/bindacount';
    $('#submit').click(function() {
        // 获取输入的帐号
        var adminName = $('#adminName').val();
        // 获取输入的密码
        var password = $('#password').val();
        // 获取输入的验证码
        var verifyCodeActual = $('#j_captcha').val();
        var needVerify = false;
        if (!verifyCodeActual) {
            alert('请输入验证码！');
            return;
        }
        // 访问后台，绑定帐号
        $.ajax({
            url : bindUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                adminName : adminName,
                password : password,
                verifyCodeActual : verifyCodeActual
            },
            success : function(data) {
                if (data.success) {
                    alert('绑定成功！');

                        window.location.href = '/backstage/Login';


                } else {
                    alert('提交失败！' + data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });
});