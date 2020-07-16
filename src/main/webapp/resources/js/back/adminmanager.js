layui.config({
    version: '1559588381765' //为了更新 js 缓存，可忽略
});

layui.use(['laydate', 'form','laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], function(){
    var laydate = layui.laydate //日期
        ,laypage = layui.laypage //分页
        ,layer = layui.layer //弹层
        ,table = layui.table //表格
        ,carousel = layui.carousel //轮播
        ,upload = layui.upload //上传
        ,element = layui.element //元素操作
        ,slider = layui.slider //滑块
        ,form=layui.form;



    //监听Tab切换
    element.on('tab(demo)', function(data){
        layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
            tips: 1
        });
    });

    //执行一个 table 实例
    table.render({
        elem: '#demo'
        ,height: 420
        ,url: '/admin/listadminByPage' //数据接口
        ,title: '管理员表'
        ,page: true //开启分页
        ,limit:6
        ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,totalRow: true //开启合计行
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'adminId', title: 'ID', width:80, sort: true, fixed: 'left', totalRowText: '合计：'}
            ,{field: 'adminName', title: '管理员', width:80}
            ,{field: 'adminPassword', title: '密码', width: 90, sort: true,totalRow: true}
            ,{field: 'role', title: '身份', width:80, sort: true}
            ,{field: 'sex', title: '性别', width: 80, sort: true, totalRow: true}
            ,{field: 'nickname', title: '昵称', width:150}
            ,{field: 'photo', title: '头像', width: 200}
            ,{field: 'phone', title: '手机', width: 100}
            ,{field: 'email', title: '邮箱', width: 135, sort: true, totalRow: true}
            ,{field: 'contents', title: '签名', width: 135, sort: true, totalRow: true}
            ,{fixed: '',title:"操作", width: 165, align:'center', toolbar: '#barDemo'}
        ]]
        ,parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": 0, //解析接口状态
                "msg": "", //解析提示文本
                "count": res.count, //解析数据长度
                "data": res.adminList//解析数据列表
            };
        }
    });

    //监听头工具栏事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'add':
                layer.msg('添加');
                layer.open({
                    type: 1,
                    shadeClose: true,
                    skin: 'yourClass',
                    area:['25%'],
                    title: '<h2>添加管理员</h2>'
                    ,anim:4
                    ,content:$('#addAdmin')

                });
                break;
            case 'update':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    layer.alert('编辑 [id]：'+ checkStatus.data[0].adminId);
                }
                break;
            case 'delete':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                    var adminIdList=new Array();
                    for(var i=0;i<data.length;i++){
                        //adminIdList[i]=data[i].adminId;
                        adminIdList.push(data[i].adminId);
                    }
                    alert(adminIdList[0]);
                    $.ajax({
                        url : '/admin/batchremoveadmin',
                        type : 'POST',
                        data : JSON.stringify(adminIdList),
                        contentType : 'application/json',
                        processData : false,
                        cache : false,
                        success : function(data) {
                            if (data.success) {
                                alert('删除成功！');
                            } else {
                                alert('删除失败！' + data.errMsg);
                            }
                        }
                    });



                }
                break;
        };
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            layer.msg('查看操作');
            layer.open({
                type: 1,
                shade: false,
                shadeClose:true,
                area: '30%',
                title:'管理员详情',
                maxmin: true,
                content:
                    '<form class="layui-form" action="">'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员ID</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.adminId+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员名称</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.adminName+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员密码</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.adminPassword+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员角色</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.role+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员性别</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.sex+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员昵称</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.nickname+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员图片</label>'
                    +'<div class="layui-input-block layer-photos-demo" id="layer-photos-demo">'
                    +'<img id="img1"  class="layui-nav-img" layer-src="'+data.photo+'" src=" '+data.photo+'" alt="图片">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员电话</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.phone+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员邮件</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.email+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员签名</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  lay-verify="required" placeholder="'+data.contents+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                +'</form>'
                ,
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero);
                    layui.use('layer', function(){
                        var layer = layui.layer;
                        layer.photos({
                            photos: '#layer-photos-demo'
                            ,zIndex:20000000
                            ,shadeClose:true
                            ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
                        });
                    });

                    //重点2
                }


            });
            //layer.close(layer.index);


        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么',{anim:5}, function(index){

                //向服务端发送删除指令
                var adminId=data.adminId;
                var role=data.role;
                if(role=="超级管理员"){layer.alert("超级管理员不能被删除！")}
                else {
                    obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    $.ajax({
                        url:  '/admin/removeadmin',
                        async: false,
                        type: "get",
                        data: {adminId: adminId},
                        dataType: "json",
                        success: function (datas) {

                            if(datas.success){
                                layer.alert("删除成功",{icon:1});

                            }
                            else layer.alert('删除失败',{icon:2});
                        }
                    })
                }


            });
        } else if(layEvent === 'edit'){
            layer.msg('编辑操作');
            layer.open({
                type: 1,
                shade: false,
                area: '30%',
                title:'管理员详情',
                maxmin: true,
                anim:0,
                content:
                    '<form class="layui-form" action="">'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员ID</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  placeholder="'+data.adminId+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员名称</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title"   placeholder="'+data.adminName+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员密码</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title" readonly  placeholder="'+data.adminPassword+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员角色</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title"    placeholder="'+data.role+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员性别</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title"   lay-verify="required" placeholder="'+data.sex+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员昵称</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title"    placeholder="'+data.nickname+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员头像</label>'
                    +'<div class="layui-input-block">'
                    +'<img class="layui-nav-img" src=" '+data.photo+'">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员电话</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title"   placeholder="'+data.phone+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员邮件</label>'
                    +'<div class="layui-input-block">'
                    +'<input type="text" name="title"   placeholder="'+data.email+'" autocomplete="off" class="layui-input">'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<label class="layui-form-label">管理员签名</label>'
                    +'<div class="layui-input-block">'
                    +'<textarea type="text" name="title"   placeholder="'+data.contents+'" autocomplete="off" class="layui-input"></textarea>'
                    +'</div>'
                    +'</div>'
                    +'<div class="layui-form-item">'
                    +'<div class="layui-input-block">'
                    +'<button class="layui-btn" lay-submit lay-filter="formDemo">立即修改</button>'
                    +'<button type="reset" class="layui-btn layui-btn-primary">重置</button>'
                    +'</div>'
                    +'</div>'
                    +'</form>'
                ,
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero);
                    //重点2
                }


            });
            layer.close();

        }
    });

    //添加管理员
    $('#addsubmit').click(function () {

        var name=$('#adminName').val();
        var password=$('#password').val();
        var role=$('#role option:selected').text();
        var sex=$('#gender input[name="sex"]:checked').val();
        var nickName=$('#nickname').val();
        var photo=$('#photoURL').val();
        var phone=$('#phone').val();
        var email=$('#email').val();
        var contents=$('#content').val();
        var formdata=new FormData();
        formdata.append('adminName',name);
        formdata.append('password',password);
        formdata.append('role',role);
        formdata.append('sex',sex);
        formdata.append('nickName',nickName);
        formdata.append("photo",photo);
        formdata.append('phone',phone);
        formdata.append('email',email);
        formdata.append('contents',contents);
        $.ajax({
            url: '/admin/addAdmin',
            type: 'POST',
            data: formdata,
            dataType: 'json',
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                //data[i].id == undefined ? "" : data[i].id;
                if (data.success) {
                    layer.msg('修改成功', {icon: 1});

                } else {
                    layer.msg('修改失败', {icon: 3});
                }
            }
        })


    })



    //执行一个轮播实例
    carousel.render({
        elem: '#test1'
        ,width: '100%' //设置容器宽度
        ,height: 200
        ,arrow: 'none' //不显示箭头
        ,anim: 'fade' //切换动画方式
    });

    //将日期直接嵌套在指定容器中
    var dateIns = laydate.render({
        elem: '#laydateDemo'
        ,position: 'static'
        ,calendar: true //是否开启公历重要节日
        ,mark: { //标记重要日子
            '0-10-14': '生日'
            ,'2018-08-28': '新版'
            ,'2018-10-08': '神秘'
        }
        ,done: function(value, date, endDate){
            if(date.year == 2017 && date.month == 11 && date.date == 30){
                dateIns.hint('一不小心就月底了呢');
            }
        }
        ,change: function(value, date, endDate){
            layer.msg(value)
        }
    });

    //分页
    laypage.render({
        elem: 'pageDemo' //分页容器的id
        ,count: 100//数据总数
        ,limit:10//默认为10
        ,skin: '#1E9FFF' //自定义选中色值
        //,skip: true //开启跳页
        ,jump: function(obj, first){
            if(!first){
                layer.msg('第'+ obj.curr +'页', {offset: 'b'});
            }
        }
    });

    //上传
    upload.render({
        elem: '#uploadDemo'
        ,url: '' //上传接口
        ,done: function(res){
            console.log(res)
        }
    });
    //管理员头像
    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#photo' //绑定元素
            ,url: '/admin/uploadheadimage/' //上传接口
            ,done: function(res){
                //上传完毕回调:图片路径res.data.src
                layer.alert("上传成功！",{icon:1});
                $('#photoURL').val(res.data.src);
                $('#headimg').attr('src',res.data.src);
            }
            ,error: function(){
                //请求异常回调
                layer.alert("上传失败！"+res.msg,{icon:2});
            }
        });
    });


    slider.render({
        elem: '#sliderDemo'
        ,input: true //输入框
    });

    //底部信息
    var footerTpl = lay('#footer')[0].innerHTML;
    lay('#footer').html(layui.laytpl(footerTpl).render({}))
        .removeClass('layui-hide');
});


//JavaScript代码区域
layui.use('element', function(){
    var element = layui.element;//模块预加载

});



layui.use('laydate', function() {
    var laydate = layui.laydate;

    //传入Date对象给初始值
    laydate.render({
        elem: '#time'
        ,type: 'datetime'
        ,value: new Date()
    });

});

