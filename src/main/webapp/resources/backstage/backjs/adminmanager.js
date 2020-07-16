$(function() {
    var listUrl = '/admin/listadmin';//获取页面json数据
    var deleteUrl = '/admin/removeadmin';//获取页面删除数据；
    var addUrl = '/admin/addadmin';//获取页面添加数据;
    $.toast({text:"加载成功！！！",textAlign:'center',position:'mid-center',hideAfter:2000});
    getList();
    function getList() {
        $.getJSON(
            listUrl,
            function (data) {
                if (data.success) {
                    // $.toast('欢迎回来');
                    var dataList = data.adminList;
                    $('#admintable').html('');
                    var tempHtml = '';
                    dataList.map(function (item, index) {
                        tempHtml += ''
                            + '<div class="row container admin" style="background: papayawhip">'
                            + '<div class="col-md-2" style="background: white">'
                            + item.adminId
                            + '</div>'
                            + '<div class="col-md-2" style="background: palegoldenrod">'
                            + item.adminName
                            + '</div>'
                            + '<div class="col-md-2">'
                            + item.adminPassword
                            + '</div>'
                            + '<div class="col-md-2" style="background: palevioletred">'
                            + item.adminContent
                            + '</div>'
                            + '<div class="col-md-2">'
                            + '<div class="btn btn-danger deleteadmin"  dataId="'
                            + item.adminId
                            + '">删除</div>'
                            + '</div>'
                            + '</div>';
                    });
                    $('#admintable').append(tempHtml);
                }

            });
    }



    $('#admintable').on('click','div.deleteadmin',function (e) {
        console.log($(this));
        $(this).parent().parent().remove();//删除当前元素祖宗节点tr
    });

//删除后台数据库数据
    $('#admintable').on('click','div.deleteadmin',function (e) {
        //var target=e.currentTarget;
        var id = $(this).attr("dataId");
        $("#test").innerText = id;
        alert(id);
        $("#test").css("background-color","yellow");
        var num=confirm("确认删除吗?");
        if(num==true){
            $.ajax({
                url: deleteUrl,
                type: 'GET',
                data: {adminId: id},
                dataType: 'json',
                success: function (data) {
                    //data[i].id == undefined ? "" : data[i].id;
                    if (data.success) {
                        $.toast('删除成功!');
                        getList();
                    } else {
                        $.toast('删除失败!');
                    }
                }
            });
        }
        else{
            $.toast("放弃删除");
        }
  });


    //新增管理员
    $('a#new')
        .click(
            function() {
                var tempHtml =
                    // em继承根节点，rem继承父节点
                    '<div class="row container insertadmin" style="background: papayawhip">'
                    + '<div class="col-md-2" style="background: white"><input class="adminId" style="border-style: none" type="number" placeholder="编号">'
                    + '</div>'
                    + '<div class="col-md-2" style="background: palegoldenrod"><input class="adminname" style="border-style: none"type="text" placeholder="名称">'
                    + '</div>'
                    + '<div class="col-md-2"><input class="adminpwd" style="border-style: none" type="text" placeholder="密码">'
                    + '</div>'
                    + '<div class="col-md-2" style="background: palevioletred"><input class="adminintroduce" style="border-style: none" type="text" placeholder="简介">'
                    + '</div>'
                    + '<div class="col-md-2">'
                    + '<div class="btn btn-danger deleteadmin"  dataId="'
                    + '">删除</div>'
                    + '<div class="btn btn-info addadmin"  dataId="'
                    + '">添加</div>'
                    + '</div>'
                    + '</div>';
                $('#admintable').append(tempHtml);
            });

    $('.submit').click(function (e) {
        var tempArr = $('.insertadmin');
        var adminList = [];
        //封装
        //$.map()把每个元素通过函数传递到当前匹配集合中,生成包含返回值的新的JQuery对象
        tempArr.map(function(index, item) {
            var tempObj = {};
            tempObj.adminName = $(item).find('.adminname').val();

            tempObj.adminPassword = $(item).find('.adminpwd').val();
            tempObj.adminContent = $(item).find('.adminintroduce').val();
            if (tempObj.adminName && tempObj.adminPassword) {

                adminList.push(tempObj);
            }
        });
        //alert(JSON.stringify(adminList));
        $.ajax({
            url : addUrl,
            type : 'post',
            data : JSON.stringify(adminList),
            contentType : 'application/json',
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    getList();
                } else {
                    $.toast('提交失败！');
                }
            }
        });
    });


});