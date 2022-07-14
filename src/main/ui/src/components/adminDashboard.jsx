import React from "react";

import Table from 'react-bootstrap/Table'
import 'bootstrap/dist/css/bootstrap.min.css';
import { formatDate } from "../service/utils";

function AdminDashboard({data}) {

    const now = new Date();
    const lastWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 7);
    const lastFortnight = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 14);

    const entriesThisWeek = data && data.filter(row => row.intakeDate > formatDate(lastWeek)).length
    const entriesLastWeek = data && data.filter(row => row.intakeDate <= formatDate(lastWeek) && row.intakeDate > formatDate(lastFortnight)).length
    const users = [...new Set(data.map(row => row.userId))].sort()

    const avgCalories = (userId) => {
        const totalCalories = data.filter(row => row.userId === userId)
        .filter(row => row.intakeDate > formatDate(lastWeek))
        .map(row => row.calorieValue)
        .reduce((partialSum, num) => partialSum + num, 0)
        return Math.round(totalCalories/7)
    }

    const columns = [
        { name: 'User ID'},
        { name: 'Average Calorie Intake Last Week'},
    ]

    return (
        <div>
            <h3>Admin Dashboard</h3>
            <br></br>
            <div>
                <b>Number Of Entries Added This Week : </b>{entriesThisWeek}
            </div>
            <br></br>
            <div>
            <b>Number Of Entries Added Last Week : </b>{entriesLastWeek}
            </div>
            <br></br>
            <Table responsive>
                <thead>
                    <tr>{data && columns.map(column => <th>{column.name}</th>)}</tr>
                </thead>
                <tbody>
                    {users.map(user => 
                    <tr>
                        <td>
                            {user}
                        </td>
                        <td>
                            {avgCalories(user)}
                        </td>
                    </tr>)}
                </tbody>
            </Table>
        </div>
    )
}

export default AdminDashboard