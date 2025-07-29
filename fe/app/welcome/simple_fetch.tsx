import { useState, useEffect } from "react";

export function SimpleFetchComponent() {
  const [data, setData] = useState({availability: "empty"})
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const beUrl = "http://localhost:30002";

    fetch(beUrl)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        setData(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(`${err.message} from ${beUrl}`);
        setLoading(false);
      });
  }, []);

  let msg;
  if (loading) {
    msg = "Loading...";
  } else if (error) {
    msg = `Error: ${error}`;
  } else {
    msg = data.availability;
  }

  return (
    <div className="max-w-[300px] w-full space-y-6 px-4">
      <nav className="rounded-3xl border border-gray-200 p-6 dark:border-gray-700 space-y-4">
        <p className="leading-6 text-gray-700 dark:text-gray-200 text-center">
          Simple HTTP query:
        </p>
        <p className="leading-6 text-green-700 dark:text-green-200 text-center">
          {msg}
        </p>
      </nav>
    </div>
  );
}

export default SimpleFetchComponent;
