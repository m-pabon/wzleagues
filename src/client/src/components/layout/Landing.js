import React from 'react'
import { Link } from 'react-router-dom'

export const Landing = () => {
  return (
    <section className="landing">
    <div className="dark-overlay">
      <div className="landing-inner">
        <h1 className="x-large">Warzone Leagues</h1>
        <p className="lead">Bringing a dedicated ranking system to warzone</p>
        <div className="buttons">
          <Link to="/register" className="btn btn-success">Sign Up</Link>
          <Link to="/login" className="btn">Login</Link>
        </div>
      </div>
    </div>
  </section>
  )
}
