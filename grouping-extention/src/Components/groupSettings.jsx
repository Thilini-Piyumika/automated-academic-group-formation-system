import React, { useState } from "react";
import "../Styles/groupSettings.css";

const GroupSettings = () => {
  const [groupingStrategy, setGroupingStrategy] = useState("");
  const [selectedCourse, setSelectedCourse] = useState("");
  const [selectedBatch, setSelectedBatch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [selectedModules, setSelectedModules] = useState([]);

  const [attendanceWeight, setAttendanceWeight] = useState(0);
  const [prevGpaWeight, setPrevGpaWeight] = useState(0);
  const [currentGpaWeight, setCurrentGpaWeight] = useState(0);

  const [attendanceThreshold, setAttendanceThreshold] = useState("");
  const [leadershipPreference, setLeadershipPreference] = useState("");
  const [allowRepetition, setAllowRepetition] = useState(false);
  const [numGroups, setNumGroups] = useState("");
  const [eachStudentForGroups, setEachStudentForGroups] = useState("");

  const courseModules = {
    "Diploma in Software Engineering": [
      "Programming Fundamentals",
      "Web Development",
      "Database Systems",
      "OOP with Java"
    ],
    "Diploma in Network Engineering": [
      "Networking Basics",
      "Routing & Switching",
      "Network Security",
      "Linux Administration"
    ],
    "Diploma in Information System": [
      "Information Systems",
      "Business Analysis",
      "Data Management",
      "IT Project Management"
    ]
  };

  const totalWeight =
    attendanceWeight + prevGpaWeight + currentGpaWeight;

  return (
    <div className="dashboard-layout">
      {/* Sidebar */}
      <aside className="sidebar">
        <div className="sidebar-container">
          <h1>Group Setting</h1>
          <p>Student Groups</p>

          <nav className="navigation">
            <ul>
              <li><a href="#">Dashboard</a></li>
              <li><a href="#">Students</a></li>
              <li className="active"><a href="#">Group Settings</a></li>
              <li><a href="#">Generated Groups</a></li>
              <li><a href="#">History</a></li>
            </ul>
          </nav>
        </div>

        <div className="sidebar-footer">
          <div className="user-role">T</div>
          <div className="user-info">
            <p>Thilini Piyumika</p>
            <span>Lecturer</span>
          </div>
        </div>
      </aside>

      {/* Main content */}
      <main className="main-content">
        <div className="header">
          <h1>Group Formation System</h1>
          <p>
            Configure how student groups are generated using different
            grouping strategies.
          </p>
        </div>

        <div className="content-grid">
          {/* left card */}
          <div className="card">
            <h3>Configuration Settings</h3>

            <div className="form-group">
              <label>Select Course</label>
              <select
                value={selectedCourse}
                onChange={(e) => setSelectedCourse(e.target.value)}
              >
                <option value="">Select course</option>
                <option value="Diploma in Software Engineering">
                  Diploma in Software Engineering
                </option>
                <option value="Diploma in Network Engineering">
                  Diploma in Network Engineering
                </option>
                <option value="Diploma in Information System">
                  Diploma in Information System
                </option>
              </select>
            </div>

            <div className="form-group">
              <label>Select Batch</label>
              <select
                value={selectedBatch}
                onChange={(e) => setSelectedBatch(e.target.value)}
              >
                <option value="">Select batch</option>
                <option value="24.2F">24.2F</option>
                <option value="24.2P">24.2P</option>
                <option value="25.1F">25.1F</option>
                <option value="25.1P">25.1P</option>
              </select>
            </div>

            <button
              className="btn-primary"
              disabled={!selectedCourse}
              onClick={() => setShowModal(true)}
            >
              Select Attendance Modules
            </button>

            <div className="readiness">
              <h4>Readiness Score</h4>

              <div className="form-row">
                <label>Weight for Attendance</label>
                <input
                  type="number"
                  value={attendanceWeight}
                  onChange={(e) =>
                    setAttendanceWeight(Number(e.target.value))
                  }
                /> %
              </div>

              <div className="form-row">
                <label>Weight for Previous Year GPA</label>
                <input
                  type="number"
                  value={prevGpaWeight}
                  onChange={(e) =>
                    setPrevGpaWeight(Number(e.target.value))
                  }
                /> %
              </div>

              <div className="form-row">
                <label>Weight for Current Year GPA</label>
                <input
                  type="number"
                  value={currentGpaWeight}
                  onChange={(e) =>
                    setCurrentGpaWeight(Number(e.target.value))
                  }
                /> %
              </div>

              <p className="total-weight">
                Total Weight: {totalWeight}%{" "}
                {totalWeight !== 100 && "⚠ Must equal 100%"}
              </p>
            </div>

            <div className="form-group">
              <label>Attendance Threshold (%)</label>
              <input
                type="number"
                value={attendanceThreshold}
                onChange={(e) =>
                  setAttendanceThreshold(e.target.value)
                }
                placeholder="Enter threshold (e.g., 75)"
              />
            </div>

            <div className="form-group">
              <label>Leadership Preference</label>
              <input
                type="checkbox"
                value={leadershipPreference}
                onChange={(e) =>
                  setLeadershipPreference(e.target.value)
                }
                placeholder="e.g. High GPA students as leaders"
              />
            </div>

            <div className="form-group checkbox">
              <label>
                <input
                  type="checkbox"
                  checked={allowRepetition}
                  onChange={(e) =>
                    setAllowRepetition(e.target.checked)
                  }
                />
                Allow Repetition
              </label>
            </div>

            
          {/* Right card */}
          <div className="card">
            <h3>Group Generation</h3>

            <div className="form-group">
              <label>Select Grouping Strategy</label>
              <select
                value={groupingStrategy}
                onChange={(e) =>
                  setGroupingStrategy(e.target.value)
                }
              >
                <option value="">Choose a strategy</option>
                <option value="best-best">Best - Best</option>
                <option value="best-need-attention">
                  Best - Need Attention
                </option>
                <option value="best-average">
                  Best - Average
                </option>
                <option value="average-need-attention">
                  Average - Need Attention
                </option>
              </select>
            </div>
            <div className="form-group">
              <label>Number of Groups</label>
              <input
                type="number"
                value={numGroups}
                onChange={(e) =>
                  setNumGroups(e.target.value)
                }
              />
            </div>

            <div className="form-group">
              <label>Students per Group</label>
              <input
                type="number"
                value={eachStudentForGroups}
                onChange={(e) =>
                  setEachStudentForGroups(e.target.value)
                }
              />
            </div>
          </div>


            <button className="btn-generate">
              🚀 Generate Groups
            </button>
          </div>
        </div>
      </main>

      {/* modal */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Attendance Modules</h3>
            <p>{selectedCourse}</p>

            {courseModules[selectedCourse]?.map((module) => (
              <div key={module} className="checkbox-item">
                <label>
                  <input
                    type="checkbox"
                    checked={selectedModules.includes(module)}
                    onChange={(e) => {
                      if (e.target.checked) {
                        setSelectedModules([
                          ...selectedModules,
                          module
                        ]);
                      } else {
                        setSelectedModules(
                          selectedModules.filter(
                            (m) => m !== module
                          )
                        );
                      }
                    }}
                  />
                  {module}
                </label>
              </div>
            ))}

            <div className="modal-actions">
              <button
                className="btn-primary"
                onClick={() => setShowModal(false)}
              >
                Done
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default GroupSettings;
