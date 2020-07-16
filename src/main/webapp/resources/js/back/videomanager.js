layui.use(['laypage','layer','form'] ,function() {

    var laypage = layui.laypage;
    var layer=layui.layer;
    var form=layer.form;
    //加载用户列表


    pageUrl = '/videoadmin/listvideopage';
    countUrl = '/videoadmin/videocount';
    //查询分页功能
    search('');
    //submit提交查询事件
    $('#videosearch').click(function () {
        var queryKey = $("#queryVideo").val();
        search(queryKey);
    })


    //分页
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
                        elem: 'page' //分页容器的id
                        , count: datas.count//数据总数从后台获取
                        , limit: 4//默认为10,分页大小
                        , skin: '#1E9FFF' //自定义选中色值
                        //,skip: true //开启跳页
                        , jump: function (obj, first) {

                            $.ajax({
                                url: pageUrl,
                                type: 'GET',
                                data: {
                                    pageIndex: obj.curr,
                                    pageSize: 4,
                                    queryKey: queryKey
                                },
                                dataType: 'json',
                                success: function (data) {
                                    if (data.success) {
                                        var dataList = data.videoList;
                                        var str = '';
                                        dataList.map(function (item, index) {
                                            str += ''
                                                +'<div class="layui-col-md2 layui-col-sm4 layui-col-lg-offset3">'
                                                +'<div class="cmdlist-container">'
                                                +'<a href="javascript:;">'
                                                +'<video  width="150"  height="50"  controls >'
                                                +'<source src="'
                                                +item.video
                                                +'" type="video/mp4">'
                                                +'您的浏览器不支持Video标签。'
                                                +'</video>'
                                                +'</a>'
                                                +'<a href="javascript:;">'
                                                +'<div class="cmdlist-text">'
                                                +'<p class="info">视频标题：'+item.title+'</p>'
                                                +'<div class="price">'
                                                +'<b style="color: red">视频点击量：'+item.click+'</b>'
                                                +'<p>'
                                                +'<p>视频发布时间：'+item.publishTime+'</p>'
                                                +'</p>'
                                                +'<span class="flow">'
                                                +'<i class="layui-icon layui-icon-rate"></i>视频类型：'
                                                +item.videoType
                                                +'</span>'
                                                +'<button style="width: 100%" class="layui-btn  layui-btn-danger">删除</div>'
                                                +'</div>'
                                                +'</div>'
                                                +'</a>'
                                                +'</div>'
                                                +'</div>'
                                        });
                                        $('#videolist').html(str);

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
})

