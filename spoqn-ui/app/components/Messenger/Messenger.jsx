import React from 'react';
import {Message} from 'Components/Messenger/Message';
import css from './Messenger.css';

export class Messenger extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: this.props.user,
            newMessage: '',
            messages: []
        };
        this._getMessages();
    }

    componentDidUpdate() {
        this.focusLatestMessage();
        document.querySelector('.messenger-author .user-input').value = '';
    }

    componentDidMount() {
        this.focusLatestMessage();
    }

    focusLatestMessage () {
        let element;

        element = document.querySelector('.message-list').lastChild;
        element.scrollIntoView();
    }

    _getMessages() {
        this.state.messages = [
            {
                timestamp: new Date(1492556093 * 1000).toDateString(),
                text: `Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam nec felis leo. 
                Donec eu condimentum sapien, venenatis ultrices sapien. Aliquam erat volutpat. 
                Vestibulum vitae arcu dui. Suspendisse ac nisi id purus tempor semper id gravida nisl. 
                Aliquam ultrices consequat tempor. Nullam vel mauris quam. Nunc vel auctor quam. Phasellus suscipit rutrum sem, a pulvinar magna finibus nec. 
                Morbi dignissim, neque a pretium euismod, odio metus ultrices felis, ut tincidunt purus neque ac libero. 
                Donec et rhoncus nunc. Fusce quis sem quis velit dictum commodo in nec tortor.`,
                username: 'John Doe',
                userId: 'jDoe'
            },
            {
                timestamp: new Date(1492037693 * 1000).toDateString(),
                text: `Curabitur rhoncus, risus at cursus ultrices, augue felis pulvinar elit, ut lobortis sem neque id tellus.
                Aenean non vestibulum urna. Suspendisse id ligula ullamcorper.`,
                username: 'James Doe',
                userId: 'jDoe2'
            },
            {
                timestamp: new Date(1491951293 * 1000).toDateString(),
                text: `Donec ultricies a neque sit amet dignissim. Phasellus congue commodo bibendum. Nam lacinia rhoncus diam in venenatis. 
                Ut pellentesque arcu id arcu hendrerit, a condimentum urna volutpat.`,
                username: 'Jane Doe',
                userId: 'jDoe1'
            },
            {
                timestamp: new Date(1491954893 * 1000).toDateString(),
                text: 'Yea Lorem impsum mofo',
                username: 'James Doe',
                userId: 'jDoe2'
            }
        ]
    }

    sendMessage() {
        let messageList = this.state.messages.slice();

        messageList.push({
            timestamp: new Date().toDateString(),
            text: this.state.newMessage,
            username: this.state.user.username,
            userId: this.state.user.loginId
        });

        this.setState({
            messages: messageList
        });
    }

    handleNewMessage(e) {
        this.state.newMessage = e.target.value;
    }

    render() {
        return (
            <div>
                <div className="messenger-view">
                    <Message messages={this.state.messages}/>
                </div>
                <div className="messenger-author">
                    <input placeholder="Type Message... Get involved!" type="text" className="user-input form-control inline-block"
                           onChange={this.handleNewMessage.bind(this)}/>
                    <button className="send-button btn btn-primary btn-sm inline-block"
                            onClick={this.sendMessage.bind(this)}>Send
                    </button>
                </div>
            </div>
        );
    }
}