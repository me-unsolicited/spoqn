import React from 'react';
import {Message} from 'Components/Messenger/Message';
import css from './Messenger.css';

export class Messenger extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <div className="messenger-view">
                    <Message/>
                </div>
                <div className="messenger-author">
                    <input type="text" className="user-input form-control inline-block"/>
                    <button className="send-button btn btn-primary btn-sm inline-block">Send</button>
                </div>
            </div>
        );
    }
}