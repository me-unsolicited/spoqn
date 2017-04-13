import React from 'react';
import ReactDOM from 'react-dom';
import bootstrap from 'VendorCSS/bootstrap.min.css';
import styles from 'Styles/styles.css';
import {SignIn} from "Components/Authentication/SignIn";

ReactDOM.render(<SignIn/>, document.getElementById('app'));
/*
ReactDOM.render(<Messenger user={user}/>, document.getElementById('app'));*/
