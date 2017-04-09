import React from 'react';

export class Messenger extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            message: this.props.message || 'This is a default message'
        };
    }

    render() {
        return (
            <div>
                <h1>{this.state.message}</h1>
            </div>
        );
    }
}