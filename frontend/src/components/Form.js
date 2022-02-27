import axios from "axios";
import { useState } from "react";

export default function Form() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [date, setDate] = useState(0);
  const [time, setTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const [area, setArea] = useState("");
  const [building, setBuilding] = useState("");
  const [size, setSize] = useState(0);

  const reserve = (e) => {
    e.preventDefault();

    axios({
      method: "post",
      url: "http://localhost:8080/api/v1/romres",
      auth: {
        username: username,
        password: password,
      },
      data: {
        date: date,
        time: time,
        duration: duration,
        area: area,
        building: building,
        size: size,
      },
    })
      .then((res) => console.log(res))
      .catch((error) => console.log(error.response.data));
  };

  return (
    <form
      onSubmit={(e) => reserve(e)}
      style={{ width: "100px", display: "flex", flexDirection: "column" }}
    >
      <label>
        Brukernavn:
        <input
          type="text"
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </label>
      <label>
        Passord:
        <input
          type="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </label>
      <label>
        Dato:
        <input
          type="date"
          name="date"
          value={date}
          onChange={(e) => setDate(e.target.value)}
        />
      </label>
      <label>
        Tidspunkt:
        <input
          type="time"
          name="time"
          value={time}
          onChange={(e) => setTime(e.target.value)}
        />
      </label>
      <label>
        Varighet (timer):
        <input
          type="number"
          name="duration"
          value={duration}
          max="4"
          onChange={(e) => setDuration(e.target.value)}
        />
      </label>
      <label>
        Område (campus):
        <input
          type="text"
          name="area"
          value={area}
          onChange={(e) => setArea(e.target.value)}
        />
      </label>
      <label>
        Bygning:
        <input
          type="text"
          name="building"
          value={building}
          onChange={(e) => setBuilding(e.target.value)}
        />
      </label>
      <label>
        Antall plasser:
        <input
          type="number"
          name="size"
          value={size}
          onChange={(e) => setSize(e.target.value)}
        />
      </label>
      <input type="submit" value="Submit" />
    </form>
  );
}
