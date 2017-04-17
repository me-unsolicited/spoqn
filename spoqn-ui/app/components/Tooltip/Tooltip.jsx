import React from 'react';
import {css} from './Tooltip.css';

export class Tooltip extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            show: false
        }
    }

    show() {
        this.setState({
            show: true
        });
    }

    hide() {
        this.setState({
            show: false
        });
    }

    _handleHide() {
        this.hide();
        this.props.callback();
    }

    render() {
        return (
            <div className={this.state.show ? 'custom-tooltip show' : 'custom-tooltip'} onClick={this._handleHide.bind(this)}>
                <div className="tooltip-content">
                    <span className="text-content">{this.props.content}</span>
                    <i className="fa fa-arrow-circle-down" aria-hidden="true"> </i>
                </div>
            </div>
        );
    }
}
