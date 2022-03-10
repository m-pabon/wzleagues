import React from 'react'

export const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
    <div className="container-fluid">
      <a className="navbar-brand" href="dashboard.html"><i className="fas fa-parachute-box"></i> WZLeagues</a>
      <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
        <span className="navbar-toggler-icon"></span>
      </button>
      <div className="collapse navbar-collapse" id="collapsibleNavbar">
        <ul className="navbar-nav ms-auto">
          <li className="nav-item">
            <a className="nav-link" href="login.html">Login</a>
          </li>
          <li className="nav-item">
            <a className="nav-link" href="register.html">Register</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  )
}
