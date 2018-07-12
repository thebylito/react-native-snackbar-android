# react-native-snackbar-android
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fthebylito%2Freact-native-snackbar-android.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fthebylito%2Freact-native-snackbar-android?ref=badge_shield)


react-native-snackbar-android is a [React Native](http://facebook.github.io/react-native/) library for Snackbar on Android.

### Android Only

<div>
<img src="https://github.com/thebylito/react-native-snackbar-android/raw/master/screenshots/screenshot1.png" height="600">
<img src="https://github.com/thebylito/react-native-snackbar-android/raw/master/screenshots/screenshot2.png" height="600">
<img src="https://github.com/thebylito/react-native-snackbar-android/raw/master/screenshots/screenshot3.png" height="600">
</div>

## Table of Contents

- [Installation](#installation)
- [Example](#example)
- [API](#api)
- [License](#license)

## Installation

`$ npm install react-native-snackbar-android --save`

### Automatic Configuration

`$ react-native link react-native-snackbar-android`

### Manual Configuration

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.thebylito.reactnativesnackbar.ReactNativeSnackBarPackage;` to the imports at the top of the file
  - Add `new ReactNativeSnackBarPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
    include ':react-native-snackbar-android'
    project(':react-native-snackbar-android').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-snackbar-android/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
    compile project(':react-native-snackbar-android')
  	```
4. Edit settings in `android/app/build.gradle`:
    ```
    compileSdkVersion 27
    buildToolsVersion "27.0.1"
    targetSdkVersion 27
  	```
5. Add Maven to `android/build.gradle`:
    ```
    buildscript {
        repositories {
           ...
                maven {
                    url 'https://maven.google.com/'
                    name 'Google'
                }
            ...
        }
    ```
    AND
    ```
    allprojects {
        repositories {
        ...
            maven {
                url 'https://maven.google.com/'
                name 'Google'
            }
        ...
        }
    }
    ```

## Example

**Android Implementation**
```javascript
import React, { Component } from 'react';
import SnackBar, { duration } from 'react-native-snackbar-android'
import { Text, View } from 'react-native';

export default class App extends Component {
  componentDidMount() {
    SnackBar.show({
      message: 'Hello Word',
      backgroundColor: 'red',
      duration: duration.LENGTH_LONG,
      onShow: () => { console.log('SHOW') },
      onHide: () => { console.log('HIDE') },
      action: {
        title: 'My Button',
        txtColor: 'white',
        onPress: () => { alert('You Press ME!') }
      }
    })
  }

  render() {
    return (
      <View style={styles.container}>
        <Text>
        Hello Word
        </Text>
      </View>
    );
  }
}
```

## API

PS.: Supported colors formats are: `#RRGGBB #AARRGGBB`

The following names are also accepted: `red, blue, green, black, white, gray, cyan, magenta, yellow, lightgray, darkgray, grey, lightgrey, darkgrey, aqua, fuchsia, lime, maroon, navy, olive, purple, silver, and teal.`

### `show(options) :(Android)`
Show SnackBar
```
{
  message: String with text to show,
  duration: can be int number in miliseconds or import {duration}, // Required
  backgroundColor: color of background color of SnackBar, // No required
  onShow: Function on Show Snackbar, // No required
  onHide: Function on Hide Snackbar, // No required
  action: { // No required
    title: String with text to button, // Required
    onPress: Function on click button, // No required
    txtColor: The color of button text, // No required
  },
};
```


```javascript
    SnackBar.show({
      message: 'Hello Word'
    })
```

## OR

```javascript
    SnackBar.show({
      message: 'Hello Word',
      backgroundColor: 'red',
      action: {
        title: 'My Button',
        onPress: () => { alert('You Press ME!') }
      }
    })
```

## License

MIT


[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fthebylito%2Freact-native-snackbar-android.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fthebylito%2Freact-native-snackbar-android?ref=badge_large)