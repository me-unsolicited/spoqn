import React from 'react';
import PropTypes from 'prop-types';
import {push}from 'react-router-redux';
import {connect} from "react-redux";

import AppBar from 'material-ui/AppBar';
import FlatButton from 'material-ui/FlatButton';

import {IndexCss} from './Index.css';

class Index extends React.Component {
    constructor(props) {
        super(props);

        this.styles = {
            title: {
                cursor: 'pointer',
            },
            button: {
                color: '#FFFFFF', margin: '5px'
            }
        };
    }

    loadSignIn() {
        this.context.store.dispatch(push('/signin'));
    }

    loadSignUp() {
        this.context.store.dispatch(push('/signup'))
    }

    render() {
        return (
            <div className="index-component">
                <AppBar title={<span style={this.styles.title}>Spoqn</span>}
                        iconElementRight={
                            <div>
                                <FlatButton className="sing-in-button" label="Sign In"
                                            onClick={this.loadSignIn.bind(this)} style={this.styles.button}/>
                                <FlatButton className="sing-up-button" label="Sign Up"
                                            onClick={this.loadSignUp.bind(this)} style={this.styles.button}/>
                            </div>
                        }/>
            </div>
        )
    }
}

Index.contextTypes = {
    store: PropTypes.object
};

export default connect()(Index);