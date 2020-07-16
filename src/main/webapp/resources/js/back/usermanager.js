layui.use(['laypage','layer'] ,function() {

    var laypage = layui.laypage;
    var layer=layui.layer;
    //加载用户列表
    $(document).ready(function () {
        $('#users').click();
    })
    pageUrl = '/useradmin/listuserpage';
    countUrl = '/useradmin/usercount';
    //查询分页功能
    search('');
    //submit提交查询事件
    $('#usersearch').click(function () {
        var queryKey = $("#queryUser").val();
        search(queryKey);
    })
     //取消收藏
    $('#userlist').on('click','#collect',function (e) {
        $('#userlist').children().css('background','yellow');
        if($(this).text()=="标记"){
        $('#ok').removeClass('layui-icon-ok');
        $(this).addClass('layui-bg-cyan')
        $(this).text("已标记");}
        else{
            $('#ok').addClass('layui-icon-ok');
            $(this).removeClass('layui-bg-cyan');
            $(this).text("标记");
           $('#userlist').children().css('background','white');

        }
    })
    //弹出图片
    $('#userlist').on('click','#layer-photos-demo',function(){
        layer.photos({
            photos: 'layer-photos-demo'
            ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });
    })
    //新增用户
    $('#useradd').click(function () {
        var username=$('#usename').val();
        var password=$('#password').val();
        var repassword=$('#repassword').val();
        $.ajax({
            url: '/useradmin/adduser',
            async: false,
            type: "post",
            data: {userName:username,
                userPwd:password,
                userPwd2:repassword},
            dataType: "json",
            success: function (datas) {
                if(datas.success){layer.msg("添加成功！")}
                else {layer.msg('添加失败！')}
            }
        })
    })
    //用户列表分页展示
    function search(queryKey) {
        $.ajax({
            url: countUrl,
            async: false,
            type: "get",
            data: {queryKey: queryKey},
            dataType: "json",
            success: function (datas) {
                if (datas.success) {
                    //分页组件
                    laypage.render({
                        elem: 'userpage' //分页容器的id
                        , count: datas.count//数据总数从后台获取
                        , limit: 10//默认为10,分页大小
                        , skin: '#1E9FFF' //自定义选中色值
                        //,skip: true //开启跳页
                        , jump: function (obj, first) {
                                $.ajax({
                                    url: pageUrl,
                                    type: 'GET',
                                    data: {
                                        pageIndex: obj.curr,
                                        pageSize: 10,
                                        queryKey: queryKey
                                    },
                                    dataType: 'json',
                                    success: function (data) {
                                        if (data.success) {
                                            var dataList = data.userList;
                                            var str = '';
                                            dataList.map(function (item, index) {
                                                str += ''
                                                    + '<div class="caller-item">'
                                                    + '<img layer-src="/upload/img/item/pets/bird1.jpg" id="layer-photos-demo" src="'
                                                    + item.userImage
                                                    + '" alt="头像"  class="layui-nav-img caller-img caller-fl">'
                                                    + '<div class="caller-main caller-fl">'
                                                    + '<p><strong>'
                                                    + item.userName
                                                    + '</strong> <em>最近登陆：' + item.lastLogin + '</em></p>'
                                                    + '<p class="caller-adds"><i class="layui-icon layui-icon-cellphone"></i>电话：' + item.telephone + '</p>'
                                                    + '<div class="caller-iconset">'
                                                    + '<i class="layui-icon layui-icon-login-wechat"></i>'
                                                    + '<i class="layui-icon layui-icon-login-qq"></i>'
                                                    + '<i class="layui-icon layui-icon-login-weibo"></i>'
                                                    + '</div>'
                                                    + '</div>'
                                                    +'<div>'
                                                    + '<button id="collect" class="layui-btn layui-btn-sm caller-fr" style="position: relative;float: right;top: -5em">'
                                                    + '<i id="ok" class="layui-icon layui-icon-ok"></i>'
                                                    + '标记'
                                                    + '</button>'
                                                    + '<button id="deluser" class="layui-btn layui-btn-danger layui-btn-sm caller-fr" style="position: relative;float: right;top: -5em">'
                                                    + '<i id="del" class="layui-icon layui-icon-404"></i>'
                                                    + '删除'
                                                    + '</button>'
                                                    +'<div>'
                                                    + '</div>'
                                                    +'<hr class="layui-bg-green">'
                                            })
                                            $('#userlist').html(str);
/*                                          动态获取js添加的元素
                                            $('#collect').click(function (e) {
                                                $('.caller-item').css('background','red');
                                            })*/
                                        } else {
                                            layer.alert('未成功~！')
                                        }
                                    }
                                })
                        }
                    })
                }
            }
        })
    }
    //删除用户，前端展示删除行，后端删除数据库中的数据
    $(document).on('click','.deletePostbyID', function (e) {
                //  var target = $(e.currentTarget);
                // 删除帖子，并带有postId参数
                var postId=$(this).attr("data-id");
                $.confirm({
                    title: '确认',
                    content: '确定删除么?',
                    icon: 'glyphicon glyphicon-question-sign',
                    buttons: {
                        ok: {
                            text: '确认',
                            btnClass: 'btn-primary',
                            action: function() {
                                $.ajax({
                                    url : delpostUrl,
                                    type : 'GET',
                                    data : {
                                        postId : postId
                                    },
                                    dataType : 'json',
                                    success : function(data) {
                                        if (data.success) {
                                            $.alert({
                                                title: 'Alert!',
                                                content: '操作成功!'
                                            });
                                            init();
                                        } else {
                                            $.alert({
                                                title: 'Alert!',
                                                content: '操作失败!'
                                            });
                                        }
                                    }
                                });
                            }
                        },
                        cancel: {
                            text: '取消',
                            btnClass: 'btn-primary'
                        }
                    }
                });

            });





})

