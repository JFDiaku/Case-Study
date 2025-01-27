let currentReceiverId = null;
let loggedInUserId = null
let lastDate = null;
let debounceTimer;

$(document).ready(function() {
    function getLastNumberFromUrl() {
       const path = window.location.pathname; // Get the path (e.g., "/messages/4")
       const segments = path.split('/');      // Split the path into parts
       return segments.pop();                // Get the last segment
   }

    const userId = getLastNumberFromUrl();;


    if(userId != null && userId != "messages"){
       changeReceiver(userId);
    }else{
        const recipientId = $("#recipientId").val();
        changeReceiver(recipientId);
    }


});

function changeReceiver(userId){
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(() => {

        currentReceiverId = Number(userId);
        console.log("current recipient:" + currentReceiverId);

        loggedInUserId = Number($("#loggedInUserId").val());
        const currentChat = $("#current-chat");

        console.log("current user:" + loggedInUserId);
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
                        let date = new Date(message.createdAt);
                        let formattedTime = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true });
                        let currentDay = date.toLocaleDateString([], { weekday: 'long' });

                        if (lastDate !== currentDay) {
                                messages.append(`<p class="date-separator">${currentDay} ${formattedTime}</p>`);
                                lastDate = currentDay;
                        }


                        if(message.sender.id === currentReceiverId){
                            messages.append(`
                            <div class="incoming singleMessage">
                               <div class="message-body">
                                 <p>${message.message}</p>
                               </div>
                               <div class="message-details">
                                 <p>${formattedTime}</p>
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
                                     <p>${formattedTime}</p>
                                     <p>${message.sender.firstName}</p>
                                     <img src="${message.sender.image}" onerror="this.src='../../pub/images/profile.png';" alt="">
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
    let date = new Date(message.createdAt);
    let formattedTime = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true });



    if(message.sender.id === currentReceiverId){
        messages.append(`
        <div class="incoming singleMessage">
           <div class="message-body">
             <p>${message.message}</p>
           </div>
           <div class="message-details">
             <p>${formattedTime}</p>
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
                 <p>${formattedTime}</p>
                 <p>${message.sender.firstName}</p>
                 <img src="${message.sender.image}" onerror="this.src='../../pub/images/profile.png';" alt="">
           </div>
            `)
    }

    // Optionally, scroll to the bottom of the messages container
    messages.scrollTop(messages[0].scrollHeight);
}