$(function() {
    var listUrl = '/useradmin/listuser';//获取页面json数据
    getList();
    function getList() {
        $.getJSON(
            listUrl,
            function(data) {
                if (data.success) {

                    alert("hello");
                    var dataList = data.userList;
                    $('#user').html('');
                    var tempHtml = '';

                    dataList.map(function(item, index) {
                            tempHtml += ''
                                + '<div class="row row-product-category now">'
                                + '<div class="col-33 product-category-name">'
                                + item.userId
                                + '</div>'
                                + '<div class="col-33">'
                                + item.userName
                                + '</div>'
                                /*+ '<div class="col-33"><a href="#" class="button delete" data-id="'
                                + item.userPassword
                                + '">删除</a></div>'*/
                                + '</div>';
                        });
                    $('#user').append(tempHtml);
                   // $('#user').html(tempHtml);
                }
            });
    }





});