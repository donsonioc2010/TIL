import useAuth from '@/hooks/queries/useAuth';
import MainDrawerNavigator from '@/navigations/drawer/MainDrawerNavigator';
import AuthStackNavigatior from '@/navigations/stack/AuthStackNavigator';

function RootNavigator() {
  const {isLogin} = useAuth();

  return <>{isLogin ? <MainDrawerNavigator /> : <AuthStackNavigatior />}</>;
}

export default RootNavigator;
