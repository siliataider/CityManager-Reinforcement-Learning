import React from 'react';
import { Client } from '@stomp/stompjs';

// SEE : https://umes4ever.medium.com/real-time-application-using-websocket-spring-boot-java-react-js-flutter-eb87fe95f94f

const SOCKET_URL = 'ws://localhost/ws-message';

class App_Stomp extends React.Component {
  constructor() {

    super();
    this.state = {
      messages: 'You server message here.',
    };
    console.log("zagf")
  };

  componentDidMount() {
    let currentComponent = this;
    let onConnected = () => {
      console.log("Connected!!")
      client.subscribe('/topic/message', function (msg) {
        if (msg.body) {
          var jsonBody = JSON.parse(msg.body);
          if (jsonBody.message) {
            currentComponent.setState({ messages: jsonBody.message })
          }
        }
      });
    }

    let onDisconnected = () => {
      console.log("Disconnected!!")
    }

    const client = new Client({
      brokerURL: SOCKET_URL,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: onConnected,
      onDisconnect: onDisconnected
    });

    client.activate();
  };

  render() {
    return (
      <div>
        <div>{this.state.messages}</div>
      </div>
    );
  }

}

export default App_Stomp;