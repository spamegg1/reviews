import React from 'react';
import RNPickerSelect from 'react-native-picker-select';
import { StyleSheet } from 'react-native';

import theme from '../theme';

const styles = StyleSheet.create({
  picker: {
    fontFamily: theme.fonts.main,
    fontSize: theme.fontSizes.body,
    color: theme.colors.textPrimary,
  },
});

const Picker = ({ style, ...props }) => {
  const pickerStyle = { ...styles.picker, ...style };

  return <RNPickerSelect style={pickerStyle} {...props} />;
};

export default Picker;