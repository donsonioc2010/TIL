import React from 'react';
import {Button, SafeAreaView, StyleSheet, View} from 'react-native';

// React Native의 모든 화면 컴포넌트는 Navigation이라는 Props가 전달된다.
function AuthHomeScreen({navigation}: AuthHomeScreenProps) {
  return (
    <SafeAreaView>
      <View>
        <Button
          title="로그인화면으로 이동"
          onPress={() => navigation.navigate('Login')}
        />
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({});

export default AuthHomeScreen;
