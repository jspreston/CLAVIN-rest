# 5-Minute Tutorial 

This is the simplest possible example of using CLAVIN-REST. 

### Before you start

Follow the [Installation](installation.html) and setup instructions for setting up CLAVIN.

### Query the /geotag RESTful Endpoint

The /geotag endpoint accepts a POST of data, content-type: text/plain. The data model for /geotag is: 
```
resolvedLocations {
geoname (Geoname),
location (Location),
matchedName (String),
fuzzy (boolean),
confidence (integer)
}

Geoname {
geonameID (integer),
name (String),
asciiName (String),
alternateNames (Array[String]),
preferredName (String), 
latitude (double),
longitude (double),
featureClass (char),
featureCode (String),
primaryCountryCode (String),
alternateCountryCodes (Array[String]),
admin1Code (String),
admin2Code (String, optional),
admin3Code (String, optional),
admin4Code (String, optional),
population (integer),
elevation (integer),
digitalElevationModel (integer),
timezone (String),
modificationDate (integer),
parent (Parent, optional),
gazetteerRecord (String),
primaryCountryName (String),
parentAncestryKey (String),
ancestryKey (String, optional),
topLevelAdminDivision (boolean),
topLevelTerritory (boolean),
gazetteerRecordWithAncestry (String),
ancestryResolved (boolean)
}

Location {
text (boolean),
position (integer)
}

Parent {
geonameID (integer),
name (String),
asciiName (String),
alternateNames (Array[String]),
preferredName (String), 
latitude (double),
longitude (double),
featureClass (char),
featureCode (String),
primaryCountryCode (String),
alternateCountryCodes (Array[String]),
admin1Code (String),
admin2Code (String, optional),
admin3Code (String, optional),
admin4Code (String, optional),
population (integer),
elevation (integer),
digitalElevationModel (integer),
timezone (String),
modificationDate (integer),
parent (Parent, optional),
gazetteerRecord (String),
primaryCountryName (String),
parentAncestryKey (String),
ancestryKey (String, optional),
topLevelAdminDivision (boolean),
topLevelTerritory (boolean),
gazetteerRecordWithAncestry (String),
ancestryResolved (boolean)
}
```

The model schema for /geotag is:
```
{
   resolvedLocations [
      {
         "geoname":{
		    "geonameID": 0,
		    "name": "String",
 		    "asciiName": "String",
		    "alternateNames":[
		 	   "String",
		    ],
		    "preferredName": "String",
		    "latitude": 00.00000,
		    "longitude": 00.00000,
		    "featureClass": "A",
		    "featureCode": "String",
		    "primaryCountryCode": "String",
		    "alternateCountryCodes":[
 		       "String",
  		    ],
		    "admin1Code": "00",
		    "admin2Code": "",
		    "admin3Code": "",
		    "admin4Code": "",
		 	"population": 0000000,
			"elevation":  0000000,
			"digitalElevationModel": 00,
			"timezone": "String",
			"modificationDate": 0000000000000,
			"parent":{
			    "geonameID": 0,
			    "name": "String",
	 		    "asciiName": "String",
			    "alternateNames":[
			 	   "String",
			    ],
			    "preferredName": "String",
			    "latitude": 00.00000,
			    "longitude": 00.00000,
			    "featureClass": "A",
			    "featureCode": "String",
			    "primaryCountryCode": "String",
			    "alternateCountryCodes":[
	 		       "String",
	  		    ],
			    "admin1Code": "00",
			    "admin2Code": "",
			    "admin3Code": "",
			    "admin4Code": "",
			 	"population": 0000000,
				"elevation":  0000000,
				"digitalElevationModel": 00,
				"timezone": "String",
				"modificationDate": 0000000000000,
				"parent":{
					null,
				},
				"gazetteerRecord": "String",
				"primaryCountryName": "String",
				"parentAncestryKey": "String",
				"ancestryKey": "String",
				"topLevelAdminDivision": true,
				"topLevelTerritory": true,
				"gazetteerRecordWithAncestry": "String",
				"ancestryResolved": true
			},
			"gazetteerRecord": "String",
			"primaryCountryName": "String",
			"parentAncestryKey": "String",
			"ancestryKey": "String",
			"topLevelAdminDivision": true,
			"topLevelTerritory": true,
			"gazetteerRecordWithAncestry": "String",
			"ancestryResolved": true
		},
		"location":{
			"text": "Mogadishu",
			"position": 166
		},
		"matchedName": "mogadishu",
		"fuzzy": false,
		"confidence": 1
      },			
   ]
}
```

### Query the /geotagmin RESTful Endpoint

The /geotagmin endpoint accepts a POST of data, content-type: text/plain. The data model for /geotag is: 
```
resolvedLocations {
geonameID (integer),
name	(String),
countryCode (String),
matchedName (String),
latitude (double),
longitude (double)
}
```

The model schema for /geotag is:
```
{
   resolvedLocations [
      {
         "geonameID": 0,
         "name": "String",
		 "countryCode": "String",
		 "matchedName": "String",
		 "latitude": 00.00000,
		 "longitude": 00.00000
	  },
   ]
}
```

#### That\'s it!

That probably didn\'t even take five full minutes, did it?


**Please note:** Loading the worldwide gazetteer uses a non-trivial amount of memory. If you encounter `Java heap space` errors when using CLAVIN, bump up the maximum heap size for your JVM. Allocating 2GB (e.g., `-Xmx2g`) is a good place to start, but you may need to go higher if you are using the service more aggressively.
