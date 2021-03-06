<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/header.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>站点安装登记</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/mui.min.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/common.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/mobile/css/enterprise.css" />
		<link rel="stylesheet" type="text/css" href="//at.alicdn.com/t/font_575705_9raiir53539.css"/>
		<script src="<%=basePath%>resources/mobile/js/jquery-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>resources/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>resources/mobile/js/ims.js" type="text/javascript"></script>
	</head>
	<body>
	<div class="wrap">

		<div class="mui-content gray">
		    <!--header-->
			<%--<div class="header">--%>
				<%--<span class="mui-icon mui-icon-arrowleft" onclick="history.go(-1)"></span>--%>
				<%--<div>站点安装登记</div>--%>
				<%--<span class="mui-icon mui-icon-more"></span>--%>
			<%--</div>--%>
			<c:forEach var="vwr" items="${installList}">

			<div class="site-install" onclick="detail(${vwr.id});">
				<div class="part-one">
					<div class="left">
						<span>${vwr.deptName}</span>
					</div>
					<div class="right">
						<p>完成站点数：<span><i>${vwr.map.get('installed')}</i>/<i>${vwr.num}</i></span></p>
						<p>安装人：<span>${vwr.map.get("yhmc")}</span></p>
					</div>
				</div>
				<div class="part-item">
					<div class="part-two">
						<div>
							<span class="software">软件</span>
						</div>
						<div>${vwr.pdName}</div>
					</div>
					<div class="part-two">
						<div>
							<span class="hardware">硬件</span>
						</div>
						<div>${vwr.hdName}</div>
					</div>
				</div>
			</div>
			</c:forEach>


		</div>

		<!--底部菜单-->
		<div class="wrap-foot">
			<div  onclick="openIndexPage()">
				<i class="iconfont icon-task"></i>
				任务
			</div>
			<div  class="active"  onclick="siteLoad();">
				<i class="iconfont icon-site"></i>
				站点
			</div>
			<div onclick="onlineLoad();">
				<i class="iconfont icon-upload"></i>
				上传
			</div>
			<div onclick="userCenter();">
				<i class="iconfont icon-wo"></i>
				我的
			</div>
		</div>

	</div>
		<script type="text/javascript">
            $(function () {
                IMS.menuTab();
            })
			function detail(id){
				location.href="<%=basePath%>mobile/siteInstall/addAndUpdate.do?id="+id+"&serialNo=${hospcode}&userId=${work_num}";
			}
		</script>
		<%@ include file="/commons/footer.jsp" %>
	</body>
</html>
