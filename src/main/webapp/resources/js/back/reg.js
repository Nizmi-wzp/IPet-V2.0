$(function () {
    //获取短信验证码点击事件
    $('#LAY-user-reg-getsmscode').click(function () {
        var phone = $('#LAY-user-login-cellphone').val();
        if (phone.length != 11) {
            alert('请检查手机号码是否正确！' + phone.length);
        }
        console.log(phone);
        $.ajax({
            url: '/admin/sendMessage',
            type: 'POST',
            data: {phone: phone},
            dataType: 'json',
            success: function (data) {
                //data[i].id == undefined ? "" : data[i].id;
                if (data.success) {
                    layer.msg('短信发送成功', {icon: 1});

                } else {
                    layer.msg('短信发送失败', {icon: 2});
                }
            }
        })
        //点击事件样式
        var time = 60;
        layer.alert('发送成功！', {icon: 1});
        var myVar = setInterval(function () {
            $('#LAY-user-reg-getsmscode').text(time + 's');
            time--;
            if (time == 0) {
                $('#LAY-user-reg-getsmscode').text("重新发送");
                //$('#LAY-user-reg-getsmscode').on('click',a());
                //a();
                //$('#LAY-user-reg-getsmscode').on("click",a());
                $('#LAY-user-reg-getsmscode').removeClass("layui-btn-disabled");
                clearInterval(myVar);
            }
            else {
                $('#LAY-user-reg-getsmscode').addClass("layui-btn-disabled");//禁止点击样式
                $('#LAY-user-reg-getsmscode').off('click');//取消绑定点击事件
            }
        }, 1000);
    })
/*
    $('#LAY-user-reg-getsmscode').click(function () {
        var times=60;
        layer.alert('发送成功！',{icon:1});
        var myVar =  setInterval(function(){
            $('#LAY-user-reg-getsmscode').text(times+'s');
            times--;
            if(times==0){
                $('#LAY-user-reg-getsmscode').text("重新发送");
                clearInterval(myVar);
            }
            }, 1000);
       // $.ajax({})
       //输入框
        var number=parseInt(Math.random()*8999+1000)
        $('#vercode').val(number);
        });
*/

    //管理员注册
    $('#adminlogin').click(function () {
                var adminName=$('input[name="adminname"]').val();
                var phone=$('input[name="cellphone"]').val();
                var password=$('input[name="password"]').val();
                var nickname=$('input[name="nickname"]').val();
                var role=$('#role option:selected').text();
                var inputcode=$('#vercode').val();
                var formdata=new FormData();
                formdata.append('adminName',adminName);
                formdata.append('password',password);
                formdata.append('role',role);
                formdata.append('nickName',nickname);
                formdata.append('phone',phone);
                formdata.append('inputCode',inputcode);
        $.ajax({
            url: '/admin/reg',
            type: 'POST',
            data: formdata,
            dataType: 'json',
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                //data[i].id == undefined ? "" : data[i].id;
                if (data.success) {
                    layer.msg('注册成功', {icon: 1});
                    //注册成功后跳转页面
                    window.location.href= '/back/login';

                } else {
                    layer.msg('注册失败', {icon: 2});
                }
            }
        })


    })





})