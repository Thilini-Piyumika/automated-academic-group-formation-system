import React, { useState } from "react";
import GroupSelector from "./Components/GroupSelector";
import GroupList from "./Components/GroupList";

function App() {
  const [groups, setGroups] = useState(null);

  return (
    <div style={{ padding: "20px" }}>
      <h1>Academic Group Formation System</h1>

      <GroupSelector setGroups={setGroups} />

      {groups && <GroupList groups={groups} />}
    </div>
  );
}

export default App;
