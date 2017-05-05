import React from 'react';
import ReactDOM from 'react-dom';

import {SignIn} from 'Components/Authentication/SignIn';

import {bootstrap} from 'VendorCSS/bootstrap.min.css';
import {fontAwesome} from 'VendorCSS/font-awesome.min.css';
import {globalCSS} from 'Styles/styles.css';
ReactDOM.render(<SignIn/>, document.getElementById('app'));