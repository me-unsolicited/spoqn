import React from 'react';
import {Messenger} from 'Components/Messenger/Messenger';

import {HomeCss} from './Home.css';

export class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            token: this.props.token,
            topic: 'http://www.neopr.co.uk/wp-content/uploads/2016/08/newspaper.jpg'
        };
    }

    render() {
        return (
            <div className="home-component view-body">
                <div className="view-north">
                    <div className="topic-view inline-block">
                        <iframe className="content-viewer" src={this.state.topic}>
                        </iframe>
                    </div>
                    <div className="rooms-view inline-block">
                    </div>
                </div>
                <div className="view-south">
                    <div className="message-view">
                        <Messenger token={this.state.token}/>;
                    </div>
                </div>
                <div className="about">
                    <div className="logo">
                        <h3 className="logo-text">spoqn</h3>
                    </div>
                </div>
            </div>
        )
    }
}