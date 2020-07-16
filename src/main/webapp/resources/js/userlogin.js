$(function() {
    var loginUrl = '/useradmin/loginuser';


    $("#submit").click( function(){

        var userName=$("#userName").val();
        var pwd=$("#userPWD").val();
        //alert(userName);
        //alert(pwd);
        $.ajax({
            url : loginUrl,
            type : 'POST',
            data : {
                userName : userName,
                userPwd:pwd
            },
            dataType : 'json',
            success : function(data) {
                if (data.success) {

                    $.confirm({
                        title: '确认',
                        content: '登陆成功，是否继续?',
                        icon: 'glyphicon glyphicon-question-sign',
                        buttons: {
                            ok: {
                                text: '确认',
                                btnClass: 'btn-primary',
                                action: function() {
                                    window.location.href="index";
                                }
                            },
                            cancel: {
                                text: '取消',
                                btnClass: 'btn-primary'
                            }
                        }
                    });



                } else {

                    $.alert({
                        title: 'Alert!',
                        content: '登陆失败！'+data.errMsg
                    });
                    //alert('登陆失败！'+data.errMsg);
                }

            }
        });
    });

});