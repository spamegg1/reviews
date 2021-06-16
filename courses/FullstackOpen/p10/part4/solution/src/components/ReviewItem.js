import React from 'react';
import { View, StyleSheet } from 'react-native';
import { format } from 'date-fns';

import Text from './Text';
import theme from '../theme';

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'white',
    flexDirection: 'row',
  },
  ratingCircle: {
    width: 50,
    height: 50,
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 25,
    borderStyle: 'solid',
    borderWidth: 2,
    borderColor: theme.colors.primary,
  },
  ratingText: {
    color: theme.colors.primary,
  },
  ratingContainer: {
    flexGrow: 0,
    marginRight: 15,
  },
  contentContainer: {
    flexGrow: 1,
    flexShrink: 1,
  },
  createdAtText: {
    marginBottom: 5,
  },
  actionsContainer: {
    flexDirection: 'row',
    marginTop: 15,
  },
});

const ReviewItem = ({ review, title, style }) => {
  const { text, createdAt, rating, user } = review;
  const reviewTitle = title ? title : user.username;

  return (
    <View style={[styles.container, style]}>
      <View style={styles.ratingContainer}>
        <View style={styles.ratingCircle}>
          <Text
            fontWeight="bold"
            fontSize="subheading"
            style={styles.ratingText}
          >
            {rating}
          </Text>
        </View>
      </View>
      <View style={styles.contentContainer}>
        <Text fontWeight="bold" fontSize="subheading">
          {reviewTitle}
        </Text>
        <Text color="textSecondary" style={styles.createdAtText}>
          {format(new Date(createdAt), 'dd.MM.yyyy')}
        </Text>
        {text ? <Text>{text}</Text> : null}
      </View>
    </View>
  );
};

export default ReviewItem;