<#include "/ftl/layout.ftl"/>
<@layout>
<style>
    .form-group label{
        padding-left: 4px;
    }
</style>


<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2 style="padding-left: 10px">票券管理</h2>
    </div>
    <div class="col-lg-2">
    </div>
</div>
<div class="wrapper wrapper-content animated fadeInLeft" style=" padding: 0" >
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins" >
                <div class="ibox-content">
                    <h3 class="m-t-none m-b">数据查询</h3>
                    <form role="form" class="form-horizontal" id="form-search" >
                        <div class="form-group" style="padding-left: 15px">
                            <div class="row">
                                <div class="col-md-2">
                                    <label class="control-label">姓名:</label>
                                    <input type="text" class="form-control" id="t-tickedId" name="jdusername">
                                </div>
                                <div class="col-md-2">
                                    <label class="control-label" >邮箱：</label>
                                    <input type="text" class="form-control" id="t-ticketName" name="remark">
                                </div>
                                <#--<div class="col-md-2">
                                    <label class="control-label">券状态：</label>
                                    <select class="form-control" id="t-usableStatus" name="usableStatus">
                                        <option value="">全部</option>
                                        <option value="1">可用</option>
                                        <option value="0">已发放完</option>
                                    </select>
                                </div>-->
                                <div class="col-md-2">
                                    <label class="control-label" >Cookie：</label>
                                    <input type="text" class="form-control" id="t-ADMCode" name="jdcookie">
                                </div>
                                <#--<div class="col-md-2">
                                    <label class="control-label">成本中心：</label>
                                    <input type="text" class="form-control" id="t-costCenter" name="costCenter">
                                </div>-->
                                <div class="col-md-2">
                                    <a class="btn btn-sm btn-primary" style="margin-top: 25px" href="#" onclick="searchpage()"><strong>搜索</strong></a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="ibox-content" >
                    <div class="table-responsive">
                        <div id="toolbar">
                            <div class="btn-group tooltip-demo">
                                <a class="btn btn-primary" data-toggle="tooltip" data-placement="top" title="新建票券" onclick="addTickets()" href="#" >
                                    <i class="glyphicon glyphicon-plus"></i>&nbsp;新建
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="jqGrid_wrapper" style="width: 96%">
                        <table id="dataTables"></table>
                        <div id="item-table-pager"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Custom and plugin javascript -->
<script src="/js/inspinia.js"></script>
<script src="/js/plugins/pace/pace.min.js"></script>

<script src="/js/qrCode/jquery.qrcode.js"></script>
<script src="/js/qrCode/utf.js"></script>

<script>
    $(document).ready(function(){
        var jqTable=$('#dataTables').jqGrid({
//            datatype: "local",
            url: '/test',
            page:1,
            colNames:['姓名','邮箱','Cookie'],
            colModel: [
                {name:'jdusername',index:'jdusername',sortable:false,width:90},
                {name:'remark',index:'remark',sortable:false},
                {name:'jdcookie',index:'jdcookie',sortable:false}],
            pager:"#item-table-pager",
            rownumbers: true,
            multiselect: false,
            multiboxonly: true,
            caption: '数据列表',
            height: "auto",
            autowidth: true,
            gridComplete:function(){
                var _jqGrid = $(this);
                var ids = _jqGrid.jqGrid('getDataIDs');
                $.each(ids, function (i, id) {
//                    var html="<button class='btn btn-primary btn-sm' title='删除' onclick='deletTicketPonit("+id+")' >删除</button>";
//                    _jqGrid.jqGrid('setCell', id, 'caozuo', html, {}, {
//                        title: ''
//                    });
                });
            }
        });
    });

    function searchpage(){
        var paras=['ticketId','ticketName','ticketStatus','admCode','holderId','usableStatus'];
        var postData = $("#dataTables").jqGrid("getGridParam", "postData");
        $.each(paras, function (k, v) {delete postData[v]; });
        $("#dataTables").setGridParam({
            url: '/admin/queryTicketPage.jhtm',
            datatype: 'json',
            page: 1,
            postData: $.getFormData("#form-search")
        }).trigger("reloadGrid");
    }

    function deletTicketPonit(id){
        var valuesDate = $('#dataTables').jqGrid('getRowData', id);
        swal({
            title: "确认删除吗?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            cancelButtonText: "取消",
            closeOnConfirm: false
        }, function () {
             var url="/admin/ticketDel?accesstoken="+$("#_accesstoken").val()+"&authkey="+$("#_authkey").val();
            $.get(url+'&id='+valuesDate.ticketId,function(resault){
                if(resault.code=='1000'){
                    swal("已删除", "", "success");
                    $("#dataTables").setGridParam({
                        url: '/admin/queryTicketPage.jhtm?accesstoken='+$("#_accesstoken").val()+"&authkey="+$("#_authkey").val(),
                        datatype: 'json',
                        page: 1,
                        postData: $.getFormData("#form-search")
                    }).trigger("reloadGrid");
                }else{
                    swal("操作出错了！", resault.msg);
                }
            })
        });
    }
    function addTickets() {
        window.location.href="/addpage";
    }

</script>
</@layout>