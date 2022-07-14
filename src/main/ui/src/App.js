import React, {useState, useEffect} from "react";
import Tabs from 'react-bootstrap/Tabs'
import Tab from 'react-bootstrap/Tab'

import {tokenOptions, dataOptions, addCalorieIntakeOptions, formatDate, foodOptions, getSuggestions} from "./service/utils";
import Filter from './components/filter';
import Datatable from './components/displayTable';
import Entry from "./components/entry";
import AdminDashboard from "./components/adminDashboard";
import UserDashboard from "./components/userDashboard";


function App() {

  const URI = `http://localhost:8080`

  const foodOptionsURI = (query) => `https://trackapi.nutritionix.com/v2/search/instant?query=${query}`

  const [loading, setLoading] = useState(true)
  const [user, setUser] = useState({})
  const [users, setUsers] = useState([])
  const [data, setData] = useState([])
  const [displayData, setDisplayData] = useState(data)
  const [suggestions, setSuggestions] = useState([])

  useEffect(() => {
    setLoading(true)
    
    fetch(`${URI}/tokens`, tokenOptions())
    .then(response => response.json())
    .then(tokenDetails => {
      extractTokenDetails(tokenDetails)
    })
    .catch(ex => console.log(ex))

    setLoading(false)
  }, [])

  const extractTokenDetails = async (tokenDetails) => {
    const fetchedUser = {
      "id": tokenDetails.userId,
      "isAdmin": tokenDetails.admin,
      "token": tokenDetails.token
    }

    setUser(fetchedUser)
    fetchData(fetchedUser)
    if (fetchedUser.isAdmin) {
      fetchUsers(fetchedUser)
    } else {
      const users = [].concat(fetchedUser.id)
      setUsers(users)
    }
    
  }

  const fetchData = async (user, after, before) => {
    const dataURI = `${URI}/calorieIntakes`
    const userDataURI = user.isAdmin ? dataURI : `${dataURI}/${user.id}`
    const filterURI = after ? `${userDataURI}/filter?after=${after}&before=${before}` : userDataURI
    const onDataFetch = (data) => {
      if (!after) {
        // don't change main data on filtering
        setData(data)
      }
      setDisplayData(data)
    }

    fetch(filterURI, dataOptions(user.token))
    .then(response => response.json())
    .then(json => onDataFetch(json))
  }

  const fetchUsers = async (user) => {

    const usersURI = `${URI}/users`
    
    const onDataFetch = (users) => {
      const userIds = users.map(user => user.id)
      setUsers(userIds)
    }

    fetch(usersURI, dataOptions(user.token))
    .then(response => response.json())
    .then(json => onDataFetch(json))
  }

  const fetchSuggestions = async (query) => {

    const onFoodFetch = (foods) => {
      setSuggestions(getSuggestions(foods))
    }

    query && fetch(foodOptionsURI(query), foodOptions())
    .then(response => {
      if (response.ok) {
        response.json().then(json => onFoodFetch(json))
      }
    })
  }

  const addCalorieIntake = async (addEntryToUser, food, calorie, date) => {
    const onAddCalorieIntake = (addedData) => {
      addedData.intakeDate = formatDate(addedData.intakeDate)
      const withAddedData=[...data, addedData]
      setData(withAddedData)
      setDisplayData(withAddedData)
    }

    fetch(`${URI}/calorieIntakes`, addCalorieIntakeOptions(user.token, addEntryToUser, food, calorie, date))
    .then(response => response.json())
    .then(json => onAddCalorieIntake(json))
  }



  return (
    <Tabs defaultActiveKey="home" id="uncontrolled-tab-example" className="mb-3">
      <Tab eventKey="home" title="Home">
        <div>
          {!loading && <Filter onFilter={(fromDate, toDate) => fetchData(user, fromDate, toDate)} />}
        </div>
        <div>
          {!loading && <Datatable data={displayData} />}
        </div>
      </Tab>
      <Tab eventKey="add" title="Add More">
      <div>
          {users[0] && <Entry users={users} suggestions={suggestions} selected={users[0]}
          onChange={(val) => fetchSuggestions(val)}
          onEntry={(addEntryToUser, food, calorie, date) => addCalorieIntake(addEntryToUser, food, calorie, date)}/>}
        </div>
      </Tab>
      <Tab eventKey="board" title="Board">
        <div>
          {!loading && user.isAdmin && <AdminDashboard data={data} />}
        </div>
        <div>
          {!loading && !user.isAdmin && <UserDashboard data={data} userId={user.id} />}
        </div>
      </Tab>
    </Tabs>
  );
}

export default App;
