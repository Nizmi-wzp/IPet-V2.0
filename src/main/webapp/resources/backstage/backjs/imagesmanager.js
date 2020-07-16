$(function() {
    var listUrl = '/imagesadmin/listimages';//获取页面json数据
    var listPageUrl='/imagesadmin/getimageslistbyId?pageIndex='+0+'&pageSize='+999//分页数据
    var deleteUrl = '';//获取页面删除数据；
    var addUrl = '/imagesadmin/addimages';//获取页面添加数据;
    var currentpage=0;
   // $.toast({text: "加载成功！！！", textAlign: 'center', position: 'mid-center', hideAfter: 2000});
    getList();
    //分页显示图片
    function getList() {
        $.getJSON(
            listUrl,
            function (data) {
                if (data.success) {
                    // $.toast('欢迎回来');
                    var dataList = data.imagesList;
                    $('ul#photo-control').html('');
                    var tempHtml = '';
                    dataList.map(function (item, index) {
                        tempHtml += ''
                            +'<li class="imglist thumbnail">'
                            +'<img src="'
                            +item.images
                            +'" class="petimage">'
                            +'<button class="btn btn-danger">撤销</button>'
                            +'<button class="btn btn-primary">替换</button>'
                            +'</li>';
                    });
                    $('ul#photo-control').append(tempHtml);

                    var pagehtml='';
                    pagehtml+=''
                    +'<ul class="pagination">'
                    +'<li><a href="#">首页</a></li>'
                    +'<li><a href="#">&laquo;</a></li>'
                    +'<li class="active"><a href="">'
                    +currentpage
                    +'</a></li>'
                    +'<li><a href="#">'
                    +currentpage+1
                    +'</a></li>'
                    +'<li><a href="#">'
                    +currentpage+2
                    +'</a></li>'
                    +'<li><a href="#">'
                    +currentpage+3
                    +'</a></li>'
                    +'<li><a href="#">'
                    +currentpage+4
                    +'</a></li>'
                    +'<li><a href="#">...</a></li>'
                    +'<li><a href="#">&raquo;</a></li>'
                    +'<li><a href="#">最后一页</a></li>'
                    +'</ul>'
                    $('#page').append(pagehtml);
                }

            });
    }
















//看大图
    $('ul#photo-control').on('mouseover','img.petimage',function (e) {
        $(this).css({"width": "160px","height": "120px"});
    })

    $('ul#photo-control').on('mouseout','img.petimage',function (e) {
        $(this).css({"width": "80px","height": "60px"});
    })
   /* $(".petimage").click(function () {
        $(".petimage").css("border","5px solid red");
    }),hover(function () {
        $(".petimage").css("border:","5px solid red");
    })*/




       $('#search').click(function () {
           var words=$("#words").val();
           $('ul#photo-control').html('');
           //发送查询关键字
           $.ajax({
               url:listPageUrl,
               type: 'GET',
               data: {words: words},
               dataType: 'json',
               success: function (data) {
                   if (data.success) {
                       var dataList = data.imagesList;
                       $('ul#photo-control').html('');
                       var tempHtml = '';
                       dataList.map(function (item, index) {
                           tempHtml += ''
                               +'<li class="imglist" data-id="'
                               +item.imagesId
                               +'">'
                               +'<img src="'
                               +item.images
                               +'" class="petimage">'
                               +'<button class="btn btn-danger">撤销</button>'
                               +'<button class="btn btn-primary">替换</button>'
                               +'</li>';
                                   });
                                   $('ul#photo-control').append(tempHtml);
                   } else {
                       alert("failed");
                       //$.toast('删除失败!');
                   }
               }

           });
       });




   //添加上传图片
    $("div.addimages").click(function (e) {
        var tempHtml =
            '<li class="imglist">'
            +'<input type="file" id="uploadimg">'
            +'<button class="btn btn-danger">撤销</button>'
            +'<button class="btn btn-primary">替换</button>'
            +'</li>';
        $('ul#photo-control').append(tempHtml);
    });


    $('ul#photo-control').on('click','#uploadimg',function (e) {
        //获取文件流
        var image=$('this')[0].files[0];
        var name=$(this)[0].files[0].name;
        alert(name);
       /* var formData=new FormData();
        formData.append('image',image);*/
        alert("6");

    })
    $('div#submit').click(function (e) {
        var img=$('#small-img')[0].files[0];

        var images = {};
       //  $('ul#photo-control').on('click','li',function(){
       //      images.imagesId = $(this).attr('data-id');
       // })
       //  alert(images.imagesId);

        var formData=new FormData();
        formData.append('img',img);
        alert(formData.get('img').name);
        $.ajax({
            url : addUrl,
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    alert('提交成功！');
                    //$('#captcha_img').click();
                } else {
                    alert('提交失败！'+data.errMsg);
                   // $('#captcha_img').click();
                }
            }
        })
      alert('提 交')
    })

         //清空列表
        //ajax异步生成新列表


   $('#typesarch').change(
       function (e) {
           var option = this.options[this.options.selectedIndex].text
           alert(option)
           $.ajax({
               url:listPageUrl,
               type: 'GET',
               data: {option:option},
               dataType: 'json',
               success: function (data) {
                   //data[i].id == undefined ? "" : data[i].id;
                   if (data.success) {
                       alert("success");
                       // $.toast('欢迎回来');
                       var dataList = data.imagesList;
                       $('ul#photo-control').html('');
                       var tempHtml = '';
                       dataList.map(function (item, index) {
                           tempHtml += ''
                               +'<li class="imglist">'
                               +'<img src="'
                               +item.images
                               +'" class="petimage">'
                               +'<button class="btn btn-danger">撤销</button>'
                               +'<button class="btn btn-primary">替换</button>'
                               +'</li>';
                       });
                       $('ul#photo-control').append(tempHtml);
                   } else {
                       alert("failed");
                       //$.toast('删除失败!');
                   }
               }

           });

       });






});