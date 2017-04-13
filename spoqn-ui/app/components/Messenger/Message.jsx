import React from 'react';
import moment from 'VendorJS/moment';

export class Message extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        if (this.props.messages.length) {
            return (
                <div className="message-list">
                    {this.props.messages.map(function (message, index) {
                        if (index % 2 === 0) {
                            return (
                                <div className="chat-content" key={index}>
                                    <div className="chat-body inline-block">
                                        <div className="header">
                                            <div>
                                                <small>{moment.unix(message.timestamp.seconds).format('MMMM Do YYYY, h:mm:ss a')}</small>
                                                <strong className="pull-right">{message.displayName}</strong>
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
                                                <strong>{message.displayName}</strong>
                                                <small
                                                    className="pull-right">{moment.unix(message.timestamp.seconds).format('MMMM Do YYYY, h:mm:ss a')}</small>
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
        } else {
            return (<div>Loading...</div>)
        }
    }
}