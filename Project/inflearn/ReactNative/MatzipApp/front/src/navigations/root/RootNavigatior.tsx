import AuthStackNavigatior from '../stack/AuthStackNavigator';
import MainDrawerNavigator from '../drawer/MainDrawerNavigator';

function RootNavigator() {
  const isLoggedIn = true;

  return <>{isLoggedIn ? <MainDrawerNavigator /> : <AuthStackNavigatior />}</>;
}

export default RootNavigator;
