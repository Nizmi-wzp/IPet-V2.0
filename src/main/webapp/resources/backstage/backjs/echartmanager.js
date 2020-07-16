$(function() {
    listUrl='/vedioadmin/listvedio';
    function getclickList() {
        $.getJSON(listUrl, function(data) {
            if (data.success) {
                var vedioList = data.vedioList;
                vedioList.map(function(item, index) {
                    return item.click
                });

            }
        });
    }
})