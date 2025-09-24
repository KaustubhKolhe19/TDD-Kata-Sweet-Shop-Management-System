import React, { useState } from 'react';
import axiosInstance from '../api';
import { useNavigate } from 'react-router-dom';

const Register = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('user');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.post('/auth/register', { username, email, password, role });
      setSuccess('Registration successful! Redirecting to login...');
      setTimeout(() => navigate('/login'), 2000);
    } catch (err) {
      setError('Registration failed');
    }
  };

  return (
    <div className="col-md-6 offset-md-3">
      <h2>Register</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}
      <form onSubmit={handleRegister}>
        <div className="mb-3">
          <label>Username</label>
          <input value={username} className="form-control" onChange={e => setUsername(e.target.value)} required />
        </div>
        <div className="mb-3">
          <label>Email</label>
          <input type="email" value={email} className="form-control" onChange={e => setEmail(e.target.value)} required />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input type="password" value={password} className="form-control" onChange={e => setPassword(e.target.value)} required />
        </div>
        <div className="mb-3">
          <label>Role</label>
          <select className="form-select" value={role} onChange={e => setRole(e.target.value)}>
            <option value="user">User</option>
            <option value="admin">Admin</option>
          </select>
        </div>
        <button className="btn btn-primary" type="submit">Register</button>
      </form>
    </div>
  );
};

export default Register;
