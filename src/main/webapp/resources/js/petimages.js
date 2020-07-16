$(function() {
    var countUrl='/imagesadmin/getrecordCount';
    var listPageUrl = '/imagesadmin/getimageslistbypage';//分页数据
//    var queryKey=$("#queryKey").val();
    search("");




    $('#querySubmit').click(function () {
        var queryKey=$("#queryKey").val();
        search(queryKey);
    })
    //分页搜索
    function search(queryKey){
        $.ajax({
            url:countUrl,
            async: false,
            type: "get",
            data:{queryKey:queryKey},
            dataType: "json",
            success : function(datas) {
                if(datas.success){
                    //分页插件
                    $.jqPaginator('#pagination', {
                        totalCounts: datas.recordCount,//总条目数
                        pageSize: 16,//每一页的条目数，要跟page类里值一样，不然显示的值对不上
                        visiblePages: 10,//最多显示的页码数
                        currentPage: 1,//当前的页码
                        wrapper: '<ul class="pagination" style="display: inline-block;padding-left: 0; margin: 0;border-radius: 4px;"></ul>',
                        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                        last: '<li class="last"><a href="javascript:void(0);">页尾</a></li>',
                        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                        onPageChange: function (pageIndex) {
                            $.ajax({
                                url: listPageUrl,
                                async: false,
                                data: {
                                    page: pageIndex,
                                    limit:16,
                                    queryKey: queryKey,
                                },
                                type: "get",
                                dataType: "json",
                                success: function (data) {
                                    if (data.success) {
                                        var dataList = data.imagesList;
                                        var str = '';
                                        dataList.map(function (item, index) {
                                            str +=
                                                '<div class="col-md-3">'
                                                + '<a>'
                                                + '<img src="'
                                                + item.images
                                                + '" class="petimage">'
                                                + '</a>'
                                                + '</div>'
                                        })
                                        $("#petlist").html(str);
                                    }
                                    else {$("#petlist").html("暂无数据……");
                                    }

                                }

                            });

                        }
                    });
                }
                else {alert("数据发送失败")}



            }

    });
  };




});
