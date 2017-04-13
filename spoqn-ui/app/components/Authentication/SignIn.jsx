import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'VendorJS/jQuery.min';
import {Messenger} from "Components/Messenger/Messenger";
import css from './Authentication.css';

export class SignIn extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: ''
        };

    }

    handleEmail(e) {
        this.state.username = e.target.value;
    }

    handlePassword(e) {
        this.state.password = e.target.value;
    }

    createUser(){
        let deferred = $.Deferred(),
            user = {
                username: this.state.username,
                password: this.state.password
            };

        $.ajax({
            url: '/api/token',
            type: 'GET',
            headers: {
                'Authorization': 'Basic ' + window.btoa(user.username + ':' +  user.password),
                'Accept':'application/json'
            },
            success: deferred.resolve,
            error: deferred.reject
        });

        deferred.promise().done(function (response) {
            ReactDOM.render(<Messenger token={response}/>, document.getElementById('app'));
        });
    }

    render() {
        return (
            <div className="sign-in-form">
                <div className="form-group">
                    <label for="email">Email:</label>
                    <input type="email" className="form-control" id="email" placeholder="Enter email" onChange={this.handleEmail.bind(this)}/>
                </div>
                <div className="form-group">
                    <label for="pwd">Password:</label>
                    <input type="password" className="form-control" id="pwd" placeholder="Enter password" onChange={this.handlePassword.bind(this)}/>
                </div>
                <div className="checkbox">
                    <label><input type="checkbox"/> Remember me</label>
                </div>
                <button className="btn btn-default" onClick={this.createUser.bind(this)}>Submit</button>
            </div>
        )
    }
}