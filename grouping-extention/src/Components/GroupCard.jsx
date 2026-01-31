import React from "react";

function GroupCard({ groupName, students }) {
  return (
    <div
      style={{
        border: "1px solid #ccc",
        padding: "15px",
        marginBottom: "20px",
        borderRadius: "8px"
      }}
    >
      <h2>{groupName}</h2>

      <table width="100%" border="1" cellPadding="8">
        <thead>
          <tr>
            <th>ID</th>
            <th>Attendance</th>
            <th>Current GPA</th>
            <th>Previous GPA</th>
            <th>Category</th>
            <th>Leadership</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student) => (
            <tr key={student.id}>
              <td>{student.id}</td>
              <td>{student.attendance}</td>
              <td>{student.currentGpa}</td>
              <td>{student.previousGpa}</td>
              <td>{student.category}</td>
              <td>{student.leadershipPreference}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default GroupCard;
