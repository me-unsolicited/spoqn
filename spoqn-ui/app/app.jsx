import React from 'react';
import ReactDOM from 'react-dom';
import {bootstrap} from 'VendorCSS/bootstrap.min.css';
import {fontAwesome} from 'VendorCSS/font-awesome.min.css';
import {styles} from 'Styles/styles.css';
import {SignIn} from "Components/Authentication/SignIn";

ReactDOM.render(<SignIn/>, document.getElementById('app'));
