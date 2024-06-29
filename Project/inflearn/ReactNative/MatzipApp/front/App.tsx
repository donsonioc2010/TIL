import React, {useState} from 'react';
import {
  Button,
  SafeAreaView,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';

function App(): JSX.Element {
  const [name, setName] = useState('');
  const handleChangeInput = (text: string) => {
    console.log(text);
    setName(text);
  };

  return (
    // 노치영역을 제외한 안전한 영역을 만들어주는 컴포넌트
    <SafeAreaView style={styles.container}>
      <View style={styles.inputContainer}>
        <Text>이름</Text>
        <TextInput
          style={styles.input}
          value={name}
          onChangeText={handleChangeInput}
        />
      </View>
    </SafeAreaView>
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
