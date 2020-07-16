$(function() {
    var listUrl = '/newsadmin/listnews';//获取页面json数据

   // $.toast({text: "加载成功！！！", textAlign: 'center', position: 'mid-center', hideAfter: 2000});
    getList();

    function getList() {
        $.getJSON(
            listUrl,
            function (data) {
                if (data.success) {
                    // $.toast('欢迎回来');
                    var dataList = data.newsList;
                   /* $('#news-list').html('');*/
                    var tempHtml = '';
                    dataList.map(function (item, index) {
                        tempHtml += ''
                           +'<tr>'
                           +' <td>'
                            +item.newsId
                            +'</td>'
                           +'<td>'
                            +item.newsTitle
                            +'</td>'
                           +' <td  class="thumbnail"><img src="/upload/img/'
                            +item.newsImage
                           + '"/></td>'
                           +' <td>'
                            +item.newsContents.substring(0,100)+'......'
                            +'</td>'
                           +' <td><button class="btn btn-danger" type="submit">确认删除</button></td>'
                           +'<tr/>';
                    });
                    $('#news-list').append(tempHtml);
                }

            });
    }
})