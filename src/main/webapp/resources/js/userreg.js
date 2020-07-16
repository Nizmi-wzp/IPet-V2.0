$(function() {
    var registerUrl = '/useradmin/adduser';
    $("#register").click( function(){

        var userName=$("#userName").val();
        var pwd=$("#PWD").val();
        var pwd2=$("#PWD2").val();
        //alert(userName);
       //alert(pwd);
       //alert(pwd2);

        $.ajax({
            url : registerUrl,
            type : 'POST',
            data : {
                userName : userName,
                userPwd:pwd,
                userPwd2:pwd2
            },
            dataType : 'json',
            success : function(data) {
              //  alert();
                if (data.success) {
                    alert('注册成功！');

                } else alert('注册失败！'+data.errMsg);
            }
        });
    });



});