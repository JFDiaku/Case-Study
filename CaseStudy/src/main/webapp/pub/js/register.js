const checkBox = $("#terms");

function setLocation(){
    if (checkBox.is(':checked')) {
        if(navigator.geolocation){
            navigator.geolocation.getCurrentPosition(showPosition);
          }
    };
}


function showPosition(position){
  $("#latitude").val(position.coords.latitude) ;
  $("#longitude").val(position.coords.longitude) ;

  console.log("latitude: " + $("#latitude").val());

}