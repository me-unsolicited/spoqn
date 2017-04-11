import React from 'react';
import ReactDOM from 'react-dom';
import {Messenger} from 'Components/Messenger/Messenger';
import bootstrap from 'VendorCSS/bootstrap.min.css';
import styles from 'Styles/styles.css';

const message = 'Welcome to spoqn!';
ReactDOM.render(<Messenger message={message}/>, document.getElementById('app'));