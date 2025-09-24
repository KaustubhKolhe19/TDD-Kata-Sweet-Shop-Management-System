import React, { useEffect, useState } from 'react';
import axiosInstance from '../api';

const SweetsList = () => {
  const [sweets, setSweets] = useState([]);
  const [search, setSearch] = useState('');
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

  const handleSearch = async () => {
    setLoading(true);
    try {
      const res = await axiosInstance.get('/sweets/search', {
        params: { name: search },
      });
      setSweets(res.data);
    } catch {
      setError('Search failed');
    } finally {
      setLoading(false);
    }
  };

  const handlePurchase = async (id) => {
    try {
      await axiosInstance.post(`/sweets/${id}/purchase`);
      fetchSweets();
    } catch {
      alert('Purchase failed (maybe out of stock or login required)');
    }
  };

  useEffect(() => {
    fetchSweets();
  }, []);

  return (
    <div>
      <h2>Available Sweets</h2>
      <div className="input-group mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="Search sweets by name"
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
        <button className="btn btn-primary" onClick={handleSearch}>Search</button>
        <button className="btn btn-secondary" onClick={() => {setSearch(''); fetchSweets();}}>Clear</button>
      </div>
      {loading && <p>Loading sweets...</p>}
      {error && <p className="text-danger">{error}</p>}
      <div className="row">
        {sweets.map(sweet => (
          <div key={sweet.id} className="col-md-4 mb-3">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{sweet.name}</h5>
                <p className="card-text">Category: {sweet.category}</p>
                <p className="card-text">Price: ${sweet.price.toFixed(2)}</p>
                <p className="card-text">Quantity in stock: {sweet.quantity}</p>
                <button
                  className="btn btn-success"
                  disabled={sweet.quantity === 0}
                  onClick={() => handlePurchase(sweet.id)}
                >
                  {sweet.quantity === 0 ? 'Out of Stock' : 'Purchase'}
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SweetsList;
