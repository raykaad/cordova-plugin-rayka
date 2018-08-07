Rayka Cordova plugin
====================
Rayka plugin for Cordova and Phonegap.<br/>

## Install plugin ##

### Cordova cli ###
https://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-Line%20Interface - npm install -g cordova@6.2.3
```c
cordova plugin add cordova-plugin-rayka
(when build error, use github url: cordova plugin add cordova plugin add https://github.com/raykaad/cordova-plugin-rayka)
```

### Cocoon ###
https://cocoon.io - Create project - [specific project] - Setting - Plugins - Custom - Git Url: https://github.com/raykaad/cordova-plugin-rayka.git - INSTALL - Save<br>

### Phonegap build service (config.xml) ###
https://build.phonegap.com/ - Apps - [specific project] - Update code - Zip file including config.xml
```c
<gap:plugin name="cordova-plugin-rayka" source="npm" />
```


## Use Plugin ##
    
### 1.Add banner 

you can add the banner at the TOP/BOTTOM/XY of your app.
windows.rayka.Position holds  positions const .

    window.rayka.addBanner(window.rayka.Position.XY, x, y);

### 2.Remove banner 

    window.rayka.removeBanner();

###  3.Show popup 
first cache popup ad, and then show it in onPopupReady function or show it when your game over.

```
function cachePopup(zone_id){
	window.rayka.cachePopup(zone_id);
}
	
function showPopup(){
	window.rayka.showPopup();
}

function onPopupReady(){
	alert('Popup is ready to show');
	// or call showPopup()
}

function onPopupFail(){
	alert('Popup is failed to load');
}

function onPopupClose(){
	alert('Popup is closed);
}

function onPopupClick(){
	alert('Popup is clicked);
}

function onPopupRequest(){
	alert('Popup is requested);
}

function onPopupShow(){
	alert('Popup is showing);
}
```

###  4.Show video 
first cache video ad, and then show it in onVideoReady function or show it when your game over.

```
function cacheVideo(zone_id){
	window.rayka.cacheVideo(zone_id);
}
	
function showVideo(){
	window.rayka.showVideo();
}

function onVideoReady(){
	alert('Video is ready to show');
	// or call showVideo()
}

function onVideoFail(){
	alert('Video is failed to load');
}

function onPopupClose(){
	alert('Video is closed);
}

function onVideoClick(){
	alert('Video is clicked);
}

function onVideoRequest(){
	alert('Video is requested);
}

function onVideoStart(){
	alert('Video is started);
}

function onVideoComplete(){
	alert('Video is completed);
}

function onVideoNotComplete(){
	alert('Video is not completed);
}
```

## Examples ##
<a href="https://github.com/raykaad/cordova-plugin-rayka/blob/master/example/index.html">Click to see!</a><br>

# Credite #
Created by: Rayka<br>
Email: info@raykaad.com
