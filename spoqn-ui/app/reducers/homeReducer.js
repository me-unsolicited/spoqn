const defaultState = {
    token: {},
    topic: 'http://www.neopr.co.uk/wp-content/uploads/2016/08/newspaper.jpg'
};

export default function homeReducer (state = defaultState, action) {
    switch (action.type) {
        case 'SET_TOKEN':
            return {
                ...state,
                token: action.token
            };
        case 'SET_TOPIC':
            return {
                ...state,
                topic: action.topic
            };
        default:
            return state;
    }
}