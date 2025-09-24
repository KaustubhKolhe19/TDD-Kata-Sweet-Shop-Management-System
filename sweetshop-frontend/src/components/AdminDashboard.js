import React, { useEffect, useState } from 'react';
import axiosInstance from '../api';

const AdminDashboard = () => {
  const [sweets, setSweets] = useState([]);
  const [form, setForm] = useState({ id: null, name: '', category: '', price: '', quantity: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  const fetchSweets = async () => {
    setLoading(true);
    try {
      const res = await axiosInstance.get('/sweets');
      setSweets(res.data);
    } catch {
      setError('Failed to fetch sweets');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSweets();
  }, []);

  const handleChange = e => {
    setForm({...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      if (form.id) {
        await axiosInstance.put(`/sweets/${form.id}`, {
          name: form.name,
          category: form.category,
          price: parseFloat(form.price),
          quantity: parseInt(form.quantity),
        });
      } else {
        await axiosInstance.post('/sweets', {
          name: form.name,
          category: form.category,
          price: parseFloat(form.price),
          quantity: parseInt(form.quantity),
        });
      }
      setForm({ id: null, name: '', category: '', price: '', quantity: '' });
      fetchSweets();
    } catch {
      alert('Operation failed');
    }
  };

  const handleEdit = sweet => {
    setForm({
      id: sweet.id,
      name: sweet.name,
      category: sweet.category,
      price: sweet.price,
      quantity: sweet.quantity,
    });
  };

  const handleDelete = async id => {
    if (window.confirm('Delete this sweet?')) {
      try {
        await axiosInstance.delete(`/sweets/${id}`);
        fetchSweets();
      } catch {
        alert('Delete failed');
      }
    }
  };

  return (
    <div>
      <h2>Admin Dashboard</h2>
 
      <form onSubmit={handleSubmit} className="mb-4">
        <h4>{form.id ? 'Edit Sweet' : 'Add New Sweet'}</h4>
        <div className="mb-3">
          <label>Name</label>
          <input name="name" value={form.name} onChange={handleChange} className="form-control" required/>
        </div>
        <div className="mb-3">
          <label>Category</label>
          <input name="category" value={form.category} onChange={handleChange} className="form-control" required/>
        </div>
        <div className="mb-3">
          <label>Price</label>
          <input name="price" type="number" step="0.01" value={form.price} onChange={handleChange} className="form-control" required/>
        </div>
        <div className="mb-3">
          <label>Quantity</label>
          <input name="quantity" type="number" value={form.quantity} onChange={handleChange} className="form-control" required/>
        </div>
        <button type="submit" className="btn btn-primary">{form.id ? 'Update' : 'Add'}</button>
        {form.id && <button type="button" className="btn btn-secondary ms-2" onClick={() => setForm({id:null,name:'',category:'',price:'',quantity:''})}>Cancel</button>}
      </form>

      {loading && <p>Loading sweets...</p>}
      {error && <p className="text-danger">{error}</p>}

      <table className="table table-striped">
        <thead>
          <tr>
            <th>Name</th><th>Category</th><th>Price</th><th>Quantity</th><th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {sweets.map(sweet => (
            <tr key={sweet.id}>
              <td>{sweet.name}</td>
              <td>{sweet.category}</td>
              <td>${sweet.price.toFixed(2)}</td>
              <td>{sweet.quantity}</td>
              <td>
                <button className="btn btn-sm btn-warning me-2" onClick={() => handleEdit(sweet)}>Edit</button>
                <button className="btn btn-sm btn-danger" onClick={() => handleDelete(sweet.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminDashboard;
