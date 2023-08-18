/*******************************************************************************
 *
 *   $$$$$$$\            $$\                     
 *   $$  __$$\           $$ |                     
 *   $$ |  $$ |$$\   $$\ $$ | $$$$$$$\  $$$$$$\   
 *   $$$$$$$  |$$ |  $$ |$$ |$$  _____|$$  __$$\  
 *   $$  ____/ $$ |  $$ |$$ |\$$$$$$\  $$$$$$$$ |  
 *   $$ |      $$ |  $$ |$$ | \____$$\ $$   ____|  
 *   $$ |      \$$$$$$  |$$ |$$$$$$$  |\$$$$$$$\  
 *   \__|       \______/ \__|\_______/  \_______|
 *
 *  Copyright c 2022-2023 TimeStored
 *
 *  Licensed under the Reciprocal Public License RPL-1.5
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/license/rpl-1-5/
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/
 
package com.sqldashboards.dashy;

import com.sqldashboards.shared.JdbcTypes;

/**
 * {@link ServerConfig} is immutable so this builder provides methods for
 * copying an existing config and modifying a few fields. 
 */
public class ServerConfigBuilder {

	private String name;
	private String username;
	private String password;
	private String host;
	private int port = -1;
	private String database;
	private JdbcTypes jdbcType = JdbcTypes.KDB;

//	public ServerConfigBuilder() { }

	public ServerConfigBuilder(ServerConfig sc) { 
		name = sc.getName();
		username = sc.getUsername();
		password = sc.getPassword();
		host = sc.getHost();
		port = sc.getPort();
		database = sc.getDatabase();
		jdbcType = sc.getJdbcType();
	}

	public ServerConfigBuilder setName(String name) {this.name = name;	return this; }
	public ServerConfigBuilder setUsername(String username) { this.username = username; return this; }
	public ServerConfigBuilder setPassword(String password) { this.password = password; return this; }
	public ServerConfigBuilder setDatabase(String database) { this.database = database; return this; }
	public ServerConfigBuilder setJdbcType(JdbcTypes jdbcType) { this.jdbcType = jdbcType; return this; }
	
	public ServerConfigBuilder setFolder(String folder) { 
		
		int p = name.lastIndexOf("/");
		String n = p>-1 ? name.substring(p + 1) : name;
		String f = folder;
		if(!f.equals("") && !f.endsWith("/")) {
			f = f + "/";
		}
		this.name = f + n;
		return this; 
	}
	
	public ServerConfigBuilder setHost(String host) { this.host = host; return this; }

	public ServerConfigBuilder setPort(int port) {
		if(port<0) {
			throw new IllegalArgumentException("Must specify positive port");
		}
		this.port = port; return this;
	}
	
	public ServerConfig build() {
		if(port == -1) {
			port = jdbcType.getDefaultPort();
		}
		return new ServerConfig(host, port, username, password, name, jdbcType, database);
	}
}
