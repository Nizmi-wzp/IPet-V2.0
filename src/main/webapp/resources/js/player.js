$(function () {
// 从地址栏里获取videoID
    var videoId = getQueryString('videoID');
    alert('视频编号：'+videoId);
    url='/videoadmin/watchVideo?videoID='+videoId;
    $.ajax({
        url:url,
        type: 'GET',
        data: {videoID:videoId},
        dataType: 'json',
        success: function (data) {
            //data[i].id == undefined ? "" : data[i].id;
            if (data.success) {
                alert("success");
                // $.toast('欢迎回来');

            } else {
                alert("failed");
                //$.toast('删除失败!');
            }
        }

    });

    getVideoList();
    function getVideoList() {
        $.getJSON(
            url,
            function (data) {
                if (data.success) {
                    alert('数据获取成功')
                    var dataList = data.video;
                    var tempHtml = '';
                        tempHtml += ''
                            +'<h2 style="">'
                            +dataList.title
                            +'</h2>'
                            +'<video width="800" height="600"  controls >'
                            +'<source src="'
                            +dataList.video
                            +'" type="video/mp4">'
                            +'您的浏览器不支持Video标签。'
                            +'</video>';
                    $('div#vedios-player').append(tempHtml);
                    //$('div#vedios-player').css('background','yellow');

                }
                else{
                    alert("failed");
                }

            });
    }




})