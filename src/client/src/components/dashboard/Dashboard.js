import React, { Fragment } from 'react'

export const Dashboard = () => {
  return (
    <Fragment>
      <div className="container mt-3">
        <h2>Data</h2>
        <table className="table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Activision ID</th>
              <th>Email</th>
              <th>Rank</th>
            </tr>
          </thead>
          <tbody id="table">
          </tbody>
        </table>
        <button type="button" className="btn btn-success">Refresh</button>
      </div>
    </Fragment>
  )
}
