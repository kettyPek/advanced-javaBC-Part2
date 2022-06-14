package ajbc.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionBuilder {

	private String url;
	private String serverAdress;
	private String port;
	private String dataBaseName;
	private String serverName;
	private String passWord;
	private String userName;

	public ConnectionBuilder(String propertiesFile) {
		Properties props = new Properties();
		try(FileInputStream fileInputStream = new FileInputStream(propertiesFile);){
			props.load(fileInputStream);
			this.serverAdress = props.getProperty("server_address");
			this.port = props.getProperty("port");
			this.dataBaseName = props.getProperty("db_name");
			this.serverName = props.getProperty("server_name");
			this.passWord = props.getProperty("password");
			this.userName = props.getProperty("user");
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		buildURL();
	}

	public Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url, userName, passWord);
	}

	private void buildURL() {
		this.url = "jdbc:sqlserver://" + serverAdress + ":" + port + ";databaseName=" + dataBaseName + ";servername="
				+ serverName + ";encrypt=false;";
	}

	public String getUrl() {
		return url;
	}

	public String getServerAdress() {
		return serverAdress;
	}

	public String getPort() {
		return port;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public String getServerName() {
		return serverName;
	}

	public String getPassWord() {
		return passWord;
	}

	public String getUserName() {
		return userName;
	}

}
