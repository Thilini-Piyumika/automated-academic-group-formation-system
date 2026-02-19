import React, { useState } from "react";

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

  // ======================
  // GENERATE GROUPS
  // ======================
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

  // ======================
  // DOWNLOAD EXCEL
  // ======================
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

  return (
    <div style={styles.container}>

      <h2>Automated Academic Group Formation</h2>

      <div style={styles.card}>

        <label>Upload Excel File</label>
        <input
          type="file"
          accept=".xlsx"
          onChange={(e) => setFile(e.target.files[0])}
        />

        <div style={styles.grid}>

          <input
            type="number"
            value={size}
            onChange={(e) => setSize(e.target.value)}
            placeholder="Group Size"
          />

          <input
            type="number"
            value={threshold}
            onChange={(e) => setThreshold(e.target.value)}
            placeholder="Attendance Threshold (%)"
          />

          <input
            type="number"
            step="0.1"
            value={w1}
            onChange={(e) => setW1(e.target.value)}
            placeholder="Attendance Weight (W1)"
          />

          <input
            type="number"
            step="0.1"
            value={w2}
            onChange={(e) => setW2(e.target.value)}
            placeholder="Current GPA Weight (W2)"
          />

          <input
            type="number"
            step="0.1"
            value={w3}
            onChange={(e) => setW3(e.target.value)}
            placeholder="Previous GPA Weight (W3)"
          />

          <select
            value={strategy}
            onChange={(e) => setStrategy(e.target.value)}
          >
            <option value="1">Strategy 1 – Best-Best</option>
            <option value="2">Strategy 2 – Best-Average</option>
            <option value="3">Strategy 3 – Mixed</option>
          </select>

        </div>

        <div style={{ marginTop: "10px" }}>
          <label>
            <input
              type="checkbox"
              checked={detailed}
              onChange={() => setDetailed(!detailed)}
            />
            Detailed Excel Export
          </label>
        </div>

        <div style={styles.buttonRow}>
          <button onClick={handleGenerate}>Generate Groups</button>
          <button onClick={handleDownload}>Download Excel</button>
        </div>

      </div>

      <h3>Generated Groups (Preview)</h3>

      <div style={styles.preview}>
        <pre>{JSON.stringify(groups, null, 2)}</pre>
      </div>

    </div>
  );
};

const styles = {
  container: {
    maxWidth: "1000px",
    margin: "auto",
    padding: "20px",
    fontFamily: "Arial"
  },
  card: {
    background: "#f5f5f5",
    padding: "20px",
    borderRadius: "10px"
  },
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(3, 1fr)",
    gap: "10px",
    marginTop: "15px"
  },
  buttonRow: {
    marginTop: "15px",
    display: "flex",
    gap: "10px"
  },
  preview: {
    background: "#eee",
    padding: "10px",
    borderRadius: "6px",
    maxHeight: "300px",
    overflow: "auto"
  }
};

export default GroupGenerator;
