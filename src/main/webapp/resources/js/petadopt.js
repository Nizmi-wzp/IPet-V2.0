$(function() {
    $('#submit').click(
        function() {
            alert("1");
            // 创建商品json对象，并从表单里面获取对应的属性值
            var product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#priority').val();
            product.normalPrice = $('#normal-price').val();
            product.promotionPrice = $('#promotion-price').val();
            // 获取选定的商品类别值
            product.productCategory = {
                productCategoryId : $('#category').find('option').not(
                    function() {
                        return !this.selected;
                    }).data('value')
            };
            if (isEdit) product.productId = productId;

            // 获取缩略图文件流
            var thumbnail = $('#small-img')[0].files[0];
            // 生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            formData.append('thumbnail', thumbnail);
            // 遍历商品详情图控件，获取里面的文件流
            $('.detail-img').map(
                function(index, item) {
                    // 判断该控件是否已选择了文件
                    if ($('.detail-img')[index].files.length > 0) {
                        // 将第i个文件流赋值给key为productImgi的表单键值对里
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                });
            // 将product json对象转成字符流保存至表单对象key为productStr的的键值对里
            formData.append('productStr', JSON.stringify(product));
            // 获取表单里输入的验证码
            var verifyCodeActual = $('#j_captcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);

            alert("3");
            // 将数据提交至后台处理相关操作
            $.ajax({
                url : productPostUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        $.toast('提交成功！');
                        $('#captcha_img').click();
                    } else {
                        $.toast('提交失败！');
                        $('#captcha_img').click();
                    }
                }
            });
        });

})