<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/header.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>院方确认</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/mui.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/service.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>resources/zoomify/css/zoomify.min.css"/>
    <link rel="stylesheet" type="text/css" href="//at.alicdn.com/t/font_575705_kyiw62yjuy6nu3di.css"/>
    <link rel="shortcut icon" href="<%=basePath%>resources/img/logo.ico"/>
    <%--photoSwipe--%>
    <link rel="stylesheet prefetch" href="<%=basePath%>resources/photoSwipe/css/photoswipe.css">
    <link rel="stylesheet prefetch" href="<%=basePath%>resources/photoSwipe/css/default-skin.css">
    <script src="<%=basePath%>resources/photoSwipe/js/photoswipe.min.js"></script>
    <script src="<%=basePath%>resources/photoSwipe/js/photoswipe-ui-default.min.js"></script>
    <script src="<%=basePath%>resources/photoSwipe/js/myPhoto.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<div class="wrap">
    <div class="wrap-header">
        <div class="header" style="margin: 0;padding: 10px;">
            <div style="text-align: left;line-height: 20px">
                <c:if test="${siteQuestionInfo.processStatus != 1}">

                    <a href="<%=basePath%>mobile/log/list.do?sourceId=${siteQuestionInfo.id}">更新记录</a>

                </c:if>

            </div>

            <input id="userId" type="hidden" name="userId" value="${userId}">
            <input id="serialNo" type="hidden" name="serialNo" value="${serialNo}">
            <input id="openId" type="hidden" name="openId" value="${openId}">
        </div>
    </div>
    <div class="wrap-cnt">
        <div class="column-2 large-list">
            <strong>科室病区</strong>
            <span>${siteQuestionInfo.map.get("deptName")}</span>
        </div>
        <div class="column-2 large-list">
            <strong>系统名称</strong>
            <span>${siteQuestionInfo.map.get("plName")}</span>
        </div>
        <div class="column-2 large-list">
            <strong>问题标题</strong>
            <span>${siteQuestionInfo.menuName.trim()}</span>
        </div>
        <div class="column-2 large-list">
            <strong>问题描述</strong>
            <span>${siteQuestionInfo.questionDesc}</span>
        </div>
        <div class="column-2 large-list">
            <strong>优先等级</strong>
            <span class="levelA"></span>
        </div>
        <div class="column-2 large-list">
            <strong>影像资料</strong>
        </div>
        <div class="column-2 large-list">
					<span class="large-img" id="imgs">
                    <c:if test="${siteQuestionInfo.imgPath !=null && siteQuestionInfo.imgPath !=''}">
                        <c:forEach var="img" items="${siteQuestionInfo.imgs}" varStatus="status">
                            <img style="width: 88px;height: 92px;" src="<%=Constants.FTP_SHARE_FLODER%>${img}"
                                 onclick="toBigPic(${status.index})">
                        </c:forEach>
                    </c:if>
					</span>
        </div>
        <div class="column-2 large-list">
            <strong>解决方案</strong>
            <span>${siteQuestionInfo.solutionResult}</span>
        </div>
        <div class="space"></div>
        <c:if test="${siteQuestionInfo.processStatus == 4}">
            <div class="column-2 large-list">
                <strong>院方意见</strong>
            </div>
            <div class="column-2 large-list">
					<span>
						<textarea id="suggestDesc">${siteQuestionInfo.suggest}</textarea>
					</span>
            </div>
        </c:if>
    </div>
    <jsp:include page="../enterprise/img.jsp"></jsp:include>
    <c:if test="${siteQuestionInfo.processStatus == 4}">
        <div class="wrap-foot large-btn">
            <a href="#" id="refuse"><span>打回</span></a>
            <a href="#" id="complete"><span>确认完成</span></a>
        </div>
    </c:if>
</div>
<script type="text/javascript" src="<%=basePath%>resources/mobile/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/mobile/js/mui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/zoomify/js/zoomify.min.js" charset="utf-8"></script>
<script type="text/javascript">

    $(function () {
        $('#refuse').click(function () {
            mui.confirm('', '确认是否打回该工作', function (obj) {
                if (obj.index === 0) {
                    console.log('取消');
                } else if (obj.index === 1) {
                    var suggestDesc = $("#suggestDesc").val();
                    if (suggestDesc != null && suggestDesc != '') {
                        status(6, suggestDesc);//院方退回
                    } else {
                        console.log('确认');
                        mui.toast('请输入您的意见!', {duration: 2000, type: 'div'});
                    }

                }
            })
        });
        $('#complete').click(function () {
            status(5, null);//院方确认完成
        });
        setListLevel(${siteQuestionInfo.priority});

        $('.zoomify').zoomify();
    });


    function status(val, suggest) {
        var id = ${siteQuestionInfo.id};
        var userId = ${userId};
        var serialNo = ${serialNo};
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/mobile/tempSiteQuestion/processStatus.do",
            data: {id: id, val: val, suggest: suggest, serialNo: serialNo, userId: userId},
            dataType: "json",
            error: function (request) {
                mui.toast('服务端错误，或网络不稳定，本次操作被终止。', {duration: 'long', type: 'div'})
            },
            success: function () {
                if (val == 5) {
                    mui.toast('确认成功', {duration: 600, type: 'div'});
                    setTimeout(location.href = "<%=basePath%>/mobile/tempSiteQuestion/laodList.do?processStatus=" + val + "&openId=" + $("#openId").val()
                        + "&userId=" + $("#userId").val() + "&serialNo=" + $("#serialNo").val(), 600);
                } else {
                    mui.toast('打回成功', {duration: 600, type: 'div'});
                    setTimeout(location.href = "<%=basePath%>/mobile/tempSiteQuestion/laodList.do?processStatus=4" + "&openId=" + $("#openId").val()
                        + "&userId=" + $("#userId").val() + "&serialNo=" + $("#serialNo").val(), 600);
                }
            }
        });
    }


    function setListLevel(val) {
        var valStr = convertTypeToInt(val);
        $(".levelA").text(valStr);
    }

    /**
     *优先级转换 从码值到ABCD
     *@param val
     *@returns {string}
     */
    function convertTypeToInt(val) {
        var valStr = '';
        switch (val) {
            case 1:
                valStr = 'A'
                break;
            case 2:
                valStr = 'B'
                break;
            case 3:
                valStr = 'C'
                break;
            case 4:
                valStr = 'D'
                break;
            default:
                'D'
        }
        return valStr;
    }

</script>
</body>
</html>

