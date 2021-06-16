import React from 'react';
import { FlatList, View, StyleSheet, Alert } from 'react-native';
import { Link } from 'react-router-native';
import { useMutation } from '@apollo/react-hooks';

import ReviewItem from './ReviewItem';
import Button from './Button';
import useAuthorizedUser from '../hooks/useAuthorizedUser';
import { DELETE_REVIEW } from '../graphql/mutations';

const styles = StyleSheet.create({
  reviewItemWrapper: {
    padding: 15,
    backgroundColor: 'white',
  },
  separator: {
    height: 10,
  },
  actionsContainer: {
    marginTop: 15,
    flexDirection: 'row',
  },
  actionButton: {
    flexGrow: 1,
    marginRight: 15,
  },
  lastActionButton: {
    marginRight: 0,
  },
});

const DeleteReviewButton = ({ onPress, ...props }) => {
  const alertButtons = [
    {
      text: 'Cancel',
      style: 'cancel',
    },
    {
      text: 'Delete',
      onPress: () => onPress(),
    },
  ];

  const deleteWithConfirmation = () => {
    Alert.alert(
      'Delete review',
      'Are you sure you want to delete this review?',
      alertButtons,
      { cancelable: false },
    );
  };

  return (
    <Button onPress={deleteWithConfirmation} color="error" {...props}>
      Delete review
    </Button>
  );
};

const ReviewItemWithActions = ({ review, onDelete }) => {
  return (
    <View style={styles.reviewItemWrapper}>
      <ReviewItem review={review} title={review.repository.fullName} />
      <View style={styles.actionsContainer}>
        <Link
          component={Button}
          to={`/repositories/${review.repository.id}`}
          style={styles.actionButton}
        >
          View repository
        </Link>

        <DeleteReviewButton
          onPress={onDelete}
          style={[styles.actionButton, styles.lastActionButton]}
        />
      </View>
    </View>
  );
};

const ItemSeparator = () => <View style={styles.separator} />;

const MyReviews = () => {
  const { authorizedUser, refetch } = useAuthorizedUser({
    includeReviews: true,
  });

  const [deleteReview] = useMutation(DELETE_REVIEW);
  const reviewEdges = authorizedUser ? authorizedUser.reviews.edges : [];
  const reviewNodes = reviewEdges.map(({ node }) => node);

  const onDelete = async (id) => {
    await deleteReview({ variables: { id } });
    refetch();
  };

  return (
    <FlatList
      data={reviewNodes}
      renderItem={({ item }) => (
        <ReviewItemWithActions
          review={item}
          onDelete={() => onDelete(item.id)}
        />
      )}
      keyExtractor={({ id }) => id}
      ItemSeparatorComponent={ItemSeparator}
    />
  );
};

export default MyReviews;