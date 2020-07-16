$(function() {
    var listUrl = '/vedioadmin/listvedio';//获取页面json数据
alert('666')
    // $.toast({text: "加载成功！！！", textAlign: 'center', position: 'mid-center', hideAfter: 2000});
    getList();
    function getList() {
        $.getJSON(
            listUrl,
            function (data) {
                if (data.success) {
                    // $.toast('欢迎回来');
                    var dataList = data.vedioList;
                    /* $('#news-list').html('');*/
                    var tempHtml = '';
                    dataList.map(function (item, index) {
                        tempHtml += ''
                            +'<li><embed src="'
                            +item.vedios
                            + '"/>'
                            + '<button class="btn btn-danger">删除</button><button class="btn btn-info">修改</button>'
                            +'</li>'
                    });
                    $('ul#vediolist').append(tempHtml);
                }

            });
    }
})