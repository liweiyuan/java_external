<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <title>quantileTest</title>
    <script>
        function check(count, check) {
            if (document.getElementById(count).value.length == 0) {
                document.getElementById(check).innerHTML = '请输入quantileSetting或count！';
                return false;
            } else {
                return true;
            }
        }

    </script>
</head>
<body>
<h3>调用quantileTestServlet2</h3>
<form name="httpclient_form2" onsubmit="return check('count','check');" method="get"
      action="qGet2">
    <p>调用的次数：
        <input type="text" id="count2" name="count2" size="10"
               onfocus="document.getElementById('check').innerHTML = '';">
    </p>
    <p>分位数设置，请输入数组：
        <input type="text" id="quantileSetting2" name="quantileSetting2" size="50"
               onfocus="document.getElementById('check').innerHTML = '';">
    </p>
    <input type="submit" value="执行">

</form>
<a id="check" style="color:#FF0000"></a>
<br>
<%= application.getServerInfo() %>
</body>
</html>
