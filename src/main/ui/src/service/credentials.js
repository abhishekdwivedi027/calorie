let username = "James"
let password = "Bond"

export const getCredentials = () => {
    return {
        username,
        password
    }
}

export const setCredentials = (uName, pWord) => {
    username = uName
    password = pWord
}

export const getNutritionixCredentials = () => {
    return {
        appId: '5d20a90e',
        appKey: 'b70f074ea444b45448612c01f7bb9765'
    }
}