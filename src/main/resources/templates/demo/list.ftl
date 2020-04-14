<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JD_Cookie列表</title>
</head>
<body>
<div >
    <a href="/cookie/addCookie"><h1>增加Cookie</h1></a>
</div>
<hr/>
<table border="1">
    <tr>
        <td align="center" bgcolor="#faebd7">姓名</td>
        <td align="center" bgcolor="#faebd7">邮箱</td>
        <td align="center" bgcolor="#faebd7">操作</td>
    </tr>
    <#list list as cookie>
        <tr>
            <th width="150px" align="center">${cookie.jdusername}</th>
            <th width="150px" align="center">${cookie.remark}</th>
            <th>
<#--                <a href="/cookie/deleteCookie/${cookie.id}" onclick="del_sure()">删除</a>&nbsp;&nbsp;-->
                <a href="javascript:if(confirm('确认删除吗?'))window.location='/cookie/deleteCookie/${cookie.id}'" >删除</a>&nbsp;&nbsp;
<#--                <a href="javascript:del(${cookie.id})">删除</a>-->
                <a href="/cookie/updatePage/${cookie.id}">修改</a>
            </th>
        </tr>
    </#list>

</table>
<!--
    发送一个delete请求的规范
    1:method="post"
    2:传递一个参数_method delete put
-->
<form method="post" id="delForm">
    <input type="hidden" name="_method" value="DELETE"/>
</form>
<script>
    function del(id) {
        var form = document.getElementById("delForm");
        form.setAttribute("action","/cookie/deleteCookie/"+id)
        form.submit();

    }

    function del_sure(){
        var gnl=confirm("你真的确定要删除吗?");
        if (gnl==true){
            return true;
        }
        else{
            return false;
        }
    }
</script>
</body>
</html>

