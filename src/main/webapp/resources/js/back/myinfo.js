$(function () {
        url='/admin/adminrole';
        url='/admin/updateadmin';
        url='/admin/uploadheadimage'
        $.getJSON(
            url,
            function (data) {
                if (data.success) {
                    //管理员名称
                    var adminName = data.admin.adminName;
                    var sex=data.admin.sex;
                    var nickname=data.admin.nickname;
                    var phone=data.admin.phone;
                    var email=data.admin.email;
                    var contents=data.admin.contents;
                    //管理员头像
                    var image=data.admin.photo;
                    var role = data.admin.role;
                    $('#role').val(role);
                    $('#username').val(adminName);

                    if (sex=="男"){$('#male').attr('checked','')}
                        else if(sex=="女"){$('#female').attr("checked",'')}

                    $('#nickname').val(nickname);
                        alert(image);

                    $('#image').attr('src',image);
                    $('#phone').val(phone);
                    $('#email').val(email);
                    $('#person-words').val(contents);


                }
                else {}

            });


    }
)