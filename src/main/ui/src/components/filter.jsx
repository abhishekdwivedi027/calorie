import React, {useState} from "react";

import Stack from 'react-bootstrap/Stack'
import Button from 'react-bootstrap/Button'
import DatePicker from "react-datepicker";
import { formatDate } from "../service/utils";

import "react-datepicker/dist/react-datepicker.css"

function Filter({onFilter}) {

    const now = new Date();
    const lastYear = new Date(now.getFullYear()-1, now.getMonth(), now.getDate());

    const [datesChanged, setDatesChanged] = useState(false)
    const [fromDate, setFromDate] = useState(lastYear);
    const [toDate, setToDate] = useState(now);

    const onFromDateChange = (date) => {
        setFromDate(date);
        setDatesChanged(true)
    }

    const onToDateChange = (date) => {
        setToDate(date);
        setDatesChanged(true)
    }

    const onApply = () => {
        if (fromDate >= toDate) {
            onReset()
        } else if (datesChanged) {
            onFilter(formatDate(fromDate), formatDate(toDate))
        }
    }

    const onReset = () => {
        if (datesChanged) {
            setFromDate(lastYear)
            setToDate(now)
            setDatesChanged(false)
            onFilter()
        }
    }

    return (
        <div>
            <div>
                <br></br>
                <b>Filter By Dates:</b>
                <br></br>
                <br></br>
            </div>
            <Stack direction="horizontal" gap={2} className="col-md-4">
                <b>From:</b><DatePicker selected={fromDate} onChange={(date) => onFromDateChange(date)} />
                <b>To:</b><DatePicker selected={toDate} onChange={(date) => onToDateChange(date)} />
                <Button variant="primary" onClick={() => onApply()}>Apply</Button>
                <Button variant="outline-primary" onClick={() => onReset()}>Reset</Button>
            </Stack>
            <div>
                <br></br>
                <br></br>
            </div>

        </div>
    )
}

export default Filter