import { AsyncStorage } from 'react-native';

class AuthStorage {
  constructor(namespace = 'auth') {
    this.namespace = namespace;
  }

  getKey(key) {
    return `${this.namespace}:${key}`;
  }

  getAccessToken() {
    return AsyncStorage.getItem(this.getKey('accessToken'));
  }

  setAccessToken(accessToken) {
    return AsyncStorage.setItem(this.getKey('accessToken'), accessToken);
  }

  removeAccessToken() {
    return AsyncStorage.removeItem(this.getKey('accessToken'));
  }
}

export default AuthStorage;