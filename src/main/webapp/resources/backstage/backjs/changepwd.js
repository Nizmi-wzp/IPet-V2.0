$(function() {
    // 修改平台密码的controller url
    var url = '/admin/changeadminpwd';
    $('#submit').click(function() {

        // 获取帐号
        var adminName = $('#adminName').val();
        // 获取原密码
        var password = $('#password').val();
        // 获取新密码
        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();
        if (newPassword != confirmPassword) {
           alert('两次输入的新密码不一致！');
            return;
        }
        // 添加表单数据
        var formData = new FormData();
        formData.append('adminName', adminName);
        formData.append('password', password);
        formData.append('newPassword', newPassword);
        // 获取验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            alert('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        // 将参数post到后台去修改密码
        alert("sdsd="+formData.get('adminName'));
        alert(formData.get('verifyCodeActual'));
        $.ajax({
            url : url,
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    alert('提交成功！');

                        window.location.href = '/backstage/Login';

                } else {
                    alert('提交失败！' + data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

    $('#back').click(function() {
        window.location.href = '/backstage/Login';
    });
});
