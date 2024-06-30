const queryKeys = {
  AUTH: 'auth',
  GET_ACCESS_TOKEN: 'getAccessToken',
  GET_PROFILE: 'getProfile',
} as const;

const storageKeys = {
  REFRESH_TOKEN: 'refreshToken',
};

export {queryKeys, storageKeys};
