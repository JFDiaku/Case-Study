function changeActivity(activityName){
      const title = $("#title");
      const description = $("#description");
      const members = $("#members");

      $.ajax({
          method: "GET",
          url: "/activities/" + activityName,
          success: function(response) {
            // Handle the list of activities in the response
            console.log('Activity and members fetched successfully');

            // Example: Dynamically render activities to the table
            title.text(response.activity.name);
            description.text(response.activity.description);
            members.empty();
            response.members.forEach(function(member) {
               members.append(`
               <div class="member-card">
                    <a href="/user/${member.id}">
                        <img class="member-image" src="${member.image}" onerror="this.src='../../pub/images/profile.png';" alt="">
                    </a>
                    <p>${member.firstName} ${member.lastName}</p>
                    <p class="location">${member.city}, ${member.state}</p>
               </div
             `)
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching activity and members', error);
            alert('An error occurred while fetching activities.');
        }

      })
    }