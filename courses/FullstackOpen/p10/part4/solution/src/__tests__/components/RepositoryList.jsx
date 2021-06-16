import React from 'react';
import { render } from '@testing-library/react-native';

import { RepositoryListContainer } from '../../components/RepositoryList';

describe('RepositoryList', () => {
  describe('RepositoryListContainer', () => {
    it('renders repository information correctly', () => {
      const repositories = {
        pageInfo: {
          totalCount: 8,
          hasNextPage: true,
          endCursor:
            'WyJhc3luYy1saWJyYXJ5LnJlYWN0LWFzeW5jIiwxNTg4NjU2NzUwMDc2XQ==',
          startCursor: 'WyJqYXJlZHBhbG1lci5mb3JtaWsiLDE1ODg2NjAzNTAwNzZd',
        },
        edges: [
          {
            node: {
              id: 'jaredpalmer.formik',
              fullName: 'jaredpalmer/formik',
              description: 'Build forms in React, without the tears',
              language: 'TypeScript',
              forksCount: 1619,
              stargazersCount: 21856,
              ratingAverage: 88,
              reviewCount: 3,
              ownerAvatarUrl:
                'https://avatars2.githubusercontent.com/u/4060187?v=4',
            },
            cursor: 'WyJqYXJlZHBhbG1lci5mb3JtaWsiLDE1ODg2NjAzNTAwNzZd',
          },
          {
            node: {
              id: 'async-library.react-async',
              fullName: 'async-library/react-async',
              description: 'Flexible promise-based React data loader',
              language: 'JavaScript',
              forksCount: 69,
              stargazersCount: 1760,
              ratingAverage: 72,
              reviewCount: 3,
              ownerAvatarUrl:
                'https://avatars1.githubusercontent.com/u/54310907?v=4',
            },
            cursor:
              'WyJhc3luYy1saWJyYXJ5LnJlYWN0LWFzeW5jIiwxNTg4NjU2NzUwMDc2XQ==',
          },
        ],
      };

      const { getAllByTestId } = render(
        <RepositoryListContainer repositories={repositories} />,
      );

      const fullNames = getAllByTestId('repositoryItemFullName');
      const descriptions = getAllByTestId('repositoryItemDescription');
      const languages = getAllByTestId('repositoryItemLanguage');
      const stars = getAllByTestId('repositoryItemStars');
      const forks = getAllByTestId('repositoryItemForks');
      const reviews = getAllByTestId('repositoryItemReviews');
      const ratings = getAllByTestId('repositoryItemRating');

      expect(fullNames[0]).toHaveTextContent('jaredpalmer/formik');
      expect(fullNames[1]).toHaveTextContent('async-library/react-async');

      expect(descriptions[0]).toHaveTextContent(
        'Build forms in React, without the tears',
      );

      expect(descriptions[1]).toHaveTextContent(
        'Flexible promise-based React data loader',
      );

      expect(languages[0]).toHaveTextContent('TypeScript');
      expect(languages[1]).toHaveTextContent('JavaScript');

      expect(stars[0]).toHaveTextContent('21.9k');
      expect(stars[1]).toHaveTextContent('1.8k');

      expect(forks[0]).toHaveTextContent('1.6k');
      expect(forks[1]).toHaveTextContent('69');

      expect(reviews[0]).toHaveTextContent('3');
      expect(reviews[1]).toHaveTextContent('3');

      expect(ratings[0]).toHaveTextContent('88');
      expect(ratings[1]).toHaveTextContent('72');
    });
  });
});