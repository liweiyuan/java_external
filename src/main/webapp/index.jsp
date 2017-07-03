<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/2/21
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <title>AgentExternalTest</title>
</head>
<body>
<p>跨应用测试页面：<a href="<%=contextPath%>/externalTest.jsp"><%=contextPath%>/externalTest.jsp</a></p>
<p>分位值测试页面：<a href="<%=contextPath%>/quantileTest.jsp"><%=contextPath%>/quantileTest.jsp</a></p>
<p>分位值测试页面(两个Webaction)：<a href="<%=contextPath%>/quantileTest2.jsp"><%=contextPath%>/quantileTest2.jsp</a></p>


<p>play框架跨应用<a href="<%=contextPath%>/play"><%=contextPath%>/play</a></p>



<p>验证HttpClientPrintWriter无flush的情况<a href="<%=contextPath%>/test_no_flush"><%=contextPath%>/test_no_flush</a></p>
<p>验证HttpClientPrintWriter有flush的情况<a href="<%=contextPath%>/test_flush"><%=contextPath%>/test_flush</a></p>
<p>验证HttpClientPrintWriter返回jsp頁面<a href="<%=contextPath%>/test_jsp"><%=contextPath%>/test_jsp</a></p>

<p>验证urlConnectionPrintWriter无flush的情况<a href="<%=contextPath%>/url_no_flush"><%=contextPath%>/url_no_flush</a></p>
<p>验证urlConnectionPrintWriter有flush的情况<a href="<%=contextPath%>/url_flush"><%=contextPath%>/url_flush</a></p>
<p>验证urlConnectionPrintWriter返回jsp頁面<a href="<%=contextPath%>/url_jsp"><%=contextPath%>/url_jsp</a></p>


<p>验证HttpClient4空指针</p>
<p>验证HttpClient4空指针异常<a href="<%=contextPath%>/post_test"><%=contextPath%>/post_test(HttpClient4.0)</a></p>

<p>
    Https调用<br/>
    <span>说明:调用https需要1.通过java生成密钥，2在Tomcat端配置https访问(conf/server.xml),3.在应用中开启https访问的请求(已注释)</span>
</p>
<p>验证Https调用<a href="<%=contextPath%>/https_test"><%=contextPath%>/https_test</a></p>
</body>
</html>
