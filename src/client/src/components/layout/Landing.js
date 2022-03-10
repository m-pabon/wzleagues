import React from 'react'

export const Landing = () => {
  return (
    <section className="landing">
    <div className="dark-overlay">
      <div className="landing-inner">
        <h1 className="x-large">Warzone Leagues</h1>
        <p className="lead">Bringing a dedicated ranking system to warzone</p>
        <div className="buttons">
          <a href="register.html" className="btn btn-success">Sign Up</a>
          <a href="login.html" className="btn">Login</a>
        </div>
      </div>
    </div>
  </section>
  )
}
