import React, { useState } from "react";

function GroupSelector({ setGroups }) {
  const [strategy, setStrategy] = useState("BEST_AVERAGE");

  const generateGroups = async () => {
    const response = await fetch(
      `http://localhost:8080/api/groups?strategy=${strategy}`
    );
    const data = await response.json();
    setGroups(data);
  };

  return (
    <div style={{ marginBottom: "20px" }}>
      <label style={{ marginRight: "10px" }}>
        Grouping Strategy:
      </label>

      <select
        value={strategy}
        onChange={(e) => setStrategy(e.target.value)}
      >
        <option value="BEST_BEST">Best + Best</option>
        <option value="BEST_AVERAGE">Best + Average</option>
        <option value="AVERAGE_NEEDS_SUPPORT">
          Average + Needs Support
        </option>
        <option value="NEEDS_SUPPORT_ONLY">
          Needs Support Only
        </option>
      </select>

      <button
        onClick={generateGroups}
        style={{ marginLeft: "10px" }}
      >
        Generate Groups
      </button>
    </div>
  );
}

export default GroupSelector;
