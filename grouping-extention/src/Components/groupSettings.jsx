import React, { useState } from "react";
import "../Styles/groupSettings.css";

const GroupSettings = () => {
  const [groupingStrategy, setGroupingStrategy] = useState("");

  return (
    <div className="dashboard-layout">
      {/* Sidebar */}
      <aside className="sidebar">
        <div className="sidebar-container">
          <h1>Group Setting</h1>

          <form className="group-form">
            <p>Student Groups</p>
          </form>

          {/* Navigation */}
          <nav className="navigation">
            <ul>
              <li>
                <a href="#">
                  <span>DashboardLayout</span>
                  <span>Dashboard</span>
                </a>
              </li>

              <li>
                <a href="#">
                  <span>DashboardLayout</span>
                  <span>Students</span>
                </a>
              </li>

              <li>
                <a href="#" className="active">
                  <span>DashboardLayout</span>
                  <span>Group Settings</span>
                </a>
              </li>

              <li>
                <a href="#">
                  <span>DashboardLayout</span>
                  <span>Generated Groups</span>
                </a>
              </li>

              <li>
                <a href="#">
                  <span>DashboardLayout</span>
                  <span>History</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>

        {/* User Info */}
        <div className="sidebar-footer">
          <div className="user-role">
            <p>T</p>
          </div>

          <div className="user-info">
            <p>Thilini Piyumika</p>
            <span>Lecturer</span>
          </div>
        </div>
      </aside>

      {/* Main Content */}
      <main className="main-content">
        <section className="group-settings">
          <div className="header">
            <h1>Group Settings</h1>
            <p>
              Configure how student groups are generated using different
              grouping strategies.
            </p>
          </div>









          

          {/* Group Generation Section */}
          <div className="group-generation">
            <h2>Group Generation</h2>

            <div className="form-group">
              <label htmlFor="grouping-strategy">
                Select Grouping Strategy
              </label>

              <select
                id="grouping-strategy"
                value={groupingStrategy}
                onChange={(e) => setGroupingStrategy(e.target.value)}
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
          </div>
        </section>
      </main>
    </div>
  );
};

export default GroupSettings;
