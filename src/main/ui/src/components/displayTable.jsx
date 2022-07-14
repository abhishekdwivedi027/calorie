import React from 'react';

import Table from 'react-bootstrap/Table'
import 'bootstrap/dist/css/bootstrap.min.css';
import { compareByDate } from '../service/utils';

function Datatable({ data }) {
    const columns = [
        { field: 'userId', headerName: 'User ID'},
        { field: 'foodName', headerName: 'Food Name'},
        { field: 'calorieValue', headerName: 'Calorie Value'},
        { field: 'intakeDate', headerName: 'Intake Date(YYYY-MM-DD)'},
    ]

    data.sort(compareByDate)

    return (
    <Table responsive>
        <thead>
            <tr>{data[0] && columns.map(column => <th>{column.headerName}</th>)}</tr>
        </thead>
        <tbody>
            {data.slice().reverse().map(row => 
            <tr>
                {columns.map(column => <td>
                    {row[column.field]}
                </td>)}
            </tr>)}
        </tbody>
    </Table>
    )
  }

  export default Datatable