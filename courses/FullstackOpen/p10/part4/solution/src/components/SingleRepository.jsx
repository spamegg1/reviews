import React from 'react';
import { useParams } from 'react-router-native';
import { useQuery } from '@apollo/react-hooks';
import { FlatList, StyleSheet, View } from 'react-native';

import RepositoryItem from './RepositoryItem';
import { GET_REPOSITORY } from '../graphql/queries';
import ReviewItem from './ReviewItem';

const styles = StyleSheet.create({
  separator: {
    height: 10,
  },
  reviewItem: {
    padding: 15,
  },
});

const ItemSeparator = () => <View style={styles.separator} />;

const RepositoryInfo = ({ repository }) => {
  return (
    <>
      <RepositoryItem repository={repository} showGithubLink />
      <ItemSeparator />
    </>
  );
};

const updateQuery = (previousResult, { fetchMoreResult }) => {
  const nextResult = {
    repository: {
      ...fetchMoreResult.repository,
      reviews: {
        ...fetchMoreResult.repository.reviews,
        edges: [
          ...previousResult.repository.reviews.edges,
          ...fetchMoreResult.repository.reviews.edges,
        ],
      },
    },
  };

  return nextResult;
};

const SingleRepository = () => {
  const { id } = useParams();
  const variables = { id, reviewsFirst: 5 };

  const { data, loading, fetchMore } = useQuery(GET_REPOSITORY, {
    fetchPolicy: 'cache-and-network',
    variables,
  });

  const repository = data ? data.repository : undefined;

  const handleFetchMore = () => {
    const canFetchMore =
      !loading && repository && repository.reviews.pageInfo.hasNextPage;

    if (!canFetchMore) {
      return;
    }

    fetchMore({
      query: GET_REPOSITORY,
      variables: {
        ...variables,
        reviewsAfter: repository.reviews.pageInfo.endCursor,
      },
      updateQuery,
    });
  };

  const reviewNodes = repository
    ? repository.reviews.edges.map(({ node }) => node)
    : [];

  return (
    <FlatList
      data={reviewNodes}
      renderItem={({ item }) => (
        <ReviewItem style={styles.reviewItem} review={item} />
      )}
      keyExtractor={({ id }) => id}
      ListHeaderComponent={() =>
        repository ? <RepositoryInfo repository={repository} /> : null
      }
      ItemSeparatorComponent={ItemSeparator}
      onEndReached={handleFetchMore}
      onEndReachedThreshold={0.5}
    />
  );
};

export default SingleRepository;