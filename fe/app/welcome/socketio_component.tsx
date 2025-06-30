import { useState, useEffect } from "react";
import { io, Socket } from "socket.io-client";

interface AvailabilityScalar {
  val: string;
}

interface MetricSubscription {
  metricName: string;
  workcellFilter: string | null;
  robotFilter: string | null;
  workflowFilter: string | null;
}

export function SocketIoComponent() {
  const [availabilityScalar, setAvailabilityScalar] = useState<AvailabilityScalar | null>(null)
  const [isConnected, setIsConnected] = useState<boolean>(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    // const beUrl = "http://localhost:8878";
    // const beUrl = "http://localhost:5000";
    const beUrl = "http://localhost:8091";

    let socket: Socket | null = null;

    try {
      socket = io(beUrl);

      socket.on("connect", () => {
        setIsConnected(true);
        setError(null);
      });

      socket.on("disconnect", (reason: Socket.DisconnectReason) => {
        setIsConnected(false);
        setError(`Disconnected: ${reason}`);
      });

      socket.on("connect_error", (err: Error) => {
        setIsConnected(false);
        setError(`SocketIO connection error: ${err.message || "Unknown error"}`);
      });

      socket.on("availability_scalar", (metric: AvailabilityScalar) => {
        setAvailabilityScalar(metric);
      });

      socket.emit("metric_subscription", { "metricName": "availability_scalar" }, (metric: AvailabilityScalar) => {
        setAvailabilityScalar(metric);
      });

    } catch (e: any) {
      console.error("Error initializing socket:", e);
      setError(`Initialization error: ${e.message}`);
    }

    return () => {
      if (socket) {
        console.log("Cleaning up socket connection...");
        socket.disconnect();
        socket.off("connect");
        socket.off("disconnect");
        socket.off("connect_error");
        socket.off("availability_scalar");
      }
    }

  }, []);

  return (
    <div className="max-w-[300px] w-full space-y-6 px-4">
      <nav className="rounded-3xl border border-gray-200 p-6 dark:border-gray-700 space-y-4">
        <p className="leading-6 text-gray-700 dark:text-gray-200 text-center">
          Streaming data from SocketIO: 
        </p>
        {availabilityScalar ? (
          <p className="leading-6 text-green-700 dark:text-green-200 text-center">
            {availabilityScalar.val}
          </p>
        ) : (
          <p className="text-gray-500 italic">No messages received yet. Waiting for server...</p>
        )}
      </nav>
    </div>
  );
}

export default SocketIoComponent;
