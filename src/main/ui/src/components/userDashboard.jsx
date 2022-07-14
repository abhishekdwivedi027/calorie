import React from "react";

import Table from 'react-bootstrap/Table'
import 'bootstrap/dist/css/bootstrap.min.css';

function UserDashboard({data, userId, calorieLimit = 2100}) {

    const userData = data && data.filter(row => row.userId === userId);
    let calorieIntakeSumByDate = [];
    userData.reduce(function(res, value) {
    if (!res[value.intakeDate]) {
        res[value.intakeDate] = { intakeDate: value.intakeDate, calorieValue: 0 };
        calorieIntakeSumByDate.push(res[value.intakeDate])
    }
    res[value.intakeDate].calorieValue += value.calorieValue;
    return calorieIntakeSumByDate;
    }, {});

    const excessCalorieIntake = calorieIntakeSumByDate.filter(row => row.calorieValue > calorieLimit).sort()

    const columns = [
        { field: 'intakeDate', name: 'Date (YYYY-MM-DD)'},
        { field: 'calorieValue', name: 'Calorie Intake for the Day'},
    ]

    return (
        <div>
            <h3>User Dashboard</h3>
            <br></br>
            <div>
                <b>Number Of Days with Excess Calorie Intake : </b>{excessCalorieIntake.length}
            </div>
            <br></br>
            <div>
                <b>Calorie Limit for User (user id {userId}): </b>{calorieLimit}
            </div>
            <br></br>
            <Table responsive>
                <thead>
                    <tr>{excessCalorieIntake[0] && columns.map(column => <th>{column.name}</th>)}</tr>
                </thead>
                <tbody>
                    {excessCalorieIntake.map(row => 
                    <tr>
                        {columns.map(column => <td>
                            {row[column.field]}
                        </td>)}
                    </tr>)}
                </tbody>
            </Table>
        </div>
    )
}

export default UserDashboard