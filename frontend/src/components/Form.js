import axios from "axios";
import { useState } from "react";

export default function Form() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [date, setDate] = useState(new Date().toISOString().split("T")[0]);
  const [time, setTime] = useState("00:00");
  const [duration, setDuration] = useState(0);
  const [area, setArea] = useState("");
  const [building, setBuilding] = useState("");
  const [type, setType] = useState("");
  const [size, setSize] = useState(0);
  const [equipment, setEquipment] = useState("");
  const [description, setDescription] = useState("");
  const [notes, setNotes] = useState("");

  const reserve = (e) => {
    e.preventDefault();

    const dd = {
      date: date,
      time: time,
      duration: duration,
      area: area,
      building: building,
      type: type,
      size: size,
      equipment: equipment.split(",").map((e) => e.trim()),
      description: description,
      notes: notes,
    };
    console.log(dd);

    axios({
      method: "post",
      url: "http://localhost:8080/api/v1/romres",
      auth: {
        username: username,
        password: password,
      },
      data: dd,
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
        Romtype:
        <input
          type="text"
          name="type"
          value={type}
          onChange={(e) => setType(e.target.value)}
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
      <label>
        Utstyr (skill med komma):
        <input
          type="text"
          name="equipment"
          value={equipment}
          onChange={(e) => setEquipment(e.target.value)}
        />
      </label>
      <label>
        Beskrivelse:
        <input
          type="text"
          name="description"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
      </label>
      <label>
        Merknader:
        <input
          type="text"
          name="notes"
          value={notes}
          onChange={(e) => setNotes(e.target.value)}
        />
      </label>
      <input type="submit" value="Submit" />
    </form>
  );
}
