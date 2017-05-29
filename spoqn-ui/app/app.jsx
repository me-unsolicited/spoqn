import React from 'react';
import ReactDOM from 'react-dom';

import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventPlugin from 'react-tap-event-plugin';

import {createStore, applyMiddleware} from 'redux';
import {Provider} from 'react-redux';

import createHistory from 'history/createBrowserHistory';
import {Route} from 'react-router';
import {ConnectedRouter, routerReducer, routerMiddleware} from 'react-router-redux';

import reducers from './reducers/rootReducer'
import SignUp from 'Components/Authentication/SignUp';
import SignIn from 'Components/Authentication/SignIn';
import Index from 'Components/Index/Index';

import {fontAwesome} from 'VendorCSS/font-awesome/font-awesome.css';
import {globalCSS} from 'Styles/styles.css';

// Create a history of your choosing (we're using a browser history in this case)
const history = createHistory();

// Build the middleware for intercepting and dispatching navigation actions
const middleware = routerMiddleware(history);
Object.assign(reducers, routerReducer);

// Add the reducer to your store on the `router` key
// Also apply our middleware for navigating
const store = createStore((reducers), applyMiddleware(middleware));

// Now you can dispatch navigation actions from anywhere!
// store.dispatch(push('/signup'));

injectTapEventPlugin();

ReactDOM.render(
    <Provider store={store}>
        <MuiThemeProvider>
            <ConnectedRouter history={history}>
                <div>
                    <Route path="/" component={Index}/>
                    <Route path="/signup" component={SignUp}/>
                    <Route path="/signin" component={SignIn}/>
                </div>
            </ConnectedRouter>
        </MuiThemeProvider>
    </Provider>,
    document.getElementById('spoqn-app'));