import React from 'react';
import PropTypes from 'prop-types';
import Services from 'Common/Services';
import {connect} from "react-redux";

import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import {AuthCss} from './Authentication.css';

class SignUp extends React.Component {
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

    handleUsername(e) {
        this.props.dispatch({
            type: 'SET_USERNAME',
            username: e.target.value
        });
    }

    createUser() {
        let state = this.context.store.getState().authReducer,
            user = {
                loginId: state.email,
                displayName: state.username,
                password: state.password
            };

        Services.post({
            url: '/api/users',
            data: JSON.stringify(user)
        }).done(function (response) {
            console.log(response);
        }).fail(function (response) {
            console.log(response);
        });
    }

    render() {
        return (
            <div className="auth-component">
                <div>
                    <TextField hintText="A Username If You Don't Mind" floatingLabelText="Username" type="text" id="username"
                               fullWidth={true} onChange={this.handleUsername.bind(this)}/>
                </div>
                <div>
                    <TextField hintText="Your E-mail Address Please" floatingLabelText="E-mail Address" type="email"
                               className="email-address" id="email-address"
                               fullWidth={true} onChange={this.handleEmail.bind(this)}/>
                </div>
                <div>
                    <TextField hintText="And Of Course A Password" floatingLabelText="Password" type="password" id="password"
                               fullWidth={true} onChange={this.handlePassword.bind(this)}/>
                </div>
                <RaisedButton label="Sign Up" onClick={this.createUser.bind(this)}/>
            </div>
        )
    }
}

SignUp.propTypes = {
    email: PropTypes.string,
    password: PropTypes.string,
    username: PropTypes.string
};

SignUp.contextTypes = {
    store: PropTypes.object
};

function mapStateToProps(state) {
    return {
        email: state.email,
        password: state.password,
        username: state.username
    };
}

export default connect(mapStateToProps)(SignUp);