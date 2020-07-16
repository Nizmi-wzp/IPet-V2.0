$(function() {
    var listUrl = '/newsadmin/listnews';
    //alert('查询成功')
        $.getJSON(
            listUrl,
            function(data) {
                if (data.success) {
                    //  alert("2");
                                        var dataList = data.newsList;
                                        $('#user').html('');
                                        var tempHtml = '';
                                        dataList.map(function (item, index) {
                                            tempHtml += ''
                                                + '<div >'
                                                + '<div style="font-size: large;">'
                                                + item.newsTitle
                                                + '</div>'
                                                + '<div class="col-4"> '
                                                + item.newsContents
                                                + '</div>'
                                                + '<div class="col-8">'
                                                + item.newsTime
                                                + '</div>'
                                                + '<br>'
                                                + '</div>';
                                        });
                                        $('#user').append(tempHtml);
                            }

    })

    $("#search").click(
        function () {
            var queryKey=$("#queryKey").val();
            getList(queryKey);
        }
    );
});