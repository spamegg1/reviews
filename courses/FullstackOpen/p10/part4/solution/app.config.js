import 'dotenv/config';

export default {
  name: 'react-native-model-solutions',
  slug: 'react-native-model-solutions',
  platforms: ['ios', 'android', 'web'],
  version: '1.0.0',
  orientation: 'portrait',
  icon: './assets/icon.png',
  splash: {
    image: './assets/splash.png',
    resizeMode: 'contain',
    backgroundColor: '#ffffff',
  },
  updates: {
    fallbackToCacheTimeout: 0,
  },
  assetBundlePatterns: ['**/*'],
  ios: {
    supportsTablet: true,
  },
  extra: {
    env: process.env.ENV,
    apolloUri: process.env.APOLLO_URI
  }
};