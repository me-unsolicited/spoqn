import React from 'react';

import $ from 'VendorJS/jQuery.min';

import {Message} from 'Components/Messenger/Message';
import {Tooltip} from 'Components/Tooltip/Tooltip'

import {css} from './Messenger.css';

export class Messenger extends React.Component {
    constructor(props) {
        super(props);
        let context = this;

        this.state = {
            newMessage: '',
            messages: [],
            isPolling: false
        };
        this._getMessages().done(function (response) {
            context.setState({
                messages: response
            });

            context._pollMessages();
            context.constructor._focusLatestMessage();
        });
    }

    static _focusLatestMessage() {
        let element;

        element = document.querySelector('.message-list').lastChild;
        element.scrollIntoView({
            behavior: "smooth"
        });
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
            if (context.state.isPolling && response.length > context.state.messages.length) {
                context.setState({
                    messages: response
                });

                context.refs.tooltip.show();
            }
        });

        return deferred.promise();
    }

    _sendMessage() {
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

            document.querySelector('.messenger-author .user-input').value = '';
            context.constructor._focusLatestMessage();
        });

        return deferred.promise();
    }

    _pollMessages() {
        let context = this;

        context.setState({
            isPolling: true
        });

        window.setInterval(function () {
            context._getMessages();
        }, 5000);
    }

    _handleNewMessage(e) {
        this.state.newMessage = e.target.value;
    }

    _handleSubmit(e) {
        e.preventDefault();
        this._sendMessage();
    }

    _handleScroll() {
        if (this.refs.tooltip.state.show) {
            this.refs.tooltip.hide();
        }
    }

    _clearMessage(e) {
        e.preventDefault();
        this.state.newMessage = '';
        document.querySelector('.messenger-author .user-input').value = '';
    }

    render() {
        return (
            <div className="messenger-component">
                <div className="messenger-view" id="messenger-view" onScroll={this._handleScroll.bind(this)}>
                    <Message messages={this.state.messages}/>
                </div>
                <Tooltip content={'New Message'} ref="tooltip"
                         callback={this.constructor._focusLatestMessage.bind(this)}/>
                <form className="messenger-author" onSubmit={this._handleSubmit.bind(this)}>
                    <textarea placeholder="Type Message... Get involved!" type="text"
                           className="user-input form-control inline-block" rows="4" cols="50"
                              onChange={this._handleNewMessage.bind(this)}>
                    </textarea>
                    <button className="send-button btn btn-primary btn-sm inline-block" type="submit">
                        <i className="fa fa-telegram fa-2x" aria-hidden="true"> </i>
                    </button>
                    <button className="clear-button btn btn-default btn-sm inline-block" type="button" onClick={this._clearMessage.bind(this)}>
                        <i className="fa fa-times-circle fa-2x" aria-hidden="true"> </i>
                    </button>
                </form>
            </div>
        );
    }
}