$(function() {

    var loading = false;
    // 分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 999;
    // 一页返回的最大条数
    var pageSize = 3;
    // 获取店铺列表的URL
    var listUrl = '/imagesadmin/getimageslistbyId';
    // 页码
    var pageNum = 1;

    addItems(pageSize, pageNum);
    function addItems(pageSize, pageIndex) {
        // 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize;
        // 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
        loading = true;
        // 访问后台获取相应查询条件下的店铺列表
        $.getJSON(url, function(data) {
            if (data.success) {
                // 获取当前查询条件下店铺的总数
                maxItems = data.count;
                var html = '';
                // 遍历店铺列表，拼接出卡片集合
                data.imagesList.map(function(item, index) {
                    html +=  ''
                        +'<li class="imglist card">'
                        +'<div class="card-header">新的公布:</div>'
                        +'<div class="card-content">'
                        +'<div class="list-block media-list">'
                        +'<div>'
                        +'<div class="item-content">'
                        +'<div class="item-media">'
                        +'<img src="'
                        +item.images
                        +'" width="44">'
                        +'</div>'
                        +'<div class="item-inner">'
                        +'<div class="item-title-row">'
                        +'<div class="item-title">标题</div>'
                        +'</div>'
                        +'<div class="item-subtitle">子标题</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'<div class="card-footer">'
                        +'<span>2015/01/15</span>'
                        +'<span>5 评论</span>'
                        +'</div>'
                        +'<img class="item-media" src="'
                        +item.images
                        +'" class="petimage" width="100">'
                        +'<button class="bntton button-danger">撤销</button>'
                        +'<button class="bntton button-light">替换</button>'
                        +'</li>';
                });
                // 将卡片集合添加到目标HTML组件里
                $('ul#imglist').append(html);
                var total = $('ul#imglist li').length;
                alert(total)
                // 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
                if (total >= maxItems) {
                    // 隐藏提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    $('.infinite-scroll-preloader').show();
                }
                // 否则页码加1，继续load出新的店铺
                pageNum++;
                //pageNum += 1;
                // 加载结束，可以再次加载了
                loading = false;
                // 刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    // 下滑屏幕自动进行分页搜索
 $('.infinite-scroll-bottom').scroll(function () {
     $.toast('666')
    // addItems(pageSize, pageNum);
 })

    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        alert('44')
        if (loading)
            return;
        alert('888')
        addItems(pageSize, pageNum);
    });
/*    $.config = {
        // 路由功能开关过滤器，返回 false 表示当前点击链接不使用路由
        routerFilter: function($link) {
            // 某个区域的 a 链接不想使用路由功能
            if ($link.is('.disable-router a')) {
                return false;
            }

            return true;
        }
    };*/

    $.init()
})