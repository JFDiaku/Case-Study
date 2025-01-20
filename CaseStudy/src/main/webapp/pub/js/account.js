  function loadActivities(){
      const activitiesBox = $("#activities");

      $.ajax({
          method: "GET",
          url: "/account/activities",
          success: function(response) {
            // Handle the list of activities in the response
            console.log('Activities fetched successfully', response);

            // Example: Dynamically render activities to the table
            activitiesBox.empty()
            response.forEach(function(activity) {
               activitiesBox.append(`
               <div id="activity" class="activity">
                 <p class="value" style="display: none" >${activity.id}</p>
                 <p class="activity-name">${activity.name}</p>
                 <div id="activity-button"  onclick="removeActivity(${activity.id}, this)" class="activity-button" >x</div>
               </div>
             `)
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching activities:', error);
            alert('An error occurred while fetching activities.');
        }

      })
    }

  $(document).ready(function() {

    $("#accountLink").toggleClass("active");
    $("#accountLink .dash-icon").toggleClass("active");

 });

 function removeActivity(activityId, button){
     const parentDiv = $(button).closest('.activity');
     const activityName = parentDiv.find('.activity-name').html();
      parentDiv.remove();
     console.log(activityName);


     $.ajax({
         url: '/account/deleteActivity/' + activityId,
         method: 'DELETE', // Or 'PATCH' for partial update
         success: function(response) {
                     // Handle the success, for example, remove the item from the table
                     console.log('Item deleted successfully');
                 },
                 error: function(xhr, status, error) {
                     // Handle errors (e.g., if the item wasn't found)
                     if (xhr.status === 404) {
                         console.log('Item not found');
                     } else {
                         console.log('Error deleting item');
                     }
                 }
     });
}


function addActivity(){
    const activityId = $("#activity-select").val();
    $.ajax({
      url: '/account/addActivity/' + activityId,
      method: 'POST',
      success: function(response) {
           // Handle the success, for example, remove the item from the table
           console.log(response);
           $('#response-message').text(response);
           setTimeout(function() {
             $('#response-message').text(''); // Clear the text
           }, 2000);
            loadActivities();
        },
       error: function(xhr, status, error) {
           // Handle errors (e.g., if the item wasn't found)
           if (xhr.status === 404) {
               console.log('Item not found');

           } else {
               console.log('Error adding item');
           }
       }
    });
}

function uploadImage(){
    const fileInput = $('#image-upload')[0].files[0]; // Get the selected file

    if (!fileInput) {
      $('#current-file').text('Please select a file to upload');
      return;
    }

    const formData = new FormData();
    formData.append('image', fileInput); // Append the image file to the formData

    $.ajax({
      url: '/account/updateImage', // Replace with your server endpoint
      type: 'PATCH',
      data: formData,
      processData: false, // Prevent jQuery from processing the data
      contentType: false, // Prevent jQuery from setting the content type
      success: function (response) {
        console.log(response.message);
        $("#profile-pic").attr('src', response.url)
      },
      error: function (xhr, status, error) {
        console.error('Error uploading image:', error);
        alert('Failed to upload image.');
      }
    });
}


function showUpload(){
    $("#upload-box").css({
    'display':'flex'
    });
}

function updateCurrentFile(input){
   const file = $(input)[0];
   const fileName = file.files.length > 0 ? file.files[0].name : 'No file selected';
   $('#current-file').text(fileName);
}

function closeSaveImage(){
  $("#image-upload").val("");
   $('#current-file').text('No file selected');
   $("#upload-box").hide()
}


function editPersonal(){
   $("#edit-personal").hide();
   $('#save-personal').css({
   'display':'flex'
   });
   $('#fname').prop('disabled', false);
   $('#lname').prop('disabled', false);
}


function editLocation(){
    $("#edit-location").hide();
   $('#save-location').css({
   'display':'flex'
   });
   $('#city').prop('disabled', false);
   $('#state').prop('disabled', false);
}

function editBio(){
    $("#edit-bio").hide();
   $('#save-bio').css({
   'display':'flex'
   });
   $('#bio').prop('disabled', false);
}

function updateBio(){
    const bio = $("#bio").val()

     if (!bio) {
      $('#bio-response-message').show()
      $('#bio-response-message').text('All fields are required.');
       setTimeout(function() {
           $('#bio-response-message').text('');
           $('#bio-response-message').hide();
         }, 2000);
      return;
    }
    $.ajax({
      url: '/account/updateBio',
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({
        description: bio,
      }),
      success: function (response) {
        $('#bio-response-message').show()
        $('#bio-response-message').text(response.message);
         setTimeout(function() {
             $('#bio-response-message').text('');
             $('#bio-response-message').hide();
           }, 2000);
      },
      error: function (xhr, status, error) {
        $('bio-response-message').text('Error updating profile: ' + error).css('color', 'red');
      },
    });

     $("#save-bio").hide();
     $("#edit-bio").css('display','flex');
     $('#bio').prop('disabled', true);
}


function updateLocation(){
    const city = $("#city").val()
    const state = $("#state").val()

     if (!city || !state) {
      $('#location-response-message').show()
      $('#location-response-message').text('All fields are required.');
       setTimeout(function() {
           $('#location-response-message').text('');
           $('#location-response-message').hide();
         }, 2000);
      return;
    }
    $.ajax({
      url: '/account/updateLocation',
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({
        city: city,
        state: state,
      }),
      success: function (response) {
        $('#location-response-message').show()
        $('#location-response-message').text(response.message);
         setTimeout(function() {
             $('#location-response-message').text('');
             $('#location-response-message').hide();
           }, 2000);
      },
      error: function (xhr, status, error) {
        $('location-response-message').text('Error updating profile: ' + error).css('color', 'red');
      },
    });

     $("#save-location").hide();
     $("#edit-location").css('display','flex');
     $('#city').prop('disabled', true);
     $('#state').prop('disabled', true);
}


function updatePersonal(){
    const fname = $("#fname").val()
    const lname = $("#lname").val()

     if (!email || !fname || !lname) {
          $('#personal-response-message').show()
          $('#personal-response-message').text('All fields are required.');
           setTimeout(function() {
               $('#personal-response-message').text('');
               $('#personal-response-message').hide();
             }, 2000);
          return;
        }

     $.ajax({
          url: '/account/updatePersonal',
          type: 'PATCH',
          contentType: 'application/json',
          data: JSON.stringify({
            firstName: fname,
            lastName: lname,
          }),
          success: function (response) {
            $('#personal-response-message').show()
            $('#personal-response-message').text(response.message);
             setTimeout(function() {
                 $('#personal-response-message').text('');
                 $('#personal-response-message').hide();
               }, 2000);
          },
          error: function (xhr, status, error) {
            $('personal-response-message').text('Error updating profile: ' + error).css('color', 'red');
          },
        });

     $("#save-personal").hide();
     $("#edit-personal").css('display','flex');
     $('#fname').prop('disabled', true);
     $('#lname').prop('disabled', true);
 }

