import React from 'react';
import ReactDOM from 'react-dom';
import {Messenger} from 'Components/Messenger';

const message = 'Welcome to spoqn!';
ReactDOM.render(<Messenger message={message}/>, document.getElementById('app'));