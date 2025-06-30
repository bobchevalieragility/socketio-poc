package com.agilityrobotics.be;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
// import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
// import com.corundumstudio.socketio.listener.DisconnectListener;
// import io.socket.client.Socket;
// import io.socket.emitter.Emitter;

@RestController
@CrossOrigin
public class BeController {

	@Autowired
	private SocketIOServer socketServer;

	BeController(SocketIOServer socketServer) {
		this.socketServer = socketServer;

		// this.socketServer.addConnectListener(onUserConnectWithSocket);
		// this.socketServer.addDisconnectListener(onUserDisconnectWithSocket);

		this.socketServer.addEventListener("metric_subscription", MetricSubscription.class, onSubscriptionEvent);
	}

	// public ConnectListener onUserConnectWithSocket = new ConnectListener() {
	// 	@Override
	// 	public void onConnect(SocketIOClient client) {
	// 		System.out.println("Perform operation on user connect in controller.");
	// 	}
	// };

	// public DisconnectListener onUserDisconnectWithSocket = new DisconnectListener() {
	// 	@Override
	// 	public void onDisconnect(SocketIOClient client) {
	// 		System.out.println("Perform operation on user disconnect in controller.");
	// 	}
	// };

	public DataListener<MetricSubscription> onSubscriptionEvent = new DataListener<>() {
		@Override
		public void onData(SocketIOClient client, MetricSubscription subscription, AckRequest ackRequest) {
			// Store the metric filters on the client, so it can be referenced later
			client.set(subscription.metricName(), subscription);

			// Respond to the client with the current metric value (TODO retrieve it from DB)
			ackRequest.sendAckData(new AvailabilityScalar("0"));
		}
	};

	@GetMapping("/")
	public AvailabilityResponse index() {
		return new AvailabilityResponse("Monkey Pants");
	}

	@PostMapping("/availability")
	public void updateAvailability(@RequestBody String val) {
		this.socketServer.getAllClients().stream().forEach(c -> {
			System.out.println("BFC looping over client: " + c.getSessionId());
			if (c.has("availability_scalar")) {
				c.sendEvent("availability_scalar", new AvailabilityScalar(val + "%"));
			}
		});
		this.socketServer.getBroadcastOperations().sendEvent("availability_scalar", new AvailabilityScalar(val + "%"));
	}

}

// @RestController
// @CrossOrigin
// public class BeController {

// 	@Autowired
// 	private Socket socket;

// 	BeController(Socket socket) {
// 		this.socket = socket;
// 	}

// 	@GetMapping("/")
// 	public AvailabilityResponse index() {
// 		return new AvailabilityResponse("Monkey Pants");
// 	}

// 	@PostMapping("/availability")
// 	public void updateAvailability(@RequestBody String val) {
// 		System.out.println("TODO update availability");
// 	}

// }
