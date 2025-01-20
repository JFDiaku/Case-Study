function toggleDropdown() {
  const dropdown = $('#dropdown');
  const dropButton = $('#drop-button');
  dropdown.toggleClass('show');
  dropButton.toggleClass('show');

  var shown = false;
  if(dropdown.hasClass('show')){
    shown = true;
    console.log(shown);
  }else{
    console.log(shown);
  }

  if (shown) {
    // Add an event listener to the document
    $(document).on('click.dropdownListener', function (event) {
      // Check if the click is outside the dropdown
      if (!$(event.target).closest('#dropdown').length && !$(event.target).closest('#drop-button').length) {
        dropdown.toggleClass('show');
        dropButton.toggleClass('show'); // Toggle the class
        console.log('Clicked outside');

        // Remove the event listener to avoid repeated bindings
        $(document).off('click.dropdownListener');
      }
    });
  }


}

function toggleNewMessages(){
  let newMessages = $("#new-messages");
  let toggleImg = $("#toggle-new-messages").find("img");
  toggleImg.toggleClass('show');
  newMessages.toggleClass('show');
}


function toggleActiveChat(){
  let Activechat = $("#active-chats");
  let toggleImg = $("#toggle-active-chat").find("img");
  toggleImg.toggleClass('show');
  Activechat.toggleClass('show');
}


function toggleCreatActivity(){
  let toggleCreateForm = $("#toggle-create-activity").find("img");
  let activityForm = $("#create-activity-form");
  toggleCreateForm.toggleClass('show')
  activityForm.toggleClass('show')
}

