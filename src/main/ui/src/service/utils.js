import Moment from 'moment';
import { getCredentials, getNutritionixCredentials } from './credentials';

export const formatDate = (date) => {
    return Moment(date).format('YYYY-MM-DD')
}

export const tokenOptions = () => {
  const uName = getCredentials().username
  const pWord = getCredentials().password
    return {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ 
          username: uName, 
          password: pWord
        })
      }
}

export const foodOptions = () => {
  const appId = getNutritionixCredentials().appId
  const appKey = getNutritionixCredentials().appKey
    return {
        method: 'GET',
        headers: { 
          'x-app-id': appId,
          'x-app-key': appKey,
        },
      }
}

export const dataOptions = (token) => {
    const bearer = `Bearer ${token}`;
    return {
        method: 'GET',
        headers: { 
          'Content-Type': 'application/json',
          'Authorization': bearer,
        },
      }
}

export const addCalorieIntakeOptions = (token, user, food, calorie, date) => {
    const bearer = `Bearer ${token}`;
    return {
        method: 'POST',
        headers: { 
          'Content-Type': 'application/json',
          'Authorization': bearer, 
        },
        body: JSON.stringify({ 
          'userId': `${user}`,
          'foodName': `${food}`,
          'calorieValue': `${calorie}`,
          'intakeDate': `${date}`
        })
      }
}

export const compareByDate = (a, b) => {
  if ( a.intakeDate < b.intakeDate ){
      return -1;
    }
    if ( a.intakeDate > b.intakeDate ){
      return 1;
    }
    return 0;
}

export const getSuggestions = (foods) => {
  let suggestedFoods = []
  const commonFoods = foods.common
  // const brandedFoods = foods.branded
  const commonFoodNames = commonFoods.map(common => common.food_name)
  // const brandedFoodNames = brandedFoods.map(branded => branded.food_name)
  // suggestedFoods = suggestedFoods.concat(commonFoodNames).concat(brandedFoodNames).sort()
  suggestedFoods = suggestedFoods.concat(commonFoodNames).sort()
  return suggestedFoods
}

export const capitalizeFirstLetter = (string) => string.charAt(0).toUpperCase() + string.slice(1)
