package Dao;

import Model.Config;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection getConnection(){
        try {
            String serverName = Config.map.get("db_server");
            String databaseName = Config.map.get("db_name");
            String url = "jdbc:sqlserver://" + serverName + ";databaseName=" + databaseName + ";";
            String username = Config.map.get("db_username");
            String password = Config.map.get("db_password");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Create an SSLContext with the TrustManager that trusts all certificates
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        // Set the default SSL socket factory with the custom SSLContext
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Disable hostname verification
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        System.out.println(getConnection().getCatalog());
    }
}
