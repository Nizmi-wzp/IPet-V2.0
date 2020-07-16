$(function () {
    url='/admin/adminrole';
    reseturl='/admin/changeadminpwd';

    //提交修改密码
    $('#resetpwd').click(function () {

  alert('555')
        var curpassword=$('#current-password').val();
        var newpassword=$('#newpassword').val();
        var repassword=$('#repassword').val();


        alert(curpassword);
        var formdata=new FormData();
        formdata.append('curpassword',curpassword);
        formdata.append('newPassword',newpassword);
        formdata.append('repassword',repassword);
        alert('666')
        if(newpassword!=repassword){
            layer.alert('密码不一致', {icon: 2});
        }
/*        $.getJSON(
            url,
            function (data) {
                if (data.success) {
                    //管理员名称
                    var password=data.admin.adminPassword;
                    if(curpassword!=password){
                        layer.alert('当前输入密码有误！', {icon: 2});
                    }
                }
                else {alert('请先登录！！！')}

            });*/
        $.ajax({
            url: reseturl,
            type: 'POST',
            data: formdata,
            dataType:'json',
            contentType : false,
            processData : false,
            cache : false,
            success: function (data) {
                alert('7777')
                //data[i].id == undefined ? "" : data[i].id;
                if (data.success) {
                    layer.alert('修改成功', {icon: 1});

                } else {
                    layer.alert('修改失败', {icon: 3});
                }
            }
        });
    })



})