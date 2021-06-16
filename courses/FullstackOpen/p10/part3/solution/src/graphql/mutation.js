import { gql } from 'apollo-boost';

import { USER_BASE_FIELDS } from './fragments';

export const AUTHORIZE = gql`
  mutation authorize($credentials: AuthorizeInput!) {
    authorize(credentials: $credentials) {
      accessToken
      user {
        ...UserBaseFields
      }
    }
  }

  ${USER_BASE_FIELDS}
`;