

/**
 * Private configuration constants
 */
var BASE_URL = 'http://localhost:9090',
	WIDTH = $(window).width(), height = $(window).height();

/**
 * Private global variables
 */
var PLACES = [],
	UNIQUE_PLACES = [],
	MARKERS = [],
	MAP = null,
	POSITION = {}, 
	DIR_SERVICE = null,
	DIR_DISPLAY = null,
	MARKER_BOUNDARY = null, 
	RADIUS = 50,
	TOTAL_RESULTS = 0,
	TOTAL_PAGES = 0;

/**
 * MAIN CLAVIN OBJECT
 */
var clavin = {
	
	settings: {
	    results: $("#resultList"),
	    canvas: $("#map-canvas")
	    
	},
		
	init: function() {
		SET = this.settings;
    }
};



var Map = {
		
	init: function() {
		
		//Google Directions
		DIR_SERVICE = new google.maps.DirectionsService;
				
		//Initialize Google Map
	   	MAP = new google.maps.Map(document.getElementById('map-canvas'), {
	   		center: {lat: 41.2324, lng: -96.681679},
	   		zoom:4,
	   		scrollwheel: false,
	   		zoomControl: true,
	        zoomControlOptions: {
	              position: google.maps.ControlPosition.RIGHT_TOP
	        },
	   		mapTypeId: google.maps.MapTypeId.ROADMAP,
	   		styles: [
	   	            {elementType: 'geometry', stylers: [{color: '#19233e'}]},
	   	            {elementType: 'labels.text.stroke', stylers: [{color: '#19233e'}]},
	   	            {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
	   	            {
	   	              featureType: 'administrative.locality',
	   	              elementType: 'labels.text.fill',
	   	              stylers: [{color: '#d59563'}]
	   	            },
	   	            {
	   	              featureType: 'poi',
	   	              elementType: 'labels.text.fill',
	   	              stylers: [{color: '#d59563'}]
	   	            },
	   	            {
	   	              featureType: 'poi.park',
	   	              elementType: 'geometry',
	   	              stylers: [{color: '#263c3f'}]
	   	            },
	   	            {
	   	              featureType: 'poi.park',
	   	              elementType: 'labels.text.fill',
	   	              stylers: [{color: '#6b9a76'}]
	   	            },
	   	            {
	   	              featureType: 'road',
	   	              elementType: 'geometry',
	   	              stylers: [{color: '#38414e'}]
	   	            },
	   	            {
	   	              featureType: 'road',
	   	              elementType: 'geometry.stroke',
	   	              stylers: [{color: '#212a37'}]
	   	            },
	   	            {
	   	              featureType: 'road',
	   	              elementType: 'labels.text.fill',
	   	              stylers: [{color: '#9ca5b3'}]
	   	            },
	   	            {
	   	              featureType: 'road.highway',
	   	              elementType: 'geometry',
	   	              stylers: [{color: '#746855'}]
	   	            },
	   	            {
	   	              featureType: 'road.highway',
	   	              elementType: 'geometry.stroke',
	   	              stylers: [{color: '#1f2835'}]
	   	            },
	   	            {
	   	              featureType: 'road.highway',
	   	              elementType: 'labels.text.fill',
	   	              stylers: [{color: '#fcb040'}]  //  f3d19c
	   	            },
	   	            {
	   	              featureType: 'transit',
	   	              elementType: 'geometry',
	   	              stylers: [{color: '#2f3948'}]
	   	            },
	   	            {
	   	              featureType: 'transit.station',
	   	              elementType: 'labels.text.fill',
	   	              stylers: [{color: '#d59563'}]
	   	            },
	   	            {
	   	              featureType: 'water',
	   	              elementType: 'geometry',
	   	              stylers: [{color: '#010623'}]
	   	            },
	   	            {
	   	              featureType: 'water',
	   	              elementType: 'labels.text.fill',
	   	              stylers: [{color: '#515c6d'}]
	   	            },
	   	            {
	   	              featureType: 'water',
	   	              elementType: 'labels.text.stroke',
	   	              stylers: [{color: '#17263c'}]
	   	            }
	   	          ]
	   	});  //land 19233e, water 010623, footer.dark #222, sec-footer #1b1b1b,
	   	
	}	
};

function processData(data) 
{
	console.log(data);
	
   	//Clear the Results List
    $( "#resultsList" ).empty();
	
   	//Local Count Variable
   	var i = 0;
     	
   	$("#results").show();

    //Cycle Through Results
    $.each( data.resolvedLocationsMinimum, function( idx, entry ) {
		
    	var place = new Object();
       	place.id = entry.geonameID;
       	place.name = entry.name;
       	place.countryCode = entry.countryCode;
       	place.latitude = entry.latitude;
       	place.longitude = entry.longitude;
       	
       	if ( UNIQUE_PLACES.indexOf( place.id ) == -1 ) 
       	{
       	
       		UNIQUE_PLACES.push(place.id);
       	
	    	console.log("Found " + place.id + " Name: " + place.name);
	    		
	  		//Append Results to DIV
	  		$("#resultsList").append(styleResultSet(place)); 
	  			
	  		//Create Google Lat/Lng
	  	   	var latLng = new google.maps.LatLng(place.latitude, place.longitude);
	  						
	  		//Add Marker
	  	   	addMarker(latLng, place);
  	
       	}
  		//Increment Local Variable
  		i = i+1;
    		  	
    });
    
    //If !=0, Adjust Map Zoom to Boundary
	if (i != 0) {
					
		//Boundary Object
		MARKER_BOUNDARY = new google.maps.LatLngBounds();
			
		//Loop Through Markers to Form Boundary
		$.each(MARKERS, function (index, marker) {
			MARKER_BOUNDARY.extend(marker.position);
		});
			
		//Set Map to Boundary
		MAP.fitBounds(MARKER_BOUNDARY);
	}
		
    //Restrain Zoom to 8 or Closer. 
//    if (MAP.getZoom() > 8) {
//    	MAP.setZoom(8);
//    }    		
		    
    return i;
    	
}

function styleResultSet(place) {
	
	var source   = $("#result_template").html();
	var template = Handlebars.compile(source);
	
	var context = {id: place.id, name: place.name, code: place.countryCode, lat: place.latitude, lon: place.longitude};
	var html    = template(context);
	
	return html;  
}  

function initMap() {
	Map.init();
}

function addMarker(location, place) {
 		
	//Create Marker
	var marker = new google.maps.Marker({
    	position: location,
    	map: MAP,
    	title: place.name,
	});
 		
 	//Add Marker to Array
	MARKERS.push(marker);
   		
 	//Create InfoWindow
//   	var infowindow = new google.maps.InfoWindow({
//   	      content: styleInfoWindowTemplate(place),
//   	      maxWidth: 275
//	});
   		
 	//Create Listener on Marker
   	google.maps.event.addListener(marker, 'click', function () {
   	
	   	//infowindow.setContent(marker.html);
//	   	infowindow.open(map, marker);
	   			
	   	//highlight corresponding resultRow
	    $('#resultsList .resultEntry').each(function () {
	    	var target = $(this).attr("id"); 
	    	
	    	var title = $(this).children(':nth-child(1)').text();
	    	if (title == place.name) {
	    		$(this).css("background", "#E9F8FF");
	    				
	    		//if position is 'absolute', scrollTo corresponding resultEntry
	    		if ($('#results').css("position") == 'absolute') {
	    		
		    		var anchor = $('#resultsList #' + target);
		    		var position = anchor.position().top;
	    		
	    			$("#results").animate({scrollTop: position});
	    		}
	    	}
	    	else
	    	{
	    		$(this).css("background", "white");
	    	}
	    });
   	});
   		
   	// Event that closes the InfoWindow with a click on the map
//   	google.maps.event.addListener(map, 'click', function() {
//   	    infowindow.close();	    
//   	});
    		
//	google.maps.event.addListener(infowindow, 'domready', function() {
//
//	    var iwOuter = $('.gm-style-iw');
//
//   	    var iwBackground = iwOuter.prev();
//	    
//   	    // Removes background shadow DIV
//	    iwBackground.children(':nth-child(2)').css({'display' : 'none'});
//	    
//	    // Removes white background DIV
//	    iwBackground.children(':nth-child(4)').css({'display' : 'none'});
//		
//	    // Changes the desired tail shadow color.
//   	    iwBackground.children(':nth-child(3)').find('div').children().css({'box-shadow': 'rgba(72, 181, 233, 0.6) 0px 1px 6px', 'z-index' : '1'});
//    	
//   	    // Reference to the div that groups the close button elements.
//   	    var iwCloseBtn = iwOuter.next();
//
//   	    // Apply the desired effect to the close button
//   	    iwCloseBtn.css({right: '-270px', top: '19px', position: 'relative'}); //, opacity: '1', border: '7px solid #48b5e9', 'border-radius': '13px', 'box-shadow': '0 0 5px #3990B9'});
//    });
    		
 }

//JQuery Ready Function
$(document).ready(function(){
	
	
	
	$("#search_clavin").on("click", function( e ) {
		
		e.preventDefault(); 
		
		var textarea = $("#textstring");
		
	    $.ajax
	    ({
	        type: "POST",
	        url: BASE_URL + "/api/v0/geotagmin",
	        dataType: 'json',
		    contentType: 'text/plain',
	        async: true,
	        data: textarea,
	        success: function (data) {
	        	
	        	i = processData(data);
	        }
        }); 
		
	});
	
	$('#results').on('mousewheel DOMMouseScroll', function(e) {
		if ( $('#results').css( "position" ) == 'absolute') {
    		var d = e.originalEvent.wheelDelta || -e.originalEvent.detail,
    	        dir = d > 0 ? 'up' : 'down',
    	        stop = (dir == 'up' && this.scrollTop == 0) || (dir == 'down' && this.scrollTop == this.scrollHeight-this.offsetHeight);
    	    stop && e.preventDefault();
		}
	});
	
});

(function() {

	clavin.init();
	
})();


