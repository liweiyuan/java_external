<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <title>AgentTest_External_01_IndexPage</title>
    <script>
        function checkUrl(url, check) {
            if (document.getElementById(url).value.length == 0) {
                document.getElementById(check).innerHTML = '请输入调用的url！';
                return false;
            } else {
                return true;
            }
        }
        function checkUploadFile(uploadUrl, check) {
            if (document.getElementById(uploadUrl).value == "") {
                document.getElementById(check).innerHTML = '请选择上传的文件！';
                return false;
            } else {
                return true;
            }
        }
        function checkDownloadFile(downloadUrl, check) {
            if (document.getElementById(downloadUrl).value == "") {
                document.getElementById(check).innerHTML = '请输入需下载文件的url！';
                return false;
            } else {
                return true;
            }
        }
        function httpServer(status) {
            document.forms['httpServer_form'].action = "httpserver?httpServerStatus=" + status;
            document.forms['httpServer_form'].submit();

        }
    </script>
</head>
<body>
<table>
    <tr>
        <td valign="top">
            <h3>Commons Http3.0</h3>
            <form name="commonshttp_form" onsubmit="return checkUrl('commonshttpGetUrl','check2');" method="get"
                  action="commonshttpGet">
                <p>commonhttp url：
                    <input type="text" id="commonshttpGetUrl" name="commonshttpGetUrl" size="50"
                           onfocus="document.getElementById('check2').innerHTML = '';">
                    <input type="submit" value="get connect">

                </p>
            </form>
            <form name="upload_form" onsubmit="return checkUploadFile('commonshttpUpload_file','check2');"
                  action="commonshttpUpload" method="POST"
                  enctype="multipart/form-data">
                <p>upload file：
                    <input type="file" id="commonshttpUpload_file" name="commonshttpUpload_file"
                           onclick="document.getElementById('check2').innerHTML = '';">
                    <input type="submit" value="upload"/>
                </p>
            </form>
            <form name="download_form" onsubmit="return checkDownloadFile('commonshttpDownload_url','check2');"
                  action="commonshttpDownload" method="POST">
                <p>download file:
                    <input type="text" id="commonshttpDownload_url" name="commonshttpDownload_url" size="50">
                    <input type="submit" value="download"/>
                </p>
            </form>
            <a id="check2" style="color:#FF0000"></a>

            <p>get 请求：<a href="<%=contextPath%>/commonshttpGet"><%=contextPath%>/commonshttpGet</a></p>
            <p>post请求：<a href="<%=contextPath%>/commonshttpPost"><%=contextPath%>/commonshttpPost</a></p>
            <p>上传文件：<a href="<%=contextPath%>/commonshttpUpload"><%=contextPath%>/commonshttpUpload</a></p>
            <p>下载文件：<a href="<%=contextPath%>/commonshttpDownload"><%=contextPath%>/commonshttpDownload</a></p>
            <p>表单提交：<a href="<%=contextPath%>/commonshttpParam"><%=contextPath%>/commonshttpParam</a></p>
            <p>901 UnknownHostException：<a href="<%=contextPath%>/commonshttperr901"><%=contextPath%>
                /commonshttperr901</a>
            </p>
            <%--<p>902 ConnectException：<a href="<%=contextPath%>/commonshttperr902"><%=contextPath%>/commonshttperr902</a></p>--%>
            <p>903 SocketTimeoutException：<a href="<%=contextPath%>/commonshttperr903"><%=contextPath%>
                /commonshttperr903</a><a
                    style="color:#FF0000"> start httpserver first</a></p>
            <%--<p>904 ClientProtocolException：<a href="<%=contextPath%>/commonshttperr904"><%=contextPath%>/commonshttperr904</a></p>--%>
            <p>908 SSLHandshakeException：<a href="<%=contextPath%>/commonshttperr908"><%=contextPath%>
                /commonshttperr908</a>
            </p>
            <p>404 HTTP Error：<a href="<%=contextPath%>/commonshttperr404"><%=contextPath%>/commonshttperr404</a></p>
            <p>500 HTTP Error：<a href="<%=contextPath%>/commonshttperr500"><%=contextPath%>/commonshttperr500</a></p>
            <br>
            <br>
            <form name="httpServer_form" method="post">
                <p>httpServer:
                    connectSleeptime <input type="text" id="httpserver_connectSleeptime"
                                            name="httpserver_connectSleeptime" value="6000" size="3">
                    responseSleeptime <input type="text" id="httpserver_responseSleeptime"
                                             name="httpserver_responseSleeptime" value="60000" size="3">
                    <input type="button" value="start" onclick="httpServer(1)"/>
                    <input type="submit" value="stop" onclick="httpServer(0)"/>
                </p>
            </form>
        </td>
        <td valign="top">
            <h3>HttpClient4.0</h3>
            <form name="httpclient_form" onsubmit="return checkUrl('httpclientGetUrl','check3');" method="get"
                  action="httpclientGet">
                <p>HttpClient url：
                    <input type="text" id="httpclientGetUrl" name="httpclientGetUrl" size="50"
                           onfocus="document.getElementById('check3').innerHTML = '';">
                    <input type="submit" value="get connect">

                </p>
            </form>
            <form name="upload_form" onsubmit="return checkUploadFile('httpclientUpload_file','check3');"
                  action="httpclientUpload" method="POST"
                  enctype="multipart/form-data">
                <p>upload file：
                    <input type="file" id="httpclientUpload_file" name="httpclientUpload_file"
                           onclick="document.getElementById('check3').innerHTML = '';">
                    <input type="submit" value="upload"/>
                </p>
            </form>
            <form name="download_form" onsubmit="return checkDownloadFile('httpclientDownload_url','check3');"
                  action="httpclientDownload" method="POST">
                <p>download file:
                    <input type="text" id="httpclientDownload_url" name="httpclientDownload_url" size="50">
                    <input type="submit" value="download"/>
                </p>
            </form>
            <a id="check3" style="color:#FF0000"></a>

            <p>get 请求：<a href="<%=contextPath%>/httpclientGet"><%=contextPath%>/httpclientGet</a></p>
            <p>post请求：<a href="<%=contextPath%>/httpclientPost"><%=contextPath%>/httpclientPost</a></p>
            <p>上传文件：<a href="<%=contextPath%>/httpclientUpload"><%=contextPath%>/httpclientUpload</a></p>
            <p>下载文件：<a href="<%=contextPath%>/httpclientDownload"><%=contextPath%>/httpclientDownload</a></p>
            <p>表单提交：<a href="<%=contextPath%>/httpclientParam"><%=contextPath%>/httpclientParam</a></p>
            <p>901 UnknownHostException：<a href="<%=contextPath%>/httpclienterr901"><%=contextPath%>
                /httpclienterr901</a>
            </p>
            <p>902 ConnectException：<a href="<%=contextPath%>/httpclienterr902"><%=contextPath%>/httpclienterr902</a>
            </p>
            <p>903 SocketTimeoutException：<a href="<%=contextPath%>/httpclienterr903"><%=contextPath%>
                /httpclienterr903</a><a
                    style="color:#FF0000"> start httpserver first</a></p>
            <%--
                        <p>904 ClientProtocolException：<a href="<%=contextPath%>/httpclienterr904"><%=contextPath%>/httpclienterr904</a></p>
            --%>
            <p>908 SSLHandshakeException：<a href="<%=contextPath%>/httpclienterr908"><%=contextPath%>
                /httpclienterr908</a>
            </p>
            <p>404 HTTP Error：<a href="<%=contextPath%>/httpclienterr404"><%=contextPath%>/httpclienterr404</a></p>
            <p>500 HTTP Error：<a href="<%=contextPath%>/httpclienterr500"><%=contextPath%>/httpclienterr500</a></p>

        </td>
        <td valign="top">
            <h3>UrlConnection</h3>

            <form name="url_form" onsubmit="return checkUrl('urlConnectUrl','check');" method="get" action="urlGet">
                <p>urlConnection url：
                    <input type="text" id="urlConnectUrl" name="urlConnectUrl" size="50"
                           onfocus="document.getElementById('check').innerHTML = '';">
                    <br>调用的次数：
                    <input type="text" id="count" name="count" size="10">
                    <input type="submit" value="get connect">

                </p>

            </form>
            <form name="upload_form" onsubmit="return checkUploadFile('urlUpload_file','check');" action="urlUpload"
                  method="POST"
                  enctype="multipart/form-data">
                <p>upload file：
                    <input type="file" id="urlUpload_file" name="urlUpload_file"
                           onclick="document.getElementById('check').innerHTML = '';">
                    <input type="submit" value="upload"/>
                </p>
            </form>
            <form name="download_form" onsubmit="return checkDownloadFile('urlConnectionDownload_url','check');"
                  action="urlDownload" method="POST">
                <p>download file:
                    <input type="text" id="urlConnectionDownload_url" name="urlConnectionDownload_url" size="50">
                    <input type="submit" value="download"/>
                </p>
            </form>
            <a id="check" style="color:#FF0000"></a>
            <a style="color:#FF0000"> ConnectTimeout5000,ReadTimeout 50000</a>
            <p>get 请求：<a href="<%=contextPath%>/urlGet"><%=contextPath%>/urlGet</a></p>
            <p>post请求：<a href="<%=contextPath%>/urlPost"><%=contextPath%>/urlPost</a></p>
            <p>验证getInputStream是否有跨应用：<a href="<%=contextPath%>/urlTest"><%=contextPath%>/urlTest</a></p>
            <p>上传文件：<a href="<%=contextPath%>/urlUpload"><%=contextPath%>/urlUpload</a></p>
            <p>下载文件：<a href="<%=contextPath%>/urlDownload"><%=contextPath%>/urlDownload</a></p>
            <p>表单提交：<a href="<%=contextPath%>/urlParam"><%=contextPath%>/urlParam</a></p>
            <p>900 MalformedURLException：<a href="<%=contextPath%>/urlerr900"><%=contextPath%>/urlerr900</a></p>
            <p>901 UnknownHostException：<a href="<%=contextPath%>/urlerr901"><%=contextPath%>/urlerr901</a></p>
            <p>902 ConnectException：<a href="<%=contextPath%>/urlerr902"><%=contextPath%>/urlerr902</a></p>
            <p>903 SocketTimeoutException：<a href="<%=contextPath%>/urlerr903"><%=contextPath%>/urlerr903</a><a
                    style="color:#FF0000"> start httpserver first</a></p>
            <p>908 SSLHandshakeException：<a href="<%=contextPath%>/urlerr908"><%=contextPath%>/urlerr908</a></p>
            <p>404 HTTP Error：<a href="<%=contextPath%>/urlerr404"><%=contextPath%>/urlerr404</a></p>
            <p>500 HTTP Error：<a href="<%=contextPath%>/urlerr500"><%=contextPath%>/urlerr500</a></p>
            <p>一次action多个外部服务错误：<a href="<%=contextPath%>/urlerrmult"><%=contextPath%>/urlerrmult</a></p>
       </td>
        <td valign="top">
            <h3>AsyncHttpClient</h3>
            <form name="asynchttpclient_form" onsubmit="return checkUrl('asynchttpclientGetUrl','check4');" method="get"
                  action="asynchttpGet">
                <p>AsyncHttpClient url：
                    <input type="text" id="asynchttpclientGetUrl" name="asynchttpclientGetUrl" size="50"
                           onfocus="document.getElementById('check4').innerHTML = '';">
                    <input type="submit" value="get connect">

                </p>
            </form>
            <p>get 请求：<a href="<%=contextPath%>/asynchttpGet"><%=contextPath%>/asynchttpGet</a></p>
            <p>post请求：<a href="<%=contextPath%>/asynchttpPost"><%=contextPath%>/asynchttpPost</a></p>
            <a id="check4" style="color:#FF0000"></a>
        </td>
    </tr>
</table>

<%= application.getServerInfo() %>
</body>
</html>
