let currentReceiverId = null;
let loggedInUserId = null
let debounceTimer;

function changeReceiver(userId){
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(() => {

        currentReceiverId = userId;
        loggedInUserId = $("#loggedInUserId").val();
        console.log("user ID: " + loggedInUserId);
        const currentChat = $("#current-chat");
        const currentChatImage = $("#current-chat-image");
        const currentChatName = $("#current-chat-name");
        const currentChatLocation = $("#current-chat-location");
        const currentChatActivities = $("#current-chat-activities");
        const messages = $("#messages")
        $.ajax({
                  method: "GET",
                  url: "/messages/user/" ,
                  data: { userId: userId },
                  success: function(response) {
                    // Handle the list of activities in the response
                    console.log('Now chatting with ', response.user.firstName);

                    currentChat.attr("href", "/user/" + response.user.id);
                    currentChatImage.attr("src", response.user.image );
                    currentChatName.text(response.user.firstName + " " + response.user.lastName);
                    currentChatLocation.text(response.user.city + ", " + response.user.state);
                    // Example: Dynamically render activities to the table
                    currentChatActivities.empty()
                    response.activities.forEach(function(activity) {
                       currentChatActivities.append(`
                       <a href="/activities">${activity.name}</a>
                     `)
                    });
                    messages.empty();
                    response.messages.forEach(function(message){
                        console.log(message)
                        if(message.sender.id === currentReceiverId){
                            messages.append(`
                            <div class="incoming singleMessage">
                               <div class="message-body">
                                 <p>${message.message}</p>
                               </div>
                               <div class="message-details">
                                 <p>${message.createdAt}</p>
                                 <p>${message.sender.firstName}</p>
                                 <img src="${message.sender.image}" alt="" onerror="this.src='../../pub/images/profile.png';">
                           </div>
                            `)
                        }else{
                            messages.append(`
                                <div class="outgoing singleMessage">
                                   <div class="message-body">
                                     <p>${message.message}</p>
                                   </div>
                                   <div class="message-details">
                                     <p>${message.createdAt}</p>
                                     <p>${message.sender.firstName}</p>
                                     <img src="${message.sender.image}" alt="">
                               </div>
                                `)
                        }
                    });
                    messages.scrollTop(messages[0].scrollHeight);
                    connect();
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching user:', error);
                    alert('An error occurred while fetching user.');
                }

              })
          }, 300);
}


let stompClient = null;
let subscriptionReceiver = null;
let subscriptionSender = null;
function connect() {
    const socket = new SockJS('/ws'); // Connect to WebSocket endpoint
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        // Unsubscribe from the previous subscription if it exists
        if (subscriptionReceiver) {
                    subscriptionReceiver.unsubscribe();
                }

        if (subscriptionSender) {
            subscriptionSender.unsubscribe();
        }

        // Subscribe to the receiver's topic
        subscriptionReceiver = stompClient.subscribe('/topic/messages/' + currentReceiverId, function(messageOutput) {
            showMessage(JSON.parse(messageOutput.body));  // Handle incoming message
        });

        // Subscribe to the sender's topic (to receive messages from the sender)
        subscriptionSender = stompClient.subscribe('/topic/messages/' + loggedInUserId, function(messageOutput) {
            showMessage(JSON.parse(messageOutput.body));  // Handle incoming message
        });
    });

    stompClient.debug = function(message) {
        console.log("STOMP DEBUG:", message);
    };
}

function sendMessage() {
    const messageContent = $("#chat-input").val();  // Get the typed message
    if (!messageContent.trim()) {
        alert("Please type a message before sending!");
        return;
    }

    if (stompClient && stompClient.connected) {
        // Send the message content and receiver ID via WebSocket
        stompClient.send(
            "/app/sendMessage",  // The WebSocket endpoint to map to the controller
            {},  // Headers (empty in this case)
            JSON.stringify({
                message: messageContent,
                receiverId: currentReceiverId  // Send the current receiver ID
            })
        );
        // Optionally, clear the message input field after sending
        $("#chat-input").val("");
    }
}

function showMessage(message) {
    console.log('New message received:', message);
    const messages = $("#messages");

    // Append the new message to the chat, depending on whether it's incoming or outgoing
    if (message.sender.id === currentReceiverId) {
        messages.append(`
            <div class="incoming singleMessage">
                <div class="message-body">
                    <p>${message.message}</p>
                </div>
                <div class="message-details">
                    <p>${message.createdAt}</p>
                    <p>${message.sender.firstName}</p>
                    <img src="${message.sender.image}" alt="" onerror="this.src='../../pub/images/profile.png';">
                </div>
            </div>
        `);
    } else {
        messages.append(`
            <div class="outgoing singleMessage">
                <div class="message-body">
                    <p>${message.message}</p>
                </div>
                <div class="message-details">
                    <p>${message.createdAt}</p>
                    <p>${message.sender.firstName}</p>
                    <img src="${message.sender.image}" alt="">
                </div>
            </div>
        `);
    }

    // Optionally, scroll to the bottom of the messages container
    messages.scrollTop(messages[0].scrollHeight);
}