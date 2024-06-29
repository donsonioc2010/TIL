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
    <SafeAreaView>
      <View style={styles.container}>
        <Text>텍스트</Text>
        {/* onPress가 onClick과 동일한 기능이라 생각하면 된다. */}
        <Button title="버튼 이름" onPress={() => console.log('버튼클릭')} />
        <TextInput />
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'red',
    // margin: '20%',
    // margin: 10,
    marginHorizontal: 10,
    marginVertical: 10,
  },
});

export default App;
