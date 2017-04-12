import React from 'react';

export class Message extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="message-list">
                {this.props.messages.map(function (message, index) {
                    if (index % 2 === 0) {
                        return (
                            <div className="chat-content" key={index}>
                                <div className="chat-body inline-block">
                                    <div className="header">
                                        <div>
                                            <small>{message.timestamp}</small>
                                            <strong className="pull-right">{message.username}</strong>
                                        </div>
                                    </div>
                                    <p className="message-content">{message.text}</p>
                                </div>
                                <div className="chat-head is-even inline-block">
                                     <span className="chat-img">
                                            <img src="https://www.themarysue.com/wp-content/uploads/2015/12/avatar.jpeg"
                                                 className="img-circle user-avatar"/>
                                     </span>
                                </div>
                            </div>
                        )
                    } else {
                        return (
                            <div className="chat-content" key={index}>
                                <div className="chat-head inline-block">
                                     <span className="chat-img">
                                            <img src="https://www.themarysue.com/wp-content/uploads/2015/12/avatar.jpeg"
                                                 className="img-circle user-avatar"/>
                                     </span>
                                </div>
                                <div className="chat-body inline-block">
                                    <div className="header">
                                        <div>
                                            <strong>{message.username}</strong>
                                            <small className="pull-right">{message.timestamp}</small>
                                        </div>
                                    </div>
                                    <p className="message-content">{message.text}</p>
                                </div>
                            </div>
                        )
                    }
                })}
            </div>
        );
    }
}