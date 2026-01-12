import React, { useState } from "react";
import "./StudentEntry.css";

const StudentEntry = () => {
  const [student, setStudent] = useState({
    studentId: "",
    name: "",
    email: "",
    course: "",
    gender: ""
  });

  const handleChange = (e) => {
    setStudent({
      ...student,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(student); // for testing
    alert("Student details submitted!");
  };

  return (
    <div className="student-container">
      <h2>Student Entry Form</h2>

      <form className="student-form" onSubmit={handleSubmit}>
        <label>Student ID</label>
        <input
          type="text"
          name="studentId"
          value={student.studentId}
          onChange={handleChange}
          placeholder="Enter Student ID"
          required
        />

        <label>Student Name</label>
        <input
          type="text"
          name="name"
          value={student.name}
          onChange={handleChange}
          placeholder="Enter Student Name"
          required
        />

        <label>Email</label>
        <input
          type="email"
          name="email"
          value={student.email}
          onChange={handleChange}
          placeholder="Enter Email"
          required
        />

        <label>Course</label>
        <select
          name="course"
          value={student.course}
          onChange={handleChange}
          required
        >
          <option value="">Select Course</option>
          <option value="Software Engineering">Software Engineering</option>
          <option value="Data Science">Data Science</option>
          <option value="Cyber Security">Cyber Security</option>
        </select>

        <label>Gender</label>
        <div className="radio-group">
          <label>
            <input
              type="radio"
              name="gender"
              value="Male"
              onChange={handleChange}
            />
            Male
          </label>

          <label>
            <input
              type="radio"
              name="gender"
              value="Female"
              onChange={handleChange}
            />
            Female
          </label>
        </div>

        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default StudentEntry;
