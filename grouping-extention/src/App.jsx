
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./Components/auth/Login";
import GroupSettings from "./Components/groupSettings";
function App() {
   return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/groupsettings" element={<GroupSettings />} />
      </Routes>
    </Router>
  );
}

export default App;
