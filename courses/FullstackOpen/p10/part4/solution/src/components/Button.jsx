import React from 'react';
import { TouchableWithoutFeedback, View, StyleSheet } from 'react-native';

import Text from './Text';
import theme from '../theme';

const styles = StyleSheet.create({
  container: {
    paddingVertical: 15,
    paddingHorizontal: 20,
    minWidth: 64,
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: theme.roundness,
  },
  text: {
    color: 'white',
  },
  colorPrimary: {
    backgroundColor: theme.colors.primary,
  },
  colorError: {
    backgroundColor: theme.colors.error,
  },
});

const Button = ({ children, style, color = 'primary', ...props }) => {
  const buttonStyle = [
    styles.container,
    style,
    color === 'primary' && styles.colorPrimary,
    color === 'error' && styles.colorError,
  ];

  return (
    <TouchableWithoutFeedback {...props}>
      <View style={buttonStyle}>
        <Text style={styles.text} fontWeight="bold">
          {children}
        </Text>
      </View>
    </TouchableWithoutFeedback>
  );
};

export default Button;