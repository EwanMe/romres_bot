import axios from "axios";
import { useState } from "react";

function App() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const reserve = (e) => {
    e.preventDefault();

    axios({
      method: "post",
      url: "http://localhost:8080/api/v1/romres",
      auth: {
        username: username,
        password: password,
      },
    })
      .then((res) => console.log(res))
      .catch((error) => console.log(error.response.data));
  };

  return (
    <div className="App">
      <header className="App-header">RomResBot under construction🔨</header>
      <form onSubmit={(e) => reserve(e)}>
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </label>
        <label>
          Password:
          <input
            type="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <input type="submit" value="Submit" />
      </form>
    </div>
  );
}

export default App;
