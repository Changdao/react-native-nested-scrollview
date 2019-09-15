# react-native-innerfirst-scrollview

## Getting started

`$ npm install react-native-innerfirst-scrollview --save`

### Mostly automatic installation

`$ react-native link react-native-innerfirst-scrollview`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-innerfirst-scrollview` and add `InnerfirstScrollview.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libInnerfirstScrollview.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.InnerfirstScrollviewPackage;` to the imports at the top of the file
  - Add `new InnerfirstScrollviewPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-innerfirst-scrollview'
  	project(':react-native-innerfirst-scrollview').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-innerfirst-scrollview/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-innerfirst-scrollview')
  	```


## Usage
```javascript
import InnerfirstScrollview from 'react-native-innerfirst-scrollview';

// TODO: What to do with the module?
InnerfirstScrollview;
```
# react-native-innerfirst-scrollview
