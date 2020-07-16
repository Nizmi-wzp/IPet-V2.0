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
        elem: '#imglist'
        ,height: 420
        ,url: '/imagesadmin/getimageslistbypage' //数据接口
        ,title: '图片表'
        ,page: true //开启分页
        ,limit:6
        //,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,totalRow: true //开启合计行
        ,cols: [[ //表头<img class="layui-nav-img" src="'+images+'">
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'imagesId', title: 'ID', width:80, sort: true, fixed: 'left', totalRowText: '合计：'}
            ,{field: 'images', title: '图片路径', width:80}
            ,{field: 'title', title: '标题', width: 90, sort: true,totalRow: true}
            ,{field: 'petType', title: '宠物类型', width:80, sort: true}
            ,{field: 'introduce', title: '简介', width: 80, sort: true, totalRow: true}
            ,{field: 'petId', title: '宠物Id', width:150}
            ,{fixed: '',title:"操作", width: 165, align:'center', toolbar: '#barDemo'}
        ]]
        ,parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": 0, //解析接口状态
                "msg": "", //解析提示文本
                "count": res.count, //解析数据长度
                "data": res.imagesList//解析数据列表
            };
        }
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
                area: '500px',
                maxmin: true,
                content: '<img style="width: 100%;height: 100%" src="'+data.images+'">',
                zIndex: layer.zIndex, //重点1
                success: function(layero){
                    layer.setTop(layero); //重点2
                }
            });


        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                var imageId=data.imagesId;
                alert(imageId);
                if(imageId){
                    $.ajax({
                        url:  '/imagesadmin/delimages',
                        async: false,
                        type: "POST",
                        data: {imagesId: imageId},
                        dataType: "json",
                        success: function (datas) {
                            if(datas.success){
                                layer.alert("删除成功",{icon:1});
                            }
                            else layer.alert('删除失败',{icon:2});
                        }
                    })
                }
                else {
                    layer.alert("未获取到图片信息！")
                }


            });
        } else if(layEvent === 'edit'){
            layer.msg('编辑操作');
        }
    });

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
        ,url: 'upload' //上传接口
        ,done: function(res){
            console.log(res)
        }
    });

    var uploadInst = upload.render({
        elem: '#addimg' //绑定元素
        ,url: '/imagesadmin/addimages' //上传接口
        ,done: function(res){
            //上传完毕回调
            layer.msg('上传成功！！！', {icon: 6});
        }
        ,error: function(){
            //请求异常回调
            layer.msg('请求异常！！！', {icon: 7});
        }
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

