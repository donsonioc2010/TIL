import React from 'react';
import {
  Button,
  SafeAreaView,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';

function App(): JSX.Element {
  return (
    // 노치영역을 제외한 안전한 영역을 만들어주는 컴포넌트
    <SafeAreaView style={styles.container}>
      <View style={styles.inputContainer}>
        <TextInput style={styles.input} />
        <Text>텍스트</Text>
      </View>
      <View style={styles.inputContainer}>
        <TextInput style={styles.input} />
        <Text>텍스트</Text>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'yellow',
    // margin: '20%',
    // margin: 10,
    // marginHorizontal: 10,
    // marginVertical: 10,
  },
  input: {
    flex: 1,
    borderWidth: 2,
    borderColor: 'black',
    height: 100,
    width: 100,
  },
  inputContainer: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'red',
    // justifyContent: 'center',
    // justifyContent: 'space-between',
    // justifyContent: 'space-around',
    // justifyContent: 'space-evenly',
  },
});

export default App;
