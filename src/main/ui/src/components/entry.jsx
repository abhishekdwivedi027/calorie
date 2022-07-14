import React, {useState} from "react";

import Stack from 'react-bootstrap/Stack'
import Form from 'react-bootstrap/Form'
import DatePicker from "react-datepicker";
import Button from 'react-bootstrap/Button'
import { formatDate, capitalizeFirstLetter } from "../service/utils";
import { Autocomplete, TextField } from "@mui/material";

function Entry({users, selected, suggestions, onChange, onEntry}) {

    const getCalVal = (val) => {
        return { text: val };
    }

    const [date, setDate] = useState(new Date());
    const [food, setFood] = useState("");
    const [calorie, setCalorie] = useState(getCalVal(''));
    const [selectedUser, setSelectedUser] = useState(selected);



    const onApply = () => {
        if (isValid()) {
            onEntry(selectedUser, capitalizeFirstLetter(food), parseInt(calorie.text), formatDate(date))
        }
        onReset()
    }

    const isValid = () => {
        const calVal = calorie.text
        return selectedUser && date <= new Date() && food && typeof food === 'string' 
        && Number.isInteger(parseInt(calVal)) && parseInt(calVal) > 0
    }

    const onReset = () => {
        setSelectedUser(selectedUser)
        setDate(new Date())
        setFood("")
        setCalorie(getCalVal(''))
    }

    const handleChange = (val) => {
        setFood(val)
        onChange(val)
    }

    return (
        <div>
            <br></br>
            <b>Add New Calorie Intake:</b>
            <br></br>
            <br></br>

            <Stack direction="horizontal"  gap={2} className="col-md-4">
            <Form.Label><b>For: </b></Form.Label>
            <Form.Select onChange={(selected) => setSelectedUser(selected.target.value)}>
                {users && users.map(userId => <option value={userId}>{userId}</option>)}
            </Form.Select>
            </Stack>
            <br></br>
            <Stack direction="horizontal"  gap={2} className="col-md-4">
                <Form.Label style={{'margin-right': '0.25rem'}}><b>On:</b></Form.Label>
                <DatePicker selected={date} onChange={(date) => setDate(date)} />
            </Stack>
            <br></br>
            <Stack gap={2} className="col-md-4">
                <div>
                    <Autocomplete
                        freeSolo
                        options={suggestions}
                        inputValue={food}
                        onInputChange={(e, val) => handleChange(val)}
                        renderInput={(params) => <TextField {...params} label="Foods" />}
                    />
                </div>
                <div>
                    <TextField label="Carolie Value" variant="outlined"
                    value={calorie.text}
                    onChange={(e) => setCalorie(getCalVal(e.target.value))}/>
                </div>
            </Stack>
            <br></br>
            <Stack direction="horizontal" gap={2} className="col-md-4">
                <Button variant="primary" onClick={() => onApply()}>Apply</Button>
                <Button variant="outline-primary" onClick={() => onReset()}>Reset</Button>
            </Stack>
        </div>
    )
}

export default Entry