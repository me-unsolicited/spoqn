import React from 'react';
import $ from 'VendorJS/jQuery.min';

export class SignUp extends React.Component {
    constructor(props) {
        super(props);
    }

    handleEmail(e) {
        this.state.username = e.target.value;
    }

    handlePassword(e) {
        this.state.password = e.target.value;
    }

    createUser(e){
        let deferred = $.Deferred(),
            user = {
                username: this.state.username,
                password: this.state.password
            };

        $.ajax({
            url: '/api/users',
            data: JSON.stringify(user),
            type: 'POST',
            headers: {
                'Content-Type':'application/json',
                'Accept':'application/json'
            },
            success: deferred.resolve,
            error: deferred.reject
        });

        deferred.promise().done(function (response) {
            console.log(response);
        });
    }

    render() {
        return (
            <div>
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