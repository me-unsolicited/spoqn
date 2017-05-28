const defaultState = {
    email: '',
    password: '',
    username: ''
};

export default function authReducer (state = defaultState, action) {
    switch (action.type) {
        case 'SET_EMAIL':
            return {
                ...state,
                email: action.email
            };
        case 'SET_PASSWORD':
            return {
                ...state,
                password: action.password
            };
        case 'SET_USERNAME':
            return {
                ...state,
                username: action.username
            };
        default:
            return state;
    }
}