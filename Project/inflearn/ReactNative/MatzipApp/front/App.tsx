import {NavigationContainer} from '@react-navigation/native';
import React, {useState} from 'react';
import {
  Button,
  SafeAreaView,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';

import AuthStackNavigatior from './src/navigation/AuthStackNavigator';

function App(): JSX.Element {
  const [name, setName] = useState('');
  const handleChangeInput = (text: string) => {
    console.log(text);
    setName(text);
  };

  return (
    <NavigationContainer>
      <AuthStackNavigatior />
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // margin: '20%',
    // margin: 10,
    // marginHorizontal: 10,
    // marginVertical: 10,
  },
  input: {
    flex: 1,
    borderWidth: 2,
    borderColor: 'black',
    height: 50,
    width: 100,
  },
  inputContainer: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    // justifyContent: 'center',
    // justifyContent: 'space-between',
    // justifyContent: 'space-around',
    // justifyContent: 'space-evenly',
  },
});

export default App;
