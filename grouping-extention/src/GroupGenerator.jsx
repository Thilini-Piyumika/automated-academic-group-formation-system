import React, { useState } from "react";
import "./GroupGenerator.css";
import nibmLogo from "./NIBMLogo.png";

const GroupGenerator = () => {

  const [file, setFile] = useState(null);
  const [size, setSize] = useState(4);
  const [threshold, setThreshold] = useState(75);
  const [w1, setW1] = useState(0.4);
  const [w2, setW2] = useState(0.4);
  const [w3, setW3] = useState(0.2);
  const [strategy, setStrategy] = useState(2);
  const [detailed, setDetailed] = useState(true);
  const [groups, setGroups] = useState([]);

  const handleGenerate = async () => {

    if (!file) {
      alert("Please upload Excel file.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("size", size);
    formData.append("threshold", threshold);
    formData.append("w1", w1);
    formData.append("w2", w2);
    formData.append("w3", w3);
    formData.append("strategy", strategy);

    const response = await fetch("http://localhost:8080/api/upload", {
      method: "POST",
      body: formData
    });

    const data = await response.json();
    setGroups(data);
  };

  const handleDownload = async () => {
    const response = await fetch(
      `http://localhost:8080/api/export?detailed=${detailed}`
    );

    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;
    a.download = "Groups.xlsx";
    a.click();
  };

  const getCategoryClass = (category) => {
    if (category === "BEST") return "best-row";
    if (category === "AVERAGE") return "average-row";
    if (category === "NEEDS_SUPPORT") return "worst-row";
    return "";
  };

  return (
    <div className="container">

      {/* HEADER CONTAINER */}
      <div className="header-container">
        <div className="header">
          <img src={nibmLogo} alt="NIBM Logo" className="logo" />
          <div>
            <h2 className="title">
              GroupMe - Academic Group Formation Tool
            </h2>
            <p className="subtitle">
              NIBM – Higher National Diploma in Software Engineering
            </p>
          </div>
        </div>
      </div>

      <div className="layout">

        {/* LEFT SIDE SETTINGS */}
        <div className="sidebar">

          <h3>⚙️ Group Settings</h3>

          <h4>1. Upload Student Excel File</h4>
          <input type="file" accept=".xlsx"
            onChange={(e) => setFile(e.target.files[0])}
          />

          <div className="excel-format-box">
            <h4>Required Excel File Format</h4>
            <p><strong>Column Order (Strictly Follow This Order):</strong></p>

            <ul>
              <li><strong>Column 1:</strong> Student ID (Text)</li>
              <li><strong>Column 2:</strong> Attendance (%) – Number (0–100)</li>
              <li><strong>Column 3:</strong> Current GPA – Number (0–4)</li>
              <li><strong>Column 4:</strong> Previous Year GPA – Number (0–4)</li>
            </ul>

            <small>
              ⚠ First row must contain headers. Do not change column order.
            </small>
          </div>

          <h4>2. Group Size</h4>
          <input type="number"
            value={size}
            onChange={(e) => setSize(e.target.value)}
          />

          <h4>3. Attendance Threshold (%)</h4>
          <input type="number"
            value={threshold}
            onChange={(e) => setThreshold(e.target.value)}
          />
          <small>
            Students below this percentage will be placed in the Review Group.
          </small>

          <hr />

          <h4>4. Readiness Score Weights</h4>

          <label>Attendance Weight (W1)</label>
          <input type="number" step="0.1"
            value={w1}
            onChange={(e) => setW1(e.target.value)}
          />

          <label>Current GPA Weight (W2)</label>
          <input type="number" step="0.1"
            value={w2}
            onChange={(e) => setW2(e.target.value)}
          />

          <label>Previous GPA Weight (W3)</label>
          <input type="number" step="0.1"
            value={w3}
            onChange={(e) => setW3(e.target.value)}
          />

          <small>
            Readiness Score = Weighted GPA of Attendance, Current GPA & Previous GPA.
          </small>

          <hr />

          <h4>5. Grouping Strategy</h4>

          <select value={strategy}
            onChange={(e) => setStrategy(e.target.value)}
          >
            <option value="1">Strategy 1 – Best-Best</option>
            <option value="2">Strategy 2 – Best-Average</option>
            <option value="3">Strategy 3 – Mixed</option>
          </select>

          <small>
            Strategy 2 Order: BEST+AVG → BEST+WORST → AVG+WORST → Fill Remaining
          </small>

          <hr />

          <label className="checkbox-label">
            <input
              type="checkbox"
              checked={detailed}
              onChange={() => setDetailed(!detailed)}
            />
            Detailed Excel Export
          </label>

          <div className="button-row">
            <button onClick={handleGenerate}>Generate Groups</button>
            <button onClick={handleDownload}>Download Excel</button>
          </div>

        </div>

        {/* RIGHT SIDE PREVIEW */}
        <div className="preview-panel">

          <h3>📊 Group Preview</h3>

          <div className="preview-box">

            {groups.length === 0 && (
              <p>No groups generated yet.</p>
            )}

            {groups.map((group, index) => {

              const isLast = index === groups.length - 1;
              const groupName = isLast
                ? "Review Group"
                : `Group ${index + 1}`;

              return (
                <div key={index} className="group-block">

                  <h4>{groupName}</h4>

                  <table>
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Attendance</th>
                        <th>GPA</th>
                        <th>Prev GPA</th>
                        <th>Score</th>
                        <th>Category</th>
                      </tr>
                    </thead>
                    <tbody>
                      {group.map((student, i) => (
                        <tr key={i} className={getCategoryClass(student.category)}>
                          <td>{student.studentId}</td>
                          <td>{student.attendance}</td>
                          <td>{student.thisYearGpa}</td>
                          <td>{student.previousYearGpa}</td>
                          <td>{student.readinessScore}</td>
                          <td>{student.category}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>

                </div>
              );
            })}

          </div>

        </div>

      </div>
    </div>
  );
};

export default GroupGenerator;
