import React from 'react';

export class Message extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <div className="chat-content">
                    <div className="chat-head inline-block">
                         <span className="chat-img">
                                <img src="https://www.themarysue.com/wp-content/uploads/2015/12/avatar.jpeg" className="img-circle user-avatar"/>
                         </span>
                    </div>

                    <div className="chat-body inline-block">
                        <div className="header">
                            <div>
                                <strong>John Doe</strong>
                                <small className="pull-right">00:00 pm</small>
                            </div>
                        </div>
                        <p className="message-content">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                            dolor, quis ullamcorper ligula sodales.
                        </p>
                    </div>
                </div>
            </div>
        );
    }
}