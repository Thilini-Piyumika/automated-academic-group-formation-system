import React from "react";
import GroupCard from "./GroupCard";

function GroupList({ groups }) {
  return (
    <div>
      {Object.keys(groups).map((groupName) => (
        <GroupCard
          key={groupName}
          groupName={groupName}
          students={groups[groupName]}
        />
      ))}
    </div>
  );
}

export default GroupList;
