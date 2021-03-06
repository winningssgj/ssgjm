<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/header.jsp" %>
<!DOCTYPE html >
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>产品信息</title>
    <meta name="author" content="卫宁实施工具">
    <link rel="stylesheet" href="<%=basePath%>resources/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>resources/bootstrap/css/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>resources/bootstrap/css/bootstrapValidator.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>resources/bootstrap/css/toastr.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>resources/assets/css/common.css"/>
    <link rel="shortcut icon" href="<%=basePath%>resources/img/logo.ico"/>
</head>
<style>
    .table-align {
        table-layout: fixed; /* 只有定义了表格的布局算法为fixed，下面td的定义才能起作用。 */
        font-size: 12px;
    }

    .table-align tr td:nth-child(3) {
        word-break: keep-all; /* 不换行 */
        white-space: nowrap; /* 不换行 */
        overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
        text-overflow: ellipsis; /* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
        font-size: 12px;
    }

    .table-align tr td:nth-child(4) {
        word-break: keep-all; /* 不换行 */
        white-space: nowrap; /* 不换行 */
        overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
        text-overflow: ellipsis; /* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
        font-size: 12px;
    }

    .table-align tr td:nth-child(5) {
        word-break: keep-all; /* 不换行 */
        white-space: nowrap; /* 不换行 */
        overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
        text-overflow: ellipsis; /* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
        font-size: 12px;
    }

</style>
<body>
<div class="container-fluid">
    <div class="row" id="queryScope">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 div-style text-center">
                <label class="col-sm-4 form-text" for="productName">产品名称：</label>
                <input type="text" class="input-style" id="productName"/>
            </div>
            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 div-style text-center">
                <label class="col-sm-4 form-text" for="productCode">产品编码：</label>
                <input type="text" class="input-style" id="productCode"/>
            </div>
            <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 div-style text-center">
                <button type="button" class="btn btn-success btn-sm" id="query">
                    <span class="glyphicon glyphicon-search"></span>
                    查询
                </button>
            </div>

        </div>
    </div>
    <!--表格区域  -->
    <table id="infoTable" class="table-align"></table>
    <!--toolbar区域  -->
    <div class="btn-group" id="btntoolbar">
        <%--  <button id="add" class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"></span>增加</button>--%>
    </div>
    <!--模态框  -->
    <div class="modal fade" id="productInfoModal" tabindex="-1" role="dialog" aria-labelledby="productInfoModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width:450px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">产品明细数据</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row">
                            <form class="form-horizontal col-lg-6 col-md-6 col-sm-6 col-xs-6" role="form"
                                  id="productInfoForm">
                                <div class="form-group" id="codeDiv" style="display:none;">
                                    <label class="col-sm-3 control-label">产品代码</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" readonly="readonly" id="code"
                                               name="code">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">产品名称</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="cpmc" name="name"
                                               placeholder="请输入产品名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">产品条线</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="cptxName" name="cptxName"
                                               data-provide="typeahead"
                                               placeholder="请输入产品条线">
                                        <input type="hidden" id="cptx" name="cptx">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">功能描述</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" id="gnms" name="gnms"
                                               placeholder="请输入功能描述">
                                    </div>
                                </div>

                                <input type="hidden" name="id" id="id">
                                <input type="reset" style="display:none;"/>
                                <div class="col-sm-8 text-center">
                                    <button class="btn btn-primary" id="saveProduct" type="submit">保存</button>
                                    <button class="btn btn-danger" data-dismiss="modal">取消</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/bootstrap-table.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/toastr.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/bootstrap/js/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/auth/module/productInfo.js"></script>
</html>