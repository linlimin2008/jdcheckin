<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增</title>
</head>
<script>
    function show(){
        var email = document.getElementById("remark");
        if( !email.value.match("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$")){
            alert("邮箱格式错误，请更正");
            return;
        }
    }
</script>
<body>
<!--新增的时候提交action="/user"这样一个请求-->
<form action="/cookie/addUser" method="post">
    姓名:<input name="jdusername" id="jdname"/>
    <br/>
    邮箱:<input name="remark" id="remark" onblur="show()" />
    <br/>
    Cookie:<input name="jdcookie" id="cookie"/>
    <br/>
    <input type="submit"/>
</form>
<a href="/cookie/user"><h5>返回主页</h5></a>
</body>
</html>