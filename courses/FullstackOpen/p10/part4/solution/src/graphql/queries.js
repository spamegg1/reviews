import { gql } from 'apollo-boost';

import {
  REPOSITORY_BASE_FIELDS,
  USER_BASE_FIELDS,
  REVIEW_BASE_FIELDS,
  PAGE_INFO_FIELDS,
} from './fragments';

export const GET_REPOSITORIES = gql`
  query getRepositories(
    $orderBy: AllRepositoriesOrderBy
    $orderDirection: OrderDirection
    $first: Int
    $after: String
    $searchKeyword: String
  ) {
    repositories(
      orderBy: $orderBy
      orderDirection: $orderDirection
      first: $first
      after: $after
      searchKeyword: $searchKeyword
    ) {
      edges {
        node {
          ...RepositoryBaseFields
          ratingAverage
          reviewCount
        }
        cursor
      }
      pageInfo {
        ...PageInfoFields
      }
    }
  }

  ${REPOSITORY_BASE_FIELDS}
  ${PAGE_INFO_FIELDS}
`;

export const GET_AUTHORIZED_USER = gql`
  query getAuthorizedUser(
    $includeReviews: Boolean = false
    $reviewsFirst: Int
    $reviewsAfter: String
  ) {
    authorizedUser {
      ...UserBaseFields
      reviews(first: $reviewsFirst, after: $reviewsAfter)
        @include(if: $includeReviews) {
        edges {
          node {
            ...ReviewBaseFields
            user {
              ...UserBaseFields
            }
            repository {
              ...RepositoryBaseFields
            }
          }
          cursor
        }
        pageInfo {
          ...PageInfoFields
        }
      }
    }
  }

  ${USER_BASE_FIELDS}
  ${PAGE_INFO_FIELDS}
  ${REPOSITORY_BASE_FIELDS}
  ${REVIEW_BASE_FIELDS}
`;

export const GET_REPOSITORY = gql`
  query getRepository($id: ID!, $reviewsFirst: Int, $reviewsAfter: String) {
    repository(id: $id) {
      ...RepositoryBaseFields
      ratingAverage
      reviewCount
      reviews(first: $reviewsFirst, after: $reviewsAfter) {
        edges {
          node {
            ...ReviewBaseFields
            user {
              ...UserBaseFields
            }
            repository {
              ...RepositoryBaseFields
            }
          }
          cursor
        }
        pageInfo {
          ...PageInfoFields
        }
      }
    }
  }

  ${REPOSITORY_BASE_FIELDS}
  ${REVIEW_BASE_FIELDS}
  ${USER_BASE_FIELDS}
  ${PAGE_INFO_FIELDS}
`;