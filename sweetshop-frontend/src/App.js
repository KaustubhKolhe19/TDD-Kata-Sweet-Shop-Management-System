import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import Login from './components/Login';
import Register from './components/Register';
import SweetsList from './components/SweetsList';
import AdminDashboard from './components/AdminDashboard';
import Navbar from './components/Navbar';

function App() {
   // e.g., "ROLE_ADMIN" or "ROLE_USER"
const userRole = localStorage.getItem('role');
const isLoggedIn = !!localStorage.getItem('token');
const isAdmin = userRole && userRole.includes('ADMIN');

return (
  <Router>
    <Navbar />
    <div className="container mt-4">
      <Routes>
        <Route path="/login" element={!isLoggedIn ? <Login /> : <Navigate to="/" />} />
        <Route path="/register" element={!isLoggedIn ? <Register /> : <Navigate to="/" />} />
        <Route path="/" element={<SweetsList />} />
        <Route path="/admin" element={isLoggedIn && isAdmin ? <AdminDashboard /> : <Navigate to="/" />} />
      </Routes>
    </div>
  </Router>
);
}

export default App;
