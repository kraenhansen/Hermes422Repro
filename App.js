/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import {Text, View, NativeModules} from 'react-native';

const App = () => {
  // Get the Hermes422Repro native module to initialize the TurboModule
  NativeModules.Hermes422Repro;
  // Now the "echo" function should be injected onto the global
  /* global echo */
  const result = echo(() => {
    throw new Error('Here comes the error!');
  });
  // Try logging the result - which we wont ever make it to
  console.log({result});

  return (
    <View>
      <Text>Reproducing hermes#422</Text>
    </View>
  );
};

export default App;
