$(function () {
    url='/admin/adminrole';
    newestBoardUrl='/boardadmin/newestboard';
    userSizeUrl="/useradmin/listuser";//统计用户数量
    adminSizeUrl="/admin/listadmin";//统计管理员数量
    photoSizeUrl="/imagesadmin/listimages";//统计图片数量
    videoSizeUrl="/videoadmin/listvideo";//统计视频数量
    $.getJSON(
        userSizeUrl,
        function (data) {
            if (data.success) {
                //用户数量
                var usersize = data.total;
                //$("#usercount").data("to","666");可用
                //$("#usercount").setAttribute('data-to','5555');
                //$("#usercount").attr("data-to",usersize);可用
            }
            else{return;}
        }
    );
    //个人信息样式
    $('#msg').click(function () {
        $('#msgdot').remove();
    })
    //弹出层公告栏
    $('#inform').click(function () {
               layer.prompt({
                    formType: 2,
                    value: '请输入公告信息',
                    title: '<h2>最新公告</h2>',
                    area: ['800px', '350px'] //自定义文本域宽高
                }, function(value, index, elem){
                   $.ajax({
                       url:'/boardadmin/addboard',
                       type: 'POST',
                       data: {content:value},
                       dataType: 'json',
                       success: function (data) {
                           if (data.success){alert('发布成功！');}
                           else {alert('发布失败!');}
                       }
                   });
                    alert(value); //得到value
                   //发送最新公告
                    layer.close(index);
                });
    })
    //异步传送最新的一条滚动消息
    $.getJSON(
        newestBoardUrl,
        function (data) {
            if (data.success) {
                //管理员名称
                var newestBoard = data.newestBoard;
                var temphtml='';
                temphtml+=''
                        +'最新公告：'
                        +'<i class="layui-icon layui-icon-speaker">'
                        +'</i>'
                        +newestBoard
                $('#newest_board').append(temphtml);
                //$('#newest_board').text('最新公告：'+newestBoard);
            }
            else{return;}
        }
    );
    //登录信息保存
        $.getJSON(
            url,
            function (data) {
                if (data.success) {
                    //管理员名称
                    var adminName = data.admin.adminName;
                    $('#loginname').text(adminName);
                    //管理员头像
                    var image=data.admin.photo;
                    $('#loginimg').attr('src',image);
                   //管理员角色
                    var admindata = data.admin;
                    if(admindata.role=="超级管理员"){
                        $('#superadmin').css('display','block');
                        $('#delboard').css('display','block');
                    }
                    else{return;}
                    layer.msg(admindata.role+'欢迎您！', {icon: 6});
                }
                else {layer.alert('请先登陆',{icon: 5})}
            }
         );
    //如果是超级管理员则可以管理其他管理员,非超级管理员隐藏此功能
    $('#superadmin').css('display','none');
    $('#delboard').css('display','none');
    //移除点
    $('#myself').click(function () {
        $('#dot').remove();
        }
    )
    //动态化底部时间(无效代码)
/*        $('#time').setTimeout(function () {
        },1000);*/
    //使用Echart组件
    var myChart = echarts.init(document.getElementById('echart'));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '视频点击量'
        },
        tooltip: {},
        legend: {
            data:['视频点击量']
        },
        xAxis: {
            data: ["猫","狗","鸟","水族","虫类","异类"]
        },
        yAxis: {},
        series: [{
            name: '点击量',
            type: 'bar',
            data: [10,20,30,40,50,60]
        }]
    };
    //先调用load函数，用于从后台获得数据并且传给option中的xAxis以及series（这两个用于显示柱状图）
    load(option);
    // 然后为echarts对象加载数据
    myChart.setOption(option);

    //利用ajax技术从后台获取数据并且给option
    function load(option){
        $.ajax({
            type : "get",
            async : false, //同步执行
            url : "/videoadmin/videoclick",   //后台处理的servlet路径
            data : {},
            dataType : "json", //返回数据形式为json
            success : function(result) {  //如果ajax成功则后台json返回到result中
                if (result.success) {
                    //初始化option.xAxis[0]中的data，就是给option中的xAxis加入data[]
                    var len=result.total;
                    var videoList=result.videoClickList;
                    option.xAxis.data=[];
                    for(var i=0;i<len;i++){
                        option.xAxis.data.push(videoList[i].videoType);
                    }
                    //初始化option.series[0]中的data
                    option.series[0].data=[];
                    for(var i=0;i<len;i++){
                        //alert(videoList[i].clicks)
                        option.series[0].data.push(videoList[i].clicks);
                    }



                }
            }
        });
    }


})


