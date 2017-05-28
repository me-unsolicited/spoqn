import React from 'react';
import PropTypes from 'prop-types';
import Services from 'Common/Services';
import {push} from 'react-router-redux';
import {connect} from "react-redux";

import RaisedButton from 'material-ui/RaisedButton';
import Checkbox from 'material-ui/Checkbox';
import TextField from 'material-ui/TextField';
import {AuthCss} from './Authentication.css';

class SignIn extends React.Component {
    constructor(props) {
        super(props);
    }

    handleEmail(e) {
        this.props.dispatch({
            type: 'SET_EMAIL',
            email: e.target.value
        });
    }

    handlePassword(e) {
        this.props.dispatch({
            type: 'SET_PASSWORD',
            password: e.target.value
        });
    }

    getUser() {
        let self = this,
            state = this.context.store.getState().authReducer,
            user = {
                loginId: state.email,
                displayName: state.username,
                password: state.password
            };

        Services.get({
            headers: {
                'Authorization': 'Basic ' + window.btoa(user.loginId + ':' + user.password),
            },
            url: '/api/token',
        }).done(function (response) {
            self.props.dispatch({
                type: 'SET_EMAIL',
                email: ''
            });

            self.props.dispatch({
                type: 'SET_PASSWORD',
                password: ''
            });

            self.context.store.dispatch(push('/signup'));
        }).fail(function (response) {
            debugger;
        });
    }

    render() {
        return (
            <div className="auth-component">
                <div>
                    <TextField hintText="Your E-mail Address Please" floatingLabelText="E-mail Address" type="email"
                               className="email-address" id="email-address"
                               fullWidth={true} onChange={this.handleEmail.bind(this)}/>
                </div>
                <div>
                    <TextField hintText="And Of Course A Password" floatingLabelText="Password" type="password" id="password"
                               fullWidth={true} onChange={this.handlePassword.bind(this)}/>
                </div>
                <div className="remember-me">
                    <Checkbox label="Remember Me"/>
                </div>
                <RaisedButton label="Sign In" onClick={this.getUser.bind(this)}/>
            </div>
        )
    }
}

SignIn.propTypes = {
    email: PropTypes.string,
    password: PropTypes.string
};

SignIn.contextTypes = {
    store: PropTypes.object
};

function mapStateToProps(state) {
    return {
        email: state.email,
        password: state.password
    };
}

export default connect(mapStateToProps)(SignIn);