import React from 'react';
import ReactDOM from 'react-dom';
import {Messenger} from 'Components/Messenger/Messenger';
import bootstrap from 'VendorCSS/bootstrap.min.css';
import styles from 'Styles/styles.css';

const user = {
    loginId: 'test-user-1',
    username: 'Logged In Doe'
};

ReactDOM.render(<Messenger user={user}/>, document.getElementById('app'));