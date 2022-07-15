import React, { Fragment } from 'react'

export const Login = () => {
  return (
    <Fragment>
      <div className="container">
        <h1>
          Sign In
        </h1>
        <p><i className="fas fa-user"></i>
        Sign into your account
        </p>
        <form action="#" className="needs-validation">
          <div className="mb-3 mt-3">
            <label htmlFor="email" className="form-label">Email:</label>
            <input type="email" className="form-control" id="email" placeholder="Enter email" name="email"/>
          </div>
          <div className="mb-3">
            <label htmlFor="pwd" className="form-label">Password:</label>
            <input type="password" className="form-control" id="pwd" placeholder="Enter password" name="pswd"/>
          </div>
          <div className="form-check mb-3">
            <label className="form-check-label">
              <input className="form-check-input" type="checkbox" name="remember"/> Remember me
            </label>
          </div>
          <button type="submit" className="btn btn-success">Submit</button>
        </form>
      </div>
    </Fragment>
  )
}
