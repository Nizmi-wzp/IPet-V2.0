$(function() {
    var listUrl = '/baikeadmin/baikebypage';
    var listcountUrl = '/baikeadmin/baikeCount';
    getList('');
    //alert('查询成功')
    function getList(queryKey) {
        $.getJSON(
            listcountUrl,
            {queryKey:queryKey},
            function(data) {
                if (data.success) {
                    //  alert("2");
                    //分页插件
                    $.jqPaginator('#pagination', {
                        totalCounts: data.total,//总条目数
                        pageSize: 4,//每一页的条目数，要跟page类里值一样，不然显示的值对不上
                        visiblePages: 10,//最多显示的页码数
                        currentPage: 1,//当前的页码
                        wrapper: '<ul class="pagination" style="display: inline-block;padding-left: 0; margin: 0;border-radius: 4px;"></ul>',
                        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                        last: '<li class="last"><a href="javascript:void(0);">页尾</a></li>',
                        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                        onPageChange: function (pageIndex) {
                            alert("当前页："+pageIndex);
                            if (data.success) {
                                $.getJSON(
                                    listUrl,
                                    {
                                        queryKey: queryKey,
                                        pageIndex: pageIndex
                                    },
                                    function (data) {
                                        var dataList = data.baikeList;
                                        $('#baike').html('');
                                        var tempHtml = '';
                                        dataList.map(function (item, index) {
                                            tempHtml += ''
                                                + '<div >'
                                                + '<div  style="font-size: large;">'
                                                + item.baikeTheme
                                                + '</div>'
                                                + '<img class="col-4" style="width: 20%;height: 30%" src="'
                                                + item.baikeImage
                                                + '">'
                                                + '<div class="col-8">'
                                                + item.baikeText
                                                + '</div>'
                                                + '<br>'
                                                + '</div>';
                                        });
                                        $('#baike').append(tempHtml);
                                    });
                            }

                        }
                    });
                }
            });
    }



    $("#search").click(

        function () {
            var queryKey=$("#queryKey").val();
            getList(queryKey);
        }


    );


});