package com.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;

/**
 * Created by tingyun on 2017/6/7.
 */
public class HttpsClient {
    public static void main(String[] args) {
       /* new HttpsClient().testIt();*/
    }


    HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                    + session.getPeerHost());
            return true;
        }
    };

    public void testIt() {
        //String https_url = "https://www.baidu.com";
        String https_url="https://localhost:8443/url_no_flush/merpayservice.9fbank.com/quickPay/normal/payConfirm/HttpURLConnection";
        //String https_url="https://localhost:8443/";
        URL url;
        try {
            url = new URL(https_url);
            //忽略证书，信任证书
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);


            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            HttpsURLConnection con1 = (HttpsURLConnection) url.openConnection();
            con1.setDoOutput(true);
            //dumpl all cert info
            print_https_cert(con);
            //dump all the content
            print_content(con);

            //写出操作
            print_writer(con1);

            print_conn(con1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void print_conn(HttpsURLConnection con1) throws IOException {
        con1.connect();
    }

    private synchronized void print_writer(HttpsURLConnection con) throws IOException {

        OutputStream out = con.getOutputStream();
        out.write("name=aaaa".getBytes());
        System.out.println(con.getResponseCode()); // 处理响应
    }

    private void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }
    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    private void print_https_cert(HttpsURLConnection con) {
        if (con != null) {
            try {
                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");
                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }
            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private  synchronized void print_content(HttpsURLConnection con) {
        if (con != null) {
            try {
                System.out.println("****** Content of the URL ********");

                //增加的方法
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                String input;


                //con.connect();
                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();


                //con.setDoOutput(true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
