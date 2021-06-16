import React, { useState } from 'react';
import { FlatList, View, StyleSheet, TouchableOpacity } from 'react-native';
import { useHistory } from 'react-router-native';
import { Searchbar } from 'react-native-paper';
import { useDebounce } from 'use-debounce';

import RepositoryItem from './RepositoryItem';
import useRepositories from '../hooks/useRepositories';
import Picker from './Picker';

const styles = StyleSheet.create({
  separator: {
    height: 10,
  },
  headerContainer: {
    padding: 15,
  },
  searchContainer: {
    marginBottom: 15,
  },
});

const ItemSeparator = () => <View style={styles.separator} />;

const orderByOptions = [
  { label: 'Latest repositories', value: 'latest' },
  {
    label: 'Highest rated repositories',
    value: 'highestRating',
  },
  {
    label: 'Lowest rated repositories',
    value: 'lowestRating',
  },
];

const variablesByOrderBy = {
  latest: {
    orderBy: 'CREATED_AT',
    orderDirection: 'DESC',
  },
  highestRating: {
    orderBy: 'RATING_AVERAGE',
    orderDirection: 'DESC',
  },
  lowestRating: {
    orderBy: 'RATING_AVERAGE',
    orderDirection: 'ASC',
  },
};

const RepositoryListHeader = ({
  onOrderByChange,
  orderBy,
  searchKeyword,
  onSearchKeywordChange,
}) => {
  return (
    <View style={styles.headerContainer}>
      <View style={styles.searchContainer}>
        <Searchbar
          placeholder="Search repositories"
          value={searchKeyword}
          onChangeText={onSearchKeywordChange}
        />
      </View>
      <Picker
        placeholder={{}}
        onValueChange={onOrderByChange}
        value={orderBy}
        items={orderByOptions}
      />
    </View>
  );
};

export class RepositoryListContainer extends React.Component {
  renderHeader = () => {
    const {
      onOrderByChange,
      orderBy,
      searchKeyword,
      onSearchKeywordChange,
    } = this.props;

    return (
      <RepositoryListHeader
        onOrderByChange={onOrderByChange}
        orderBy={orderBy}
        searchKeyword={searchKeyword}
        onSearchKeywordChange={onSearchKeywordChange}
      />
    );
  };

  render() {
    const { repositories, onEndReach, onRepositoryPress } = this.props;

    const repositoryNodes = repositories
      ? repositories.edges.map((edge) => edge.node)
      : [];

    return (
      <FlatList
        data={repositoryNodes}
        keyExtractor={({ id }) => id}
        renderItem={({ item }) => (
          <TouchableOpacity
            key={item.id}
            onPress={() => onRepositoryPress(item.id)}
          >
            <RepositoryItem repository={item} />
          </TouchableOpacity>
        )}
        ItemSeparatorComponent={ItemSeparator}
        ListHeaderComponent={this.renderHeader}
        onEndReached={onEndReach}
        onEndReachedThreshold={0.5}
        initialNumToRender={8}
      />
    );
  }
}

const RepositoryList = () => {
  const history = useHistory();
  const [orderBy, setOrderBy] = useState('latest');
  const [searchKeyword, setSearchKeyword] = useState('');
  const [debouncedSearchKeyword] = useDebounce(searchKeyword, 500);

  const { repositories, fetchMore } = useRepositories({
    first: 8,
    ...variablesByOrderBy[orderBy],
    searchKeyword: debouncedSearchKeyword,
  });

  const onEndReach = () => {
    fetchMore();
  };

  return (
    <RepositoryListContainer
      repositories={repositories}
      orderBy={orderBy}
      onOrderByChange={(newOrderBy) => {
        setOrderBy(newOrderBy);
      }}
      onEndReach={onEndReach}
      searchKeyword={searchKeyword}
      onSearchKeywordChange={(keyword) => setSearchKeyword(keyword)}
      onRepositoryPress={(id) => {
        history.push(`/repositories/${id}`);
      }}
    />
  );
};

export default RepositoryList;