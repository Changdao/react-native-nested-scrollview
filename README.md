# react-native-singledirection-scrollview

## Overview

ScrollView has bad experience on android while there is another scroll control inside it, because when you scroll the inside control, the scrollview will stole the responder.
This react-native-singledirection-scrollview will not stole the responder from inside horizontoal scroll control.

<div class='row'>
        <img src='https://raw.githubusercontent.com/Changdao/react-native-singledirection-scrollview/master/demo.gif' width="300px"/>
</div>

## Notice  

The singledirection scrollview doesn't support stickyheaders and zoom yet. 
The singledirection scrollview doesn't support horizontalscroll.

Many scrollview features doesn't test, if you met defect, please let me know.

This library's code was excerpt from react-native and 

## Getting started

`$ npm install react-native-singledirection-scrollview --save`

### Mostly automatic installation

`$ react-native link react-native-singledirection-scrollview`

### Manual installation


#### iOS

This componenent doesn't support iOS, because the experience in iOS is very better than scrollview on Android, it's not necessary to create a new scrollview on iOS.


#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.github.changdao.IFScrollViewPackage;` to the imports at the top of the file
  - Add `new IFScrollViewPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-singledirection-scrollview'
  	project(':react-native-singledirection-scrollview').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-singledirection-scrollview/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-singledirection-scrollview')
  	```


## Usage

It's recommended that using SDScrollView as the following:

```
...
import { ScrollView, Platform } from 'react';
import SDScrollView from 'react-native-singledirection-scrollview';
...

const ScrollViewClass = Platform==='ios'?ScrollView:SDScrollView;

render(){
	// innerfirst is necessary to enable the effect
	return <ScrollViewClass innerFirst={true}> </ScrollViewClass>
}
```



