import {combineReducers} from 'redux';
import homeReducer from './homeReducer';
import messengerReducer from './messengerReducer';
import authReducer from './authReducer'

export default combineReducers({
    messengerReducer,
    authReducer,
    homeReducer
})