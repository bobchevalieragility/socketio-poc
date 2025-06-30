package com.agilityrobotics.be;

// import java.net.URI;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
// import io.socket.client.IO;
// import io.socket.client.Socket;
// import io.socket.emitter.Emitter;
// import com.corundumstudio.socketio.store.RedissonStoreFactory;
// import org.redisson.Redisson;
// import org.redisson.config.Config;

@Component
public class BeConfiguration {
	private SocketIOServer server;
	// private Socket socket;

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setHostname("0.0.0.0");
		config.setPort(8878);
		// config.setEnableCors(true);
		SocketConfig socketConfig = new SocketConfig();
		socketConfig.setReuseAddress(true);
		config.setSocketConfig(socketConfig);

		// Config redissonConfig = new Config();
        // // redissonConfig.useSingleServer().setAddress("redis://redis:6379/0");
        // redissonConfig.useSingleServer().setAddress("redis://redis:6379");
        // Redisson redisson = (Redisson)Redisson.create(redissonConfig);
        // RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory(redisson);
        // config.setStoreFactory(redisStoreFactory);

		server = new SocketIOServer(config);
		server.start();

		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				System.out.println("new user connected with socket " + client.getSessionId());
			}
		});

		server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				client.getNamespace().getAllClients().stream().forEach(data -> {
					System.out.println("user disconnected " + data.getSessionId().toString());
				});
			}
		});

		return server;
	}

	@PreDestroy
	public void stopSocketIOServer() {
		server.stop();
	}

	// @Bean
	// public Socket socketIoSocket() {
	// 	System.out.println("BFC creating Socket Bean");
	// 	// URI uri = URI.create("http://localhost:8878");
	// 	URI uri = URI.create("http://localhost:5000");
	// 	socket = IO.socket(uri);

	// 	socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
	// 		@Override
	// 		public void call(Object... args) {
	// 			System.out.println("BFC socketIO EVENT_CONNECT!");
	// 		}
	// 	});

	// 	socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
	// 		@Override
	// 		public void call(Object... args) {
	// 			System.out.println("BFC socketIO EVENT_CONNECT_ERROR!");
	// 		}
	// 	});

	// 	socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
	// 		@Override
	// 		public void call(Object... args) {
	// 			System.out.println("BFC socketIO EVENT_DISCONNECT!");
	// 		}
	// 	});

	// 	this.socket.on("metric_subscription", new Emitter.Listener() {
	// 		@Override
	// 		public void call(Object... args) {
	// 			System.out.println("BFC got a metric_subscription event!");
	// 		}
	// 	});

	// 	socket.connect();

	// 	return socket;
	// }

	// @PreDestroy
	// public void disconnectSocket() {
	// 	if (socket != null) {
	// 		socket.disconnect();
	// 	}
	// }

}
