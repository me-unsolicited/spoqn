import React from 'react';
import {Message} from 'Components/Messenger/Message';
import $ from 'VendorJS/jQuery.min';
import ReactDOM from 'react-dom';
import css from './Messenger.css';

export class Messenger extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            newMessage: '',
            messages: []
        };
        this._getMessages();
    }

    componentDidUpdate() {
        this.focusLatestMessage();
        document.querySelector('.messenger-author .user-input').value = '';
    }

    focusLatestMessage() {
        let element;

        element = document.querySelector('.message-list').lastChild;
        element.scrollIntoView();
    }

    _getMessages() {
        let deferred = $.Deferred(),
            context = this;

        $.ajax({
            url: '/api/messages',
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + this.props.token.access,
                'Accept': 'application/json'
            },
            success: deferred.resolve,
            error: deferred.reject
        });

        deferred.promise().done(function (response) {
            context.setState({
                messages: response
            });
            //ReactDOM.render(<Message messages={context.state.messages}/>, document.getElementById('messenger-view'));
            context.focusLatestMessage();
        });
    }

    sendMessage() {
        let deferred = $.Deferred(),
            context = this;

        $.ajax({
            url: '/api/messages',
            type: 'POST',
            data: JSON.stringify({
                text: context.state.newMessage
            }),
            headers: {
                'Authorization': 'Bearer ' + context.props.token.access,
                'Content-type': 'application/json',
                'Accept': 'application/json'
            },
            success: deferred.resolve,
            error: deferred.reject
        });

        deferred.promise().done(function (response) {
            let messageList = context.state.messages.slice();

            messageList.push(response);

            context.setState({
                messages: messageList
            });
        });
    }

    handleNewMessage(e) {
        this.state.newMessage = e.target.value;
    }

    render() {
        return (
            <div>
                <div className="messenger-view" id="messenger-view">
                    <Message messages={this.state.messages}/>
                </div>
                <div className="messenger-author">
                    <input placeholder="Type Message... Get involved!" type="text"
                           className="user-input form-control inline-block"
                           onChange={this.handleNewMessage.bind(this)}/>
                    <button className="send-button btn btn-primary btn-sm inline-block"
                            onClick={this.sendMessage.bind(this)}>Send
                    </button>
                </div>
            </div>
        );
    }
}