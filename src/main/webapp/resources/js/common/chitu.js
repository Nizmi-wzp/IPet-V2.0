chitu.component('hello', function (self) {
    //属性定义
    self.name = {
        get: function () { return self.root.getAttribute("name"); },
        set: function (value) { self.root.setAttribute("name", value); }
    };
    self.value = {
        get: function () { return self.root.innerText; },
        set: function (value) { self.root.innerText = value; }
    };

    //...

    //事件定义
    self.onclick = function(){

    };

    //...

    //方法定义
    self.setColor = function(color){
        self.root.style.color = color;
    };

    //...

    //控件生成
    self.render = function () {
        //...
        var text = document.createTextNode(self.value);
        return text;
    };

    //控件消亡
    self.dispose = function(){

    };
});
//<hello id="id1" binding="hello1"></hello>