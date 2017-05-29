import React from 'react';
import {Messenger} from 'Components/Messenger/Messenger';

import {HomeCss} from './Home.css';

export class Home extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="home-component view-body">
                <div className="view-west">

                </div>
                <div className="view-east">

                </div>
            </div>
        )
    }
}